package com.thiakil.specialisedcells;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SCTags {
    public static final TagKey<Item> ARMORY_CELL_STORABLE = create("armory_cell_storable");
    public static final TagKey<Item> TOOLS_CELL_STORABLE = create("tools_cell_storable");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SpecialisedCells.MODID, name));
    }
}