package com.thiakil.specialisedcells.cells;

import appeng.api.config.IncludeExclude;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.core.localization.GuiText;
import appeng.core.localization.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Basically a copy of {@link appeng.me.cells.BasicCellHandler} but with BasicCellInventory classes replaced.
 * TODO make it not use non api classes?
 */
public class SpecialisedCellHandler implements ICellHandler {
    public static final SpecialisedCellHandler INSTANCE = new SpecialisedCellHandler();

    @Override
    public boolean isCell(ItemStack is) {
        return SpecialisedCellInventory.isCell(is);
    }

    @Override
    public SpecialisedCellInventory getCellInventory(ItemStack is, ISaveProvider container) {
        return SpecialisedCellInventory.createInventory(is, container);
    }

    public void addCellInformationToTooltip(ItemStack is, List<Component> lines) {
        var handler = getCellInventory(is, null);
        if (handler == null) {
            return;
        }

        lines.add(Tooltips.bytesUsed(handler.getUsedBytes(), handler.getTotalBytes()));
        lines.add(Tooltips.typesUsed(handler.getStoredItemTypes(), handler.getTotalItemTypes()));

        if (handler.isPreformatted()) {
            var list = (handler.getPartitionListMode() == IncludeExclude.WHITELIST ? GuiText.Included
                    : GuiText.Excluded)
                    .text();

            lines.add(
                        GuiText.Partitioned.withSuffix(" - ").append(list));
        }
    }
}