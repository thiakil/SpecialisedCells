package com.thiakil.specialisedcells;

import appeng.api.client.StorageCellModels;
import appeng.api.storage.StorageCells;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;
import appeng.init.client.InitItemColors;
import appeng.items.storage.BasicStorageCell;
import com.mojang.logging.LogUtils;
import com.thiakil.specialisedcells.cells.SpecialisedCellHandler;
import com.thiakil.specialisedcells.items.ItemEnchantedBookCell;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SpecialisedCells.MODID)
public class SpecialisedCells
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "specialised_cells";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    //public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    //public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));


    public SpecialisedCells()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        SCItems.init(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        event.enqueueWork(()->{
            StorageCells.addCellHandler(SpecialisedCellHandler.INSTANCE);
        });
        String storageCellGroup = GuiText.StorageCells.getTranslationKey();
        var itemCells = List.of(
                SCItems.ARMORY_CELL_1K, SCItems.ARMORY_CELL_4K, SCItems.ARMORY_CELL_16K,
                SCItems.TOOLS_CELL_1K, SCItems.TOOLS_CELL_4K, SCItems.TOOLS_CELL_16K,
                SCItems.ENCHANTED_BOOK_CELL_1K, SCItems.ENCHANTED_BOOK_CELL_4K, SCItems.ENCHANTED_BOOK_CELL_16K
        );
        for (var itemCell : itemCells) {
            Item cellItem = itemCell.get();
            Upgrades.add(AEItems.EQUAL_DISTRIBUTION_CARD, cellItem, 1, storageCellGroup);
            Upgrades.add(AEItems.VOID_CARD, cellItem, 1, storageCellGroup);
            if (!(cellItem instanceof ItemEnchantedBookCell)) {
                Upgrades.add(AEItems.INVERTER_CARD, cellItem, 1, storageCellGroup);
            }
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    //@SubscribeEvent
    //public void onServerStarting(ServerStartingEvent event)
    //{
    //    // Do something when the server starts
    //
    //}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            ResourceLocation ARMORY_CELL_MODEL = new ResourceLocation(MODID, "block/drive/cells/armory_cell");
            ResourceLocation TOOLS_CELL_MODEL = new ResourceLocation(MODID, "block/drive/cells/tools_cell");
            ResourceLocation EB_CELL_MODEL = new ResourceLocation(MODID, "block/drive/cells/enchanted_cell");
            StorageCellModels.registerModel(SCItems.ARMORY_CELL_1K.get(), ARMORY_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.ARMORY_CELL_4K.get(), ARMORY_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.ARMORY_CELL_16K.get(), ARMORY_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.TOOLS_CELL_1K.get(), TOOLS_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.TOOLS_CELL_4K.get(), TOOLS_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.TOOLS_CELL_16K.get(), TOOLS_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.ENCHANTED_BOOK_CELL_1K.get(), EB_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.ENCHANTED_BOOK_CELL_4K.get(), EB_CELL_MODEL);
            StorageCellModels.registerModel(SCItems.ENCHANTED_BOOK_CELL_16K.get(), EB_CELL_MODEL);
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register(BasicStorageCell::getColor,
                    SCItems.ARMORY_CELL_1K.get(),
                    SCItems.ARMORY_CELL_4K.get(),
                    SCItems.ARMORY_CELL_16K.get(),
                    SCItems.TOOLS_CELL_1K.get(),
                    SCItems.TOOLS_CELL_4K.get(),
                    SCItems.TOOLS_CELL_16K.get(),
                    SCItems.ENCHANTED_BOOK_CELL_1K.get(),
                    SCItems.ENCHANTED_BOOK_CELL_4K.get(),
                    SCItems.ENCHANTED_BOOK_CELL_16K.get()
            );
        }
    }
}