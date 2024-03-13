package com.thiakil.specialisedcells.items;

import appeng.api.stacks.AEItemKey;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.thiakil.specialisedcells.SCTags;
import com.thiakil.specialisedcells.SpecialisedCells;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Mod.EventBusSubscriber(modid = SpecialisedCells.MODID)
public class ItemOreCell extends ItemTagBasedCell {
    private static Cache<Item, ResourceLocation> PRIMARY_KEY_CACHE = CacheBuilder.newBuilder().build();
    private static void initTag(TagKey<Item> parent) {
        ResourceLocation location = parent.location();
        initTag(new ResourceLocation(location.getNamespace(), location.getPath()+"/"));
    }

    private static void initTag(ResourceLocation locationPrefix) {
        String subTagPrefix = locationPrefix.getPath();
        BuiltInRegistries.ITEM.getTagNames()
                .filter(tag -> tag.location().getNamespace().equals(locationPrefix.getNamespace()) && tag.location().getPath().startsWith(subTagPrefix))
                .forEach(subTag -> BuiltInRegistries.ITEM.getTag(subTag)
                        .ifPresent(contents -> contents
                                .forEach(holder -> {
                                    if (holder.isBound()) {
                                        SpecialisedCells.LOGGER.info("{} = {}", holder.unwrapKey().map(ResourceKey::location).orElse(null), subTag.location());
                                        PRIMARY_KEY_CACHE.put(holder.value(), subTag.location());
                                    }
                                })
                        )
                );
    }

    @SubscribeEvent
    public static void tagsUpdate(TagsUpdatedEvent event) {
        Optional<Registry<Item>> registry = event.getRegistryAccess().registry(Registries.ITEM);
        if (registry.isPresent()) {
            PRIMARY_KEY_CACHE.invalidateAll();
            initTag(Tags.Items.ORES);
            initTag(Tags.Items.RAW_MATERIALS);
            initTag(Tags.Items.DUSTS);
            initTag(new ResourceLocation("forge", "storage_blocks/raw_"));
        }
    }

    public ItemOreCell(double idleDrain, int bytesPerType, int totalItemTypes, int totalKilobytes, TagKey<Item> allowedTag) {
        super(idleDrain, bytesPerType, totalItemTypes, totalKilobytes, allowedTag);
    }

    @Override
    public Object getPrimaryKey(AEItemKey what) {
        Item item = what.getItem();
        try {
            return PRIMARY_KEY_CACHE.get(item, ()-> BuiltInRegistries.ITEM.getKey(item));
        } catch (ExecutionException e) {
            return BuiltInRegistries.ITEM.getKey(item);
        }
    }
}
