package com.thiakil.specialisedcells.datagen;

import appeng.api.ids.AEItemIds;
import com.thiakil.specialisedcells.SCItems;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class SCRecipeProvider extends RecipeProvider {
    public SCRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        Item AE2_CELL_HOUSING = ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.ITEM_CELL_HOUSING).get();
        String hasHousingName = getHasName(AE2_CELL_HOUSING);
        InventoryChangeTrigger.TriggerInstance hasHousing = has(AE2_CELL_HOUSING);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SCItems.ARMORY_CELL_HOUSING.get())
                .define('H', AE2_CELL_HOUSING)
                .define('T', Items.GOLDEN_HELMET)
                .define('L', Items.GOLDEN_CHESTPLATE)
                .define('R', Items.GOLDEN_LEGGINGS)
                .define('B', Items.GOLDEN_BOOTS)
                .pattern(" T ")
                .pattern("LHR")
                .pattern(" B ")
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("armory_cell_housing"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SCItems.TOOLS_CELL_HOUSING.get())
                .define('H', AE2_CELL_HOUSING)
                .define('T', Items.GOLDEN_HOE)
                .define('L', Items.GOLDEN_PICKAXE)
                .define('R', Items.GOLDEN_AXE)
                .define('B', Items.GOLDEN_SHOVEL)
                .pattern(" T ")
                .pattern("LHR")
                .pattern(" B ")
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("tools_cell_housing"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.ARMORY_CELL_1K.get())
                .requires(SCItems.ARMORY_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_1K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("armory_cell_1k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.ARMORY_CELL_4K.get())
                .requires(SCItems.ARMORY_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_4K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("armory_cell_4k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.ARMORY_CELL_16K.get())
                .requires(SCItems.ARMORY_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_16K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("armory_cell_16k"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.TOOLS_CELL_1K.get())
                .requires(SCItems.TOOLS_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_1K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("tools_cell_1k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.TOOLS_CELL_4K.get())
                .requires(SCItems.TOOLS_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_4K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("tools_cell_4k"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SCItems.TOOLS_CELL_16K.get())
                .requires(SCItems.TOOLS_CELL_HOUSING.get())
                .requires(ForgeRegistries.ITEMS.getDelegateOrThrow(AEItemIds.CELL_COMPONENT_16K).get())
                .unlockedBy(hasHousingName, hasHousing)
                .save(pWriter, id("tools_cell_16k"));
    }

    static ResourceLocation id(String name) {
        return new ResourceLocation(SpecialisedCells.MODID, name);
    }
}