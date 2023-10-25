package com.thiakil.specialisedcells.cells;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.cells.ICellWorkbenchItem;
import net.minecraft.world.item.ItemStack;

public interface ISpecialisedCellType extends ICellWorkbenchItem {
    double getIdleDrain();
    int getBytesPerType();
    long getTotalItemTypes();
    /** Amount of items per byte */
    int getAmountPerByte();

    boolean isAllowed(AEItemKey what);

    long getTotalBytes();

    @Override
    default FuzzyMode getFuzzyMode(ItemStack is) {
        return FuzzyMode.IGNORE_ALL;
    }

    @Override
    default void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
        //no-op
    }

    /** @return the type identifier for this cell's "type" tracking */
    Object getPrimaryKey(AEItemKey what);
}