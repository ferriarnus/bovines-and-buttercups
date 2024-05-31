package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class BovinesLootTables {
    public static final ResourceKey<LootTable> MOOBLOOM = ResourceKey.create(Registries.LOOT_TABLE, BovinesAndButtercups.asResource("entities/moobloom"));
    public static final ResourceKey<LootTable> RANCH = ResourceKey.create(Registries.LOOT_TABLE, BovinesAndButtercups.asResource("chests/ranch"));
}
