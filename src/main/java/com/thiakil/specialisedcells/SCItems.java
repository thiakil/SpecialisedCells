package com.thiakil.specialisedcells;

import com.thiakil.specialisedcells.items.ItemEnchantedBookCell;
import com.thiakil.specialisedcells.items.ItemTagBasedCell;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpecialisedCells.MODID);

    public static final RegistryObject<Item> TOOLS_CELL_HOUSING = ITEMS.register("tools_cell_housing", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOLS_CELL_16K = ITEMS.register("tools_cell_16k", () -> new ItemTagBasedCell(2.5, 48, 31, 16, SCTags.TOOLS_CELL_STORABLE));
    public static final RegistryObject<Item> TOOLS_CELL_4K = ITEMS.register("tools_cell_4k", () -> new ItemTagBasedCell(2, 32, 31, 4, SCTags.TOOLS_CELL_STORABLE));
    public static final RegistryObject<Item> TOOLS_CELL_1K = ITEMS.register("tools_cell_1k", () -> new ItemTagBasedCell(1.5, 16, 31, 1, SCTags.TOOLS_CELL_STORABLE));

    public static final RegistryObject<Item> ARMORY_CELL_HOUSING = ITEMS.register("armory_cell_housing", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMORY_CELL_16K = ITEMS.register("armory_cell_16k", () -> new ItemTagBasedCell(2.5, 48, 31, 16, SCTags.ARMORY_CELL_STORABLE));
    public static final RegistryObject<Item> ARMORY_CELL_4K = ITEMS.register("armory_cell_4k", () -> new ItemTagBasedCell(2, 32, 31, 4, SCTags.ARMORY_CELL_STORABLE));
    public static final RegistryObject<Item> ARMORY_CELL_1K = ITEMS.register("armory_cell_1k", () -> new ItemTagBasedCell(1.5, 16, 31, 1, SCTags.ARMORY_CELL_STORABLE));

    public static final RegistryObject<Item> ENCHANTED_BOOK_CELL_HOUSING = ITEMS.register("enchanted_book_cell_housing", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_BOOK_CELL_16K = ITEMS.register("enchanted_book_cell_16k", () -> new ItemEnchantedBookCell(2.5, 48, 63, 16));
    public static final RegistryObject<Item> ENCHANTED_BOOK_CELL_4K = ITEMS.register("enchanted_book_cell_4k", () -> new ItemEnchantedBookCell(2, 32, 63, 4));
    public static final RegistryObject<Item> ENCHANTED_BOOK_CELL_1K = ITEMS.register("enchanted_book_cell_1k", () -> new ItemEnchantedBookCell(1.5, 16, 63, 1));

    public static final RegistryObject<CreativeModeTab> SPECIALISED_CELLS_TAB = SpecialisedCells.CREATIVE_MODE_TABS.register("specialised_cells", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ARMORY_CELL_1K.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> entry : ITEMS.getEntries()) {
                    output.accept(entry.get());
                }
            }).build());

    static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}