package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemEnchantedBookCell extends ItemSpecialisedCell{
    public ItemEnchantedBookCell(double idleDrain, int bytesPerType, int totalItemTypes, int totalKilobytes, ItemLike coreItem, ItemLike housingItem) {
        super(idleDrain, bytesPerType, totalItemTypes, totalKilobytes, coreItem, housingItem);
    }

    @Override
    public boolean isAllowed(AEItemKey what) {
        return what.getItem() == Items.ENCHANTED_BOOK;
    }

    @Override
    public int getAmountPerByte() {
        return 2;
    }

    @Override
    @Nullable
    public Object getPrimaryKey(AEItemKey what) {
        CompoundTag stackTag = what.getTag();
        if (stackTag == null) {
            return null;
        }
        Set<ResourceLocation> enchantments = new TreeSet<>();
        ListTag pStoredEnchantments = stackTag.getList("StoredEnchantments", 10);
        for(int i = 0; i < pStoredEnchantments.size(); ++i) {
            CompoundTag compoundtag = pStoredEnchantments.getCompound(i);
            try {
                enchantments.add(EnchantmentHelper.getEnchantmentId(compoundtag));
            } catch (ResourceLocationException ignored){}
        }
        if (enchantments.isEmpty()) {
            return null;
        } else if (enchantments.size() == 1) {
            return enchantments.iterator().next();
        }
        return new CompositeEnchantments(enchantments);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.empty();//no point when it's all books
    }

    @Override
    public IUpgradeInventory getUpgrades(ItemStack stack) {
        return UpgradeInventories.forItem(stack, 2);
    }

    private record CompositeEnchantments(Set<ResourceLocation> enchantmentIds){}
}