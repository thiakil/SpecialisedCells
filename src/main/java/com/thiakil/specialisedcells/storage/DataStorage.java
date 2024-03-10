package com.thiakil.specialisedcells.storage;

import com.thiakil.specialisedcells.cells.SpecialisedCellInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class DataStorage {

    public static final DataStorage EMPTY = new DataStorage();

    public ListTag stackKeys;
    public long[] stackAmounts;
    public long itemCount;

    public DataStorage() {
        stackKeys = new ListTag();
        stackAmounts = new long[0];
        itemCount = 0;
    }

    public DataStorage(ListTag stackKeys, long[] stackAmounts, long itemCount) {
        this.stackKeys = stackKeys;
        this.stackAmounts = stackAmounts;
        this.itemCount = itemCount;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(SpecialisedCellInventory.STACK_KEYS, stackKeys);
        nbt.putLongArray(SpecialisedCellInventory.STACK_AMOUNTS, stackAmounts);
        if(itemCount != 0) nbt.putLong(SpecialisedCellInventory.ITEM_COUNT_TAG, itemCount);

        return nbt;
    }

    public static DataStorage fromNbt(CompoundTag nbt) {
        long itemCount = 0;
        ListTag stackKeys = nbt.getList(SpecialisedCellInventory.STACK_KEYS, Tag.TAG_COMPOUND);
        long[] stackAmounts = nbt.getLongArray(SpecialisedCellInventory.STACK_AMOUNTS);
        if(nbt.contains(SpecialisedCellInventory.ITEM_COUNT_TAG))
            itemCount = nbt.getLong(SpecialisedCellInventory.ITEM_COUNT_TAG);

        return new DataStorage(stackKeys, stackAmounts, itemCount);
    }
}