package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SCItems;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class SCLangProvider extends LanguageProvider {
    public SCLangProvider(PackOutput output) {
        super(output, SpecialisedCells.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(SCItems.ARMORY_CELL_HOUSING, "Armory Cell Housing");
        addItem(SCItems.ARMORY_CELL_1K, "1k Armory Cell");
        addItem(SCItems.ARMORY_CELL_4K, "4k Armory Cell");
        addItem(SCItems.ARMORY_CELL_16K, "16k Armory Cell");

        addItem(SCItems.TOOLS_CELL_HOUSING, "Tools Cell Housing");
        addItem(SCItems.TOOLS_CELL_1K, "1k Tools Cell");
        addItem(SCItems.TOOLS_CELL_4K, "4k Tools Cell");
        addItem(SCItems.TOOLS_CELL_16K, "16k Tools Cell");
    }
}