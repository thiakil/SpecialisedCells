package com.thiakil.specialisedcells.cells;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpecialisedCellHandler implements ICellHandler {
    @Override
    public boolean isCell(ItemStack is) {
        return false;//todo
    }

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack is, @Nullable ISaveProvider host) {
        return null;//todo
    }
}