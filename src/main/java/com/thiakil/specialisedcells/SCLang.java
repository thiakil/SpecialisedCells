package com.thiakil.specialisedcells;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public enum SCLang {
    OnlyEmptyCellsCanBeDisassembled;

    public Component component() {
        return Component.translatable(getKey());
    }

    @NotNull
    public String getKey() {
        return SpecialisedCells.MODID + "." + name();
    }
}
