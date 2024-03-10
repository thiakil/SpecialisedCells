package com.thiakil.specialisedcells.datagen;

import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpecialisedCells.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        BlockTagsProvider blockTagsProvider = gen.addProvider(event.includeServer(), new SCBlockTagsProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new SCItemTagsGenerator(output, event.getLookupProvider(), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()));
        gen.addProvider(event.includeClient(), new SCLangProvider(output));
        gen.addProvider(event.includeServer(), new SCRecipeProvider(output));
    }

}