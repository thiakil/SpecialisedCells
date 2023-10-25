package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.items.contents.CellConfig;
import appeng.util.ConfigInventory;
import com.thiakil.specialisedcells.cells.ISpecialisedCellType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemArmoryCell extends Item implements ISpecialisedCellType {
    public ItemArmoryCell() {
        super(new Properties());
    }

    @Override
    public double getIdleDrain() {
        return 2;//64k std
    }

    @Override
    public int getBytesPerType() {
        return 32;//4k std cell
    }

    @Override
    public long getTotalItemTypes() {
        return 63;//todo
    }

    @Override
    public int getAmountPerByte() {
        return AEKeyType.items().getAmountPerByte() / 2;
    }

    @Override
    public boolean isAllowed(AEItemKey what) {
        return false;
    }

    @Override
    public long getTotalBytes() {
        return 4 * 1024;//start with 4k
    }

    @Override
    public IUpgradeInventory getUpgrades(ItemStack stack) {
        return UpgradeInventories.forItem(stack, 3);
    }

    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return CellConfig.create(AEKeyType.items().filter(), is);
    }
}