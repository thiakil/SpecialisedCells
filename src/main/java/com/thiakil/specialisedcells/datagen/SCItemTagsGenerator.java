package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SCTags;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class SCItemTagsGenerator extends ItemTagsProvider {
    public SCItemTagsGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SpecialisedCells.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SCTags.ARMORY_CELL_STORABLE)
                .addTags(
                        Tags.Items.ARMORS,
                        ItemTags.SWORDS,
                        Tags.Items.TOOLS_SHIELDS,
                        Tags.Items.TOOLS_BOWS,
                        Tags.Items.TOOLS_CROSSBOWS,
                        Tags.Items.TOOLS_TRIDENTS
                );
    }
}