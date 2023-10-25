package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class SCLangProvider extends LanguageProvider {
    public SCLangProvider(PackOutput output) {
        super(output, SpecialisedCells.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(SpecialisedCells.ARMORY_CELL_1K, "1k Armory Cell");
    }
}