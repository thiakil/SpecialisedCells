package com.thiakil.specialisedcells;

import appeng.api.ids.AEItemIds;
import com.thiakil.specialisedcells.items.ItemEnchantedBookCell;
import com.thiakil.specialisedcells.items.ItemOreCell;
import com.thiakil.specialisedcells.items.ItemTagBasedCell;
import com.thiakil.specialisedcells.items.ItemTagWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SCItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpecialisedCells.MODID);

    public static final ItemLike CELL_COMPONENT_1K = item(AEItemIds.CELL_COMPONENT_1K);
    public static final ItemLike CELL_COMPONENT_4K = item(AEItemIds.CELL_COMPONENT_4K);
    public static final ItemLike CELL_COMPONENT_16K = item(AEItemIds.CELL_COMPONENT_16K);
    public static final ItemLike CELL_COMPONENT_64K = item(AEItemIds.CELL_COMPONENT_64K);

    public static final DeferredItem<Item> ARMORY_CELL_HOUSING = ITEMS.register("armory_cell_housing", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARMORY_CELL_1K = ITEMS.register("armory_cell_1k", () -> new ItemTagBasedCell(1.5, 16, 31, 1, SCTags.ARMORY_CELL_STORABLE, CELL_COMPONENT_1K, ARMORY_CELL_HOUSING));
    public static final DeferredItem<Item> ARMORY_CELL_4K = ITEMS.register("armory_cell_4k", () -> new ItemTagBasedCell(2, 32, 31, 4, SCTags.ARMORY_CELL_STORABLE, CELL_COMPONENT_4K, ARMORY_CELL_HOUSING));
    public static final DeferredItem<Item> ARMORY_CELL_16K = ITEMS.register("armory_cell_16k", () -> new ItemTagBasedCell(2.5, 48, 31, 16, SCTags.ARMORY_CELL_STORABLE, CELL_COMPONENT_16K, ARMORY_CELL_HOUSING));

    public static final DeferredItem<Item> ENCHANTED_BOOK_CELL_HOUSING = ITEMS.register("enchanted_book_cell_housing", ()-> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(@NotNull ItemStack pStack) {
            return true;
        }
    });
    public static final DeferredItem<Item> ENCHANTED_BOOK_CELL_1K = ITEMS.register("enchanted_book_cell_1k", () -> new ItemEnchantedBookCell(1.5, 16, 63, 1, CELL_COMPONENT_1K, ENCHANTED_BOOK_CELL_HOUSING));
    public static final DeferredItem<Item> ENCHANTED_BOOK_CELL_4K = ITEMS.register("enchanted_book_cell_4k", () -> new ItemEnchantedBookCell(2, 32, 63, 4, CELL_COMPONENT_4K, ENCHANTED_BOOK_CELL_HOUSING));
    public static final DeferredItem<Item> ENCHANTED_BOOK_CELL_16K = ITEMS.register("enchanted_book_cell_16k", () -> new ItemEnchantedBookCell(2.5, 48, 63, 16, CELL_COMPONENT_16K, ENCHANTED_BOOK_CELL_HOUSING));

    public static final DeferredItem<Item> ORE_CELL_HOUSING = ITEMS.register("ore_cell_housing", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORE_CELL_1K = ITEMS.register("ore_cell_1k", () -> new ItemOreCell(1, 8, 63, 1, SCTags.ORE_CELL_STORABLE, CELL_COMPONENT_1K, ORE_CELL_HOUSING));
    public static final DeferredItem<Item> ORE_CELL_4K = ITEMS.register("ore_cell_4k", () -> new ItemOreCell(1.5, 32, 63, 4, SCTags.ORE_CELL_STORABLE, CELL_COMPONENT_4K, ORE_CELL_HOUSING));
    public static final DeferredItem<Item> ORE_CELL_16K = ITEMS.register("ore_cell_16k", () -> new ItemOreCell(2, 64, 63, 16, SCTags.ORE_CELL_STORABLE, CELL_COMPONENT_16K, ORE_CELL_HOUSING));
    public static final DeferredItem<Item> ORE_CELL_64K = ITEMS.register("ore_cell_64k", () -> new ItemOreCell(2.5, 128, 63, 64, SCTags.ORE_CELL_STORABLE, CELL_COMPONENT_64K, ORE_CELL_HOUSING));

    public static final DeferredItem<ItemTagWrapper> TAG_WRAPPER = ITEMS.register("tag_wrapper", ItemTagWrapper::new);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPECIALISED_CELLS_TAB = SpecialisedCells.CREATIVE_MODE_TABS.register("specialised_cells", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ARMORY_CELL_1K.get().getDefaultInstance())
            .title(Component.literal("Specialised Cells"))
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> entry : ITEMS.getEntries()) {
                    if (entry == TAG_WRAPPER) {
                        continue;
                    }
                    output.accept(entry.get());
                }
            }).build());

    static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    private static ItemLike item(ResourceLocation location) {
        return DeferredItem.createItem(location);
    }
}