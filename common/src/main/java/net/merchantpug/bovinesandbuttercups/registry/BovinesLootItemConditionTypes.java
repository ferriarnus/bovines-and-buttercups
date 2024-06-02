package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.predicate.BlockInRadiusCondition;
import net.merchantpug.bovinesandbuttercups.content.predicate.SnowingCondition;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class BovinesLootItemConditionTypes {
    public static void registerAll(RegistrationCallback<LootItemConditionType> callback) {
        callback.register(BuiltInRegistries.LOOT_CONDITION_TYPE, BovinesAndButtercups.asResource("block_in_radius"), BlockInRadiusCondition.TYPE);
        callback.register(BuiltInRegistries.LOOT_CONDITION_TYPE, BovinesAndButtercups.asResource("snowing"), SnowingCondition.TYPE);
    }
}
