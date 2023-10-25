package com.thiakil.specialisedcells.cells;

import appeng.api.config.Actionable;
import appeng.api.config.FuzzyMode;
import appeng.api.config.IncludeExclude;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.IBasicCellItem;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.core.AELog;
import appeng.core.definitions.AEItems;
import appeng.items.contents.CellConfig;
import appeng.util.ConfigInventory;
import appeng.util.prioritylist.IPartitionList;
import com.thiakil.specialisedcells.items.ItemArmoryCell;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ArmoryCellInventory implements StorageCell {
    private static final int MAX_ITEM_TYPES = 63;
    private static final String ITEM_COUNT_TAG = "ic";
    private static final String STACK_KEYS = "keys";
    private static final String STACK_AMOUNTS = "amts";
    private static final AEKeyType KEY_TYPE = AEKeyType.items();

    private final ISaveProvider container;
    private IPartitionList partitionList;
    private IncludeExclude partitionListMode;
    private short storedItems;
    private long storedItemCount;
    private Object2LongMap<AEItemKey> storedAmounts;
    private final ItemStack i;
    private final long maxItemsPerType; // max items per type, basically infinite unless there is a distribution card.
    private final boolean hasVoidUpgrade;
    private boolean isPersisted = true;

    private ArmoryCellInventory(ItemStack o, ISaveProvider container) {
        this.i = o;

        this.container = container;
        this.storedItems = (short) getTag().getLongArray(STACK_AMOUNTS).length;
        this.storedItemCount = getTag().getLong(ITEM_COUNT_TAG);
        this.storedAmounts = null;

        // Updates the partition list and mode based on installed upgrades and the configured filter.
        var builder = IPartitionList.builder();

        var upgrades = getUpgradesInventory();
        var config = getConfigInventory();

        boolean hasInverter = upgrades.isInstalled(AEItems.INVERTER_CARD);

        builder.addAll(config.keySet());

        partitionListMode = (hasInverter ? IncludeExclude.BLACKLIST : IncludeExclude.WHITELIST);
        partitionList = builder.build();

        // Check for equal distribution card.
        if (upgrades.isInstalled(AEItems.EQUAL_DISTRIBUTION_CARD)) {
            // Compute max possible amount of types based on whitelist size, and bound by type limit.
            long maxTypes = Integer.MAX_VALUE;
            if (partitionListMode == IncludeExclude.WHITELIST && !config.keySet().isEmpty()) {
                maxTypes = config.keySet().size();
            }
            maxTypes = Math.min(maxTypes, MAX_ITEM_TYPES);

            long totalStorage = (getTotalBytes() - getBytesPerType() * maxTypes) * getAmountPerByte();
            // Technically not exactly evenly distributed, but close enough!
            this.maxItemsPerType = Math.max(0, (totalStorage + maxTypes - 1) / maxTypes);
        } else {
            this.maxItemsPerType = Long.MAX_VALUE;
        }

        this.hasVoidUpgrade = upgrades.isInstalled(AEItems.VOID_CARD);
    }

    public IncludeExclude getPartitionListMode() {
        return partitionListMode;
    }

    public boolean isPreformatted() {
        return !partitionList.isEmpty();
    }

    private CompoundTag getTag() {
        // On Fabric, the tag itself may be copied and then replaced later in case a portable cell is being charged.
        // In that case however, we can rely on the itemstack reference not changing due to the special logic in the
        // transactional inventory wrappers. So we must always re-query the tag from the stack.
        return this.i.getOrCreateTag();
    }

    public static ArmoryCellInventory createInventory(ItemStack o, ISaveProvider container) {
        Objects.requireNonNull(o, "Cannot create cell inventory for null itemstack");

        if (!(o.getItem() instanceof ItemArmoryCell armoryCellItem)) {
            return null;
        }

        // The cell type's channel matches, so this cast is safe
        return new ArmoryCellInventory(o, container);
    }

    protected Object2LongMap<AEItemKey> getCellItems() {
        if (this.storedAmounts == null) {
            this.storedAmounts = new Object2LongOpenHashMap<>();
            this.loadCellItems();
        }

        return this.storedAmounts;
    }

    @Override
    public void persist() {
        if (this.isPersisted) {
            return;
        }

        long itemCount = 0;

        // add new pretty stuff...
        var amounts = new LongArrayList(storedAmounts.size());
        var keys = new ListTag();

        for (var entry : this.storedAmounts.object2LongEntrySet()) {
            long amount = entry.getLongValue();

            if (amount > 0) {
                itemCount += amount;
                keys.add(entry.getKey().toTag());
                amounts.add(amount);
            }
        }

        if (keys.isEmpty()) {
            getTag().remove(STACK_KEYS);
            getTag().remove(STACK_AMOUNTS);
        } else {
            getTag().put(STACK_KEYS, keys);
            getTag().putLongArray(STACK_AMOUNTS, amounts.toArray(new long[0]));
        }

        //todo change this to be by item id not key - see AEItemKey dropSecondary
        this.storedItems = (short) this.storedAmounts.size();

        this.storedItemCount = itemCount;
        if (itemCount == 0) {
            getTag().remove(ITEM_COUNT_TAG);
        } else {
            getTag().putLong(ITEM_COUNT_TAG, itemCount);
        }

        this.isPersisted = true;
    }

    protected void saveChanges() {
        // recalculate values
        this.storedItems = (short) this.storedAmounts.size();
        this.storedItemCount = 0;
        for (var storedAmount : this.storedAmounts.values()) {
            this.storedItemCount += storedAmount;
        }

        this.isPersisted = false;
        if (this.container != null) {
            this.container.saveChanges();
        } else {
            // if there is no ISaveProvider, store to NBT immediately
            this.persist();
        }
    }

    private void loadCellItems() {
        boolean corruptedTag = false;

        var amounts = getTag().getLongArray(STACK_AMOUNTS);
        var tags = getTag().getList(STACK_KEYS, Tag.TAG_COMPOUND);
        if (amounts.length != tags.size()) {
            AELog.warn("Loading storage cell with mismatched amounts/tags: %d != %d",
                    amounts.length, tags.size());
        }

        for (int i = 0; i < amounts.length; i++) {
            var amount = amounts[i];
            AEItemKey key = (AEItemKey) KEY_TYPE.loadKeyFromTag(tags.getCompound(i));

            if (amount <= 0 || key == null) {
                corruptedTag = true;
            } else {
                storedAmounts.put(key, amount);
            }
        }

        if (corruptedTag) {
            this.saveChanges();
        }
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        for (var entry : this.getCellItems().object2LongEntrySet()) {
            out.add(entry.getKey(), entry.getLongValue());
        }
    }

    @Override
    public double getIdleDrain() {
        return 2;//64k std
    }

    public ConfigInventory getConfigInventory() {
        return CellConfig.create(KEY_TYPE.filter(), this.i);
    }

    public IUpgradeInventory getUpgradesInventory() {
        return UpgradeInventories.forItem(this.i, 3);
    }

    public int getBytesPerType() {
        return 32;//4k std cell
    }

    public boolean canHoldNewItem() {
        final long bytesFree = this.getFreeBytes();
        return (bytesFree > this.getBytesPerType()
                || bytesFree == this.getBytesPerType() && this.getUnusedItemCount() > 0)
                && this.getRemainingItemTypes() > 0;
    }

    public long getTotalBytes() {
        return 4 * 1024;//start with 4k
    }

    public long getFreeBytes() {
        return this.getTotalBytes() - this.getUsedBytes();
    }

    public long getTotalItemTypes() {
        return MAX_ITEM_TYPES;
    }

    public long getStoredItemCount() {
        return this.storedItemCount;
    }

    public long getStoredItemTypes() {
        return this.storedItems;
    }

    public long getRemainingItemTypes() {
        var basedOnStorage = this.getFreeBytes() / this.getBytesPerType();
        var baseOnTotal = this.getTotalItemTypes() - this.getStoredItemTypes();
        return Math.min(basedOnStorage, baseOnTotal);
    }

    public long getUsedBytes() {
        var bytesForItemCount = (this.getStoredItemCount() + this.getUnusedItemCount()) / getAmountPerByte();
        return this.getStoredItemTypes() * this.getBytesPerType() + bytesForItemCount;
    }

    public long getRemainingItemCount() {
        final long remaining = this.getFreeBytes() * getAmountPerByte() + this.getUnusedItemCount();
        return remaining > 0 ? remaining : 0;
    }

    private static int getAmountPerByte() {
        return KEY_TYPE.getAmountPerByte() / 2;
    }

    public int getUnusedItemCount() {
        final int div = (int) (this.getStoredItemCount() % getAmountPerByte());

        if (div == 0) {
            return 0;
        }

        return getAmountPerByte() - div;
    }

    @Override
    public CellState getStatus() {
        if (this.getStoredItemTypes() == 0) {
            return CellState.EMPTY;
        }
        if (this.canHoldNewItem()) {
            return CellState.NOT_EMPTY;
        }
        if (this.getRemainingItemCount() > 0) {
            return CellState.TYPES_FULL;
        }
        return CellState.FULL;
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (amount == 0 || !KEY_TYPE.contains(what) || !(what instanceof AEItemKey whatItem)) {
            return 0;
        }

        if (!this.partitionList.matchesFilter(what, this.partitionListMode)) {
            return 0;
        }

        //todo dont accept things we dont want
        //if (this.cellType.isBlackListed(this.i, what)) {
        //    return 0;
        //}

        // Run regular insert logic and then apply void upgrade to the returned value.
        long inserted = innerInsert(whatItem, amount, mode, source);
        return this.hasVoidUpgrade ? amount : inserted;
    }

    // Inner insert for items that pass the filter.
    private long innerInsert(AEItemKey what, long amount, Actionable mode, IActionSource source) {
        var currentAmount = this.getCellItems().getLong(what);
        long remainingItemCount = this.getRemainingItemCount();

        // Deduct the required storage for a new type if the type is new
        if (currentAmount <= 0) {
            if (!canHoldNewItem()) {
                // No space for more types
                return 0;
            }

            remainingItemCount -= (long) this.getBytesPerType() * getAmountPerByte();
            if (remainingItemCount <= 0) {
                return 0;
            }
        }

        // Apply max items per type
        remainingItemCount = Math.max(0, Math.min(this.maxItemsPerType - currentAmount, remainingItemCount));

        if (amount > remainingItemCount) {
            amount = remainingItemCount;
        }

        if (mode == Actionable.MODULATE) {
            getCellItems().put(what, currentAmount + amount);
            this.saveChanges();
        }

        return amount;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {

        var currentAmount = getCellItems().getLong(what);
        if (currentAmount > 0) {
            if (amount >= currentAmount) {
                if (mode == Actionable.MODULATE) {
                    getCellItems().remove(what, currentAmount);
                    this.saveChanges();
                }

                return currentAmount;
            } else {
                if (mode == Actionable.MODULATE) {
                    getCellItems().put((AEItemKey) what, currentAmount - amount);
                    this.saveChanges();
                }

                return amount;
            }
        }

        return 0;
    }

    @Override
    public Component getDescription() {
        return i.getHoverName();
    }
}