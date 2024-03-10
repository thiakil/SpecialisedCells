package com.thiakil.specialisedcells.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StorageManager extends SavedData {
    private static final String MANAGER_NAME = "specialisedcells_manager";
    public static final String DISKUUID = "drive_uuid";
    public static final String DISKDATA = "drive_data";
    public static final String DISKLIST = "drive_list";

    private final Map<UUID, DataStorage> disks;

    public StorageManager() {
        disks = new HashMap<>();
        this.setDirty();
    }

    private StorageManager(Map<UUID, DataStorage> disks) {
        this.disks = disks;
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag diskList = new ListTag();
        for (Map.Entry<UUID, DataStorage> entry : disks.entrySet()) {
            CompoundTag disk = new CompoundTag();

            disk.putUUID(DISKUUID, entry.getKey());
            disk.put(DISKDATA, entry.getValue().toNbt());
            diskList.add(disk);
        }

        nbt.put(DISKLIST, diskList);
        return nbt;
    }

    public static StorageManager readNbt(CompoundTag nbt) {
        Map<UUID, DataStorage> disks = new HashMap<>();
        ListTag diskList = nbt.getList(DISKLIST, CompoundTag.TAG_COMPOUND);
        for(int i = 0; i < diskList.size(); i++) {
            CompoundTag disk = diskList.getCompound(i);
            disks.put(disk.getUUID(DISKUUID), DataStorage.fromNbt(disk.getCompound(DISKDATA)));
        }
        return new StorageManager(disks);
    }

    public void updateDisk(UUID uuid, DataStorage dataStorage) {
        disks.put(uuid, dataStorage);
        setDirty();
    }

    public void removeDisk(UUID uuid) {
        disks.remove(uuid);
        setDirty();
    }

    public boolean hasUUID(UUID uuid) {
        return disks.containsKey(uuid);
    }

    public DataStorage getOrCreateDisk(UUID uuid) {
        if(!disks.containsKey(uuid)) {
            updateDisk(uuid, new DataStorage());
        }
        return disks.get(uuid);
    }

    public void modifyDisk(UUID diskID, ListTag stackKeys, long[] stackAmounts, long itemCount) {
        DataStorage diskToModify = getOrCreateDisk(diskID);
        if(stackKeys != null && stackAmounts != null) {
            diskToModify.stackKeys = stackKeys;
            diskToModify.stackAmounts = stackAmounts;
        }
        diskToModify.itemCount = itemCount;

        updateDisk(diskID, diskToModify);
    }

    public static StorageManager getInstance(MinecraftServer server) {
        ServerLevel world = Objects.requireNonNull(server.getLevel(ServerLevel.OVERWORLD), "Overworld not found");
        return world.getDataStorage().computeIfAbsent(new Factory<>(StorageManager::new, StorageManager::readNbt), MANAGER_NAME);
    }
}
