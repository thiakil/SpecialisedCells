package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SCTags;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class SCItemTagsGenerator extends ItemTagsProvider {
    private static final TagKey<Item> TOOLS_PAXELS = ItemTags.create(new ResourceLocation("forge", "tools/paxels"));
    private static final TagKey<Item> TOOLS_WRENCHES = ItemTags.create(new ResourceLocation("forge", "tools/wrench"));
    private static final TagKey<Item> WRENCHES = ItemTags.create(new ResourceLocation("forge", "wrenches"));

    public SCItemTagsGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SpecialisedCells.MODID, existingFileHelper);
    }

    @Override
    protected @Nullable Path getPath(ResourceLocation id) {
        if (id.equals(TOOLS_PAXELS.location()) || id.equals(TOOLS_WRENCHES.location()) || id.equals(WRENCHES.location())) {
            return null;//created only so they don't error, return null so they don't write empty files
        }
        return super.getPath(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TOOLS_PAXELS);
        tag(TOOLS_WRENCHES);
        tag(WRENCHES);

        tag(SCTags.ARMORY_CELL_STORABLE)
                .addTags(
                        Tags.Items.ARMORS,
                        ItemTags.SWORDS,
                        Tags.Items.TOOLS_SHIELDS,
                        Tags.Items.TOOLS_BOWS,
                        Tags.Items.TOOLS_CROSSBOWS,
                        Tags.Items.TOOLS_TRIDENTS
                );
        tag(SCTags.TOOLS_CELL_STORABLE)
                .addTags(
                        Tags.Items.TOOLS_FISHING_RODS,
                        ItemTags.AXES,
                        ItemTags.PICKAXES,
                        ItemTags.SHOVELS,
                        ItemTags.HOES,
                        TOOLS_PAXELS,
                        TOOLS_WRENCHES,
                        WRENCHES
                );
    }
}