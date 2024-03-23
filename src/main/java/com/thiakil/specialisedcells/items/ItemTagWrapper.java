package com.thiakil.specialisedcells.items;

import com.thiakil.specialisedcells.SCItems;
import com.thiakil.specialisedcells.SCLang;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemTagWrapper extends Item {
    private static final String TAG_NAME_KEY = "tag_name";

    public ItemTagWrapper() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public Component getName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        String tagName = tag != null ? getTagString(tag) : "";
        if (stack.getItem() == this && !tagName.isBlank()) {
            return Component.literal(tagName);
        }
        return SCLang.InvalidTag.component();
    }

    @Nullable
    public static TagKey<Item> unwrap(CompoundTag compoundTag) {
        if (compoundTag == null) {
            return null;
        }
        String tagStr = getTagString(compoundTag);
        try {
            return tagStr.isBlank() ? null : TagKey.create(Registries.ITEM, new ResourceLocation(tagStr));
        } catch (ResourceLocationException e) {
            return null;
        }
    }

    @NotNull
    private static String getTagString(CompoundTag tag) {
        return tag.getString(TAG_NAME_KEY);
    }

    public static ItemStack wrapping(TagKey<Item> tagKey) {
        CompoundTag tag = new CompoundTag();
        tag.putString(TAG_NAME_KEY, tagKey.location().toString());
        return new ItemStack(SCItems.TAG_WRAPPER.get(), 1, Optional.of(tag));
    }
}
