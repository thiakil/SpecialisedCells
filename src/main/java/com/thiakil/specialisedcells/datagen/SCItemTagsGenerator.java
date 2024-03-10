package com.thiakil.specialisedcells.datagen;

import appeng.api.ids.AEItemIds;
import com.thiakil.specialisedcells.SCTags;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class SCItemTagsGenerator extends ItemTagsProvider {
    private static final ResourceLocation TOOLS_PAXELS = new ResourceLocation("forge", "tools/paxels");
    private static final ResourceLocation TOOLS_WRENCHES = new ResourceLocation("forge", "tools/wrench");
    private static final ResourceLocation WRENCHES = new ResourceLocation("forge", "wrenches");
    private static final ResourceLocation QUARTZ_KNIFE = new ResourceLocation("ae2", "knife");

    public SCItemTagsGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SpecialisedCells.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SCTags.ARMORY_CELL_STORABLE)
                .addTags(
                        Tags.Items.ARMORS,
                        Tags.Items.TOOLS
                )
                .add(
                        Items.LEATHER_HORSE_ARMOR,
                        Items.IRON_HORSE_ARMOR,
                        Items.GOLDEN_HORSE_ARMOR,
                        Items.DIAMOND_HORSE_ARMOR,
                        Items.ELYTRA,
                        Items.CARROT_ON_A_STICK,
                        Items.WARPED_FUNGUS_ON_A_STICK,
                        Items.FLINT_AND_STEEL,
                        item(AEItemIds.ENTROPY_MANIPULATOR),
                        item(AEItemIds.CHARGED_STAFF)
                )
                .addOptionalTag(TOOLS_PAXELS)
                .addOptionalTag(TOOLS_WRENCHES)
                .addOptionalTag(WRENCHES)
                .addOptionalTag(QUARTZ_KNIFE)
        ;
    }

    public static Item item(ResourceLocation location) {
        return BuiltInRegistries.ITEM.get(location);
    }
}