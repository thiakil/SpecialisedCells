package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.storage.AEKeySlotFilter;
import appeng.api.storage.StorageCells;
import appeng.core.localization.PlayerMessages;
import appeng.helpers.externalstorage.GenericStackInv;
import appeng.items.contents.CellConfig;
import appeng.util.ConfigInventory;
import appeng.util.InteractionUtil;
import com.thiakil.specialisedcells.SCLang;
import com.thiakil.specialisedcells.cells.ISpecialisedCellType;
import com.thiakil.specialisedcells.cells.SpecialisedCellHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ItemSpecialisedCell extends Item implements ISpecialisedCellType {
    protected final double idleDrain;
    protected final int bytesPerType;
    protected final int totalItemTypes;
    protected final int totalBytes;
    protected final ItemLike coreItem;
    protected final ItemLike housingItem;

    public ItemSpecialisedCell(double idleDrain, int bytesPerType, int totalItemTypes, int totalKilobytes, ItemLike coreItem, ItemLike housingItem) {
        super(new Properties().stacksTo(1));
        this.idleDrain = idleDrain;
        this.bytesPerType = bytesPerType;
        this.totalItemTypes = totalItemTypes;
        this.totalBytes = totalKilobytes * 1024;
        this.coreItem = coreItem;
        this.housingItem = housingItem;
    }

    @Override
    public double getIdleDrain() {
        return idleDrain;
    }

    @Override
    public int getBytesPerType() {
        return bytesPerType;
    }

    @Override
    public long getTotalItemTypes() {
        return totalItemTypes;
    }

    @Override
    public int getAmountPerByte() {
        return AEKeyType.items().getAmountPerByte() / 2;
    }

    @Override
    public long getTotalBytes() {
        return totalBytes;
    }

    public void appendHoverText(ItemStack stack,
                                @Nullable Level level,
                                List<Component> lines,
                                TooltipFlag advancedTooltips) {
        SpecialisedCellHandler.INSTANCE.addCellInformationToTooltip(stack, lines);
    }

    private boolean disassembleDrive(ItemStack stack, Level level, Player player) {

        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (level.isClientSide()) {
                return InteractionResultHolder.success(stack);
            }

            final Inventory playerInventory = player.getInventory();
            var inv = StorageCells.getCellInventory(stack, null);
            if (inv != null && playerInventory.getSelected() == stack) {
                var list = inv.getAvailableStacks();
                if (list.isEmpty()) {
                    //playerInventory.setItem(playerInventory.selected, ItemStack.EMPTY);

                    // drop core
                    playerInventory.placeItemBackInInventory(new ItemStack(coreItem));

                    // drop upgrades
                    for (var upgrade : this.getUpgrades(stack)) {
                        playerInventory.placeItemBackInInventory(upgrade);
                    }

                    // drop empty storage cell case
                    return InteractionResultHolder.consume(new ItemStack(housingItem));
                } else {
                    player.displayClientMessage(SCLang.OnlyEmptyCellsCanBeDisassembled.component(), true);
                    return InteractionResultHolder.fail(stack);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    public static ConfigInventory createConfigInventory(AEKeySlotFilter slotFilter, ItemStack is) {
        var holder = new ConfigHolder(is);
        holder.inv = new ConfigInventory(Set.of(AEKeyType.items()), slotFilter, GenericStackInv.Mode.CONFIG_TYPES, 63, holder::save, false){};
        holder.load();
        return holder.inv;
    }

    private static class ConfigHolder {
        private final ItemStack stack;
        private ConfigInventory inv;

        public ConfigHolder(ItemStack stack) {
            this.stack = stack;
        }

        public void load() {
            if (stack.hasTag()) {
                inv.readFromChildTag(stack.getOrCreateTag(), "list");
            }
        }

        public void save() {
            inv.writeToChildTag(stack.getOrCreateTag(), "list");
        }
    }

}