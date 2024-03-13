package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.items.contents.CellConfig;
import appeng.util.ConfigInventory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemTagBasedCell extends ItemSpecialisedCell {
    protected final TagKey<Item> allowedTag;

    public ItemTagBasedCell(double idleDrain, int bytesPerType, int totalItemTypes, int totalKilobytes, TagKey<Item> allowedTag) {
        super(idleDrain, bytesPerType, totalItemTypes, totalKilobytes);
        this.allowedTag = allowedTag;
    }

    @Override
    public IUpgradeInventory getUpgrades(ItemStack stack) {
        return UpgradeInventories.forItem(stack, 3);
    }

    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return createConfigInventory((slot,what)->AEKeyType.items().filter().matches(what) && this.isAllowed((AEItemKey) what), is);
    }

    public Object getPrimaryKey(AEItemKey what) {
        return what.getPrimaryKey();//drop nbt, consider only item type
    }

    @Override
    public boolean isAllowed(AEItemKey what) {
        return what.isTagged(this.allowedTag);
    }
}