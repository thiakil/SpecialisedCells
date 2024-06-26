package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SCItems;
import com.thiakil.specialisedcells.SCLang;
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

        addItem(SCItems.ORE_CELL_HOUSING, "Ore Cell Housing");
        addItem(SCItems.ORE_CELL_1K, "1k ME Ore Cell");
        addItem(SCItems.ORE_CELL_4K, "4k ME Ore Cell");
        addItem(SCItems.ORE_CELL_16K, "16k ME Ore Cell");
        addItem(SCItems.ORE_CELL_64K, "64k ME Ore Cell");

        addItem(SCItems.ENCHANTED_BOOK_CELL_HOUSING, "Enchanted Book Cell Housing");
        addItem(SCItems.ENCHANTED_BOOK_CELL_1K, "1k ME Enchanted Book Cell");
        addItem(SCItems.ENCHANTED_BOOK_CELL_4K, "4k ME Enchanted Book Cell");
        addItem(SCItems.ENCHANTED_BOOK_CELL_16K, "16k ME Enchanted Book Cell");

        add(SCLang.OnlyEmptyCellsCanBeDisassembled, "Only empty storage cells can be disassembled.");
        add(SCLang.InvalidTag, "Invalid or missing Tag");
    }

    private void add(SCLang key, String value) {
        add(key.getKey(), value);
    }
}