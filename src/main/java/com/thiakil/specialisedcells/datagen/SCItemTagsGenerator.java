package com.thiakil.specialisedcells.datagen;

import appeng.api.ids.AEItemIds;
import com.thiakil.specialisedcells.SCTags;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
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

        tag(SCTags.ORE_CELL_STORABLE)
                .addTags(
                        Tags.Items.ORES,
                        Tags.Items.RAW_MATERIALS,
                        SCTags.STORAGE_BLOCKS_RAW_MATERIALS,
                        Tags.Items.DUSTS
                );

        //copied from ElementalCraft and added to, thanks Sirttas!
        tag(SCTags.STORAGE_BLOCKS_RAW_MATERIALS)
                .addTags(Tags.Items.STORAGE_BLOCKS_RAW_COPPER, Tags.Items.STORAGE_BLOCKS_RAW_IRON, Tags.Items.STORAGE_BLOCKS_RAW_GOLD)
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_silver"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_lead"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_tin"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_zinc"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_aluminum"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_nickel"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_uranium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_osmium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_desh"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_calorite"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_ostrum"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_platinum"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_iesnium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_unobtainium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_crimson_iron"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_allthemodium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_vibranium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_iridium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_titanium"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_azure_silver"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_antimony"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_tungsten"))
                .addOptionalTag(new ResourceLocation("forge", "storage_blocks/raw_bauxite"))
                .addOptionalTag(new ResourceLocation("blue_skies", "storage_blocks/raw_aquite"))
                .addOptionalTag(new ResourceLocation("blue_skies", "storage_blocks/raw_charoite"))
                .addOptionalTag(new ResourceLocation("blue_skies", "storage_blocks/raw_falsite"))
                .addOptionalTag(new ResourceLocation("blue_skies", "storage_blocks/raw_ventium"))
                .addOptionalTag(new ResourceLocation("blue_skies", "storage_blocks/raw_horizonite"));
    }

    public static Item item(ResourceLocation location) {
        return BuiltInRegistries.ITEM.get(location);
    }
}