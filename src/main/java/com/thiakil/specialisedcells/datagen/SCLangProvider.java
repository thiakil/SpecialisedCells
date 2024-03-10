package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SCItems;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SCLangProvider extends LanguageProvider {
    public SCLangProvider(PackOutput output) {
        super(output, SpecialisedCells.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(SCItems.ARMORY_CELL_HOUSING, "Armory Cell Housing");
        addItem(SCItems.ARMORY_CELL_1K, "1k ME Armory Cell");
        addItem(SCItems.ARMORY_CELL_4K, "4k ME Armory Cell");
        addItem(SCItems.ARMORY_CELL_16K, "16k ME Armory Cell");

        addItem(SCItems.TOOLS_CELL_HOUSING, "Tools Cell Housing");
        addItem(SCItems.TOOLS_CELL_1K, "1k ME Tools Cell");
        addItem(SCItems.TOOLS_CELL_4K, "4k ME Tools Cell");
        addItem(SCItems.TOOLS_CELL_16K, "16k ME Tools Cell");

        addItem(SCItems.ENCHANTED_BOOK_CELL_HOUSING, "Enchanted Book Cell Housing");
        addItem(SCItems.ENCHANTED_BOOK_CELL_1K, "1k ME Enchanted Book Cell");
        addItem(SCItems.ENCHANTED_BOOK_CELL_4K, "4k ME Enchanted Book Cell");
        addItem(SCItems.ENCHANTED_BOOK_CELL_16K, "16k ME Enchanted Book Cell");
    }
}