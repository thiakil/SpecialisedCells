package com.thiakil.specialisedcells.cells;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.AEKeySlotFilter;
import appeng.util.ConfigInventory;
import appeng.util.ConfigMenuInventory;
import com.thiakil.specialisedcells.items.ItemTagWrapper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class TagConfigInventory extends ConfigInventory {
    private final Function<Item, TagKey<Item>> configStackSupplier;

    public TagConfigInventory(AEKeyType supportedType, @Nullable AEKeySlotFilter slotFilter, int size, @Nullable Runnable listener, Function<Item, TagKey<Item>> configStackSupplier) {
        super(Set.of(supportedType), slotFilter, Mode.CONFIG_TYPES, size, listener, false);
        this.configStackSupplier = configStackSupplier;
    }

    public TagFilters unwrapStacks() {
        TagFilters result = new TagFilters();
        for (GenericStack stack : this.stacks) {
            if (stack != null) {
                if (stack.what() instanceof AEItemKey itemKey && itemKey.getItem() instanceof ItemTagWrapper && itemKey.getTag() != null) {
                    TagKey<Item> tagKey = ItemTagWrapper.unwrap(itemKey.getTag());
                    if (tagKey != null) {
                        result.tags.add(tagKey);
                    }
                } else {
                    result.regularKeys.add(stack.what());
                }
            }
        }
        return result;
    }

    @Override
    public void setStack(int slot, @Nullable GenericStack genericStack) {
        if (genericStack != null && genericStack.what() instanceof AEItemKey itemKey) {
            TagKey<Item> tagToStore = configStackSupplier.apply(itemKey.getItem());
            if (tagToStore != null) {
                genericStack = new GenericStack(AEItemKey.of(ItemTagWrapper.wrapping(tagToStore)), genericStack.amount());
            }
        }
        super.setStack(slot, genericStack);
    }

    public record TagFilters(Set<TagKey<Item>> tags, Set<AEKey> regularKeys){
        TagFilters() {
            this(new HashSet<>(), new HashSet<>());
        }
    }
}
