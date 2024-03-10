package com.thiakil.specialisedcells;

import com.thiakil.specialisedcells.items.ItemEnchantedBookCell;
import com.thiakil.specialisedcells.items.ItemTagBasedCell;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class SCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, SpecialisedCells.MODID);

    public static final DeferredHolder<Item, Item> TOOLS_CELL_HOUSING = ITEMS.register("tools_cell_housing", ()-> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TOOLS_CELL_16K = ITEMS.register("tools_cell_16k", () -> new ItemTagBasedCell(2.5, 48, 31, 16, SCTags.TOOLS_CELL_STORABLE));
    public static final DeferredHolder<Item, Item> TOOLS_CELL_4K = ITEMS.register("tools_cell_4k", () -> new ItemTagBasedCell(2, 32, 31, 4, SCTags.TOOLS_CELL_STORABLE));
    public static final DeferredHolder<Item, Item> TOOLS_CELL_1K = ITEMS.register("tools_cell_1k", () -> new ItemTagBasedCell(1.5, 16, 31, 1, SCTags.TOOLS_CELL_STORABLE));

    public static final DeferredHolder<Item, Item> ARMORY_CELL_HOUSING = ITEMS.register("armory_cell_housing", ()-> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ARMORY_CELL_16K = ITEMS.register("armory_cell_16k", () -> new ItemTagBasedCell(2.5, 48, 31, 16, SCTags.ARMORY_CELL_STORABLE));
    public static final DeferredHolder<Item, Item> ARMORY_CELL_4K = ITEMS.register("armory_cell_4k", () -> new ItemTagBasedCell(2, 32, 31, 4, SCTags.ARMORY_CELL_STORABLE));
    public static final DeferredHolder<Item, Item> ARMORY_CELL_1K = ITEMS.register("armory_cell_1k", () -> new ItemTagBasedCell(1.5, 16, 31, 1, SCTags.ARMORY_CELL_STORABLE));

    public static final DeferredHolder<Item, Item> ENCHANTED_BOOK_CELL_HOUSING = ITEMS.register("enchanted_book_cell_housing", ()-> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(@NotNull ItemStack pStack) {
            return true;
        }
    });
    public static final DeferredHolder<Item, Item> ENCHANTED_BOOK_CELL_16K = ITEMS.register("enchanted_book_cell_16k", () -> new ItemEnchantedBookCell(2.5, 48, 63, 16));
    public static final DeferredHolder<Item, Item> ENCHANTED_BOOK_CELL_4K = ITEMS.register("enchanted_book_cell_4k", () -> new ItemEnchantedBookCell(2, 32, 63, 4));
    public static final DeferredHolder<Item, Item> ENCHANTED_BOOK_CELL_1K = ITEMS.register("enchanted_book_cell_1k", () -> new ItemEnchantedBookCell(1.5, 16, 63, 1));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPECIALISED_CELLS_TAB = SpecialisedCells.CREATIVE_MODE_TABS.register("specialised_cells", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ARMORY_CELL_1K.get().getDefaultInstance())
            .title(Component.literal("Specialised Cells"))
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> entry : ITEMS.getEntries()) {
                    output.accept(entry.get());
                }
            }).build());

    static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}