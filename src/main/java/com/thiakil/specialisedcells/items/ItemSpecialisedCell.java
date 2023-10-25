package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKeyType;
import com.thiakil.specialisedcells.cells.ISpecialisedCellType;
import com.thiakil.specialisedcells.cells.SpecialisedCellHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ItemSpecialisedCell extends Item implements ISpecialisedCellType {
    protected final double idleDrain;
    protected final int bytesPerType;
    protected final int totalItemTypes;
    protected final int totalBytes;

    public ItemSpecialisedCell(double idleDrain, int bytesPerType, int totalItemTypes, int totalKilobytes) {
        super(new Properties().stacksTo(1));
        this.idleDrain = idleDrain;
        this.bytesPerType = bytesPerType;
        this.totalItemTypes = totalItemTypes;
        this.totalBytes = totalKilobytes * 1024;
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

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return SpecialisedCellHandler.INSTANCE.getTooltipImage(stack);
    }
}