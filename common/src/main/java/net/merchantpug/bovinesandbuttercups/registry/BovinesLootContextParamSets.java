package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BovinesLootContextParamSets {
    public static final LootContextParam<Entity> PARTNER = new LootContextParam<>(BovinesAndButtercups.asResource("partner"));
    public static final LootContextParam<Entity> CHILD = new LootContextParam<>(BovinesAndButtercups.asResource("child"));

    public static final LootContextParamSet BREEDING = LootContextParamSet.builder()
            .required(LootContextParams.THIS_ENTITY)
            .required(LootContextParams.ORIGIN)
            .required(PARTNER)
            .required(CHILD)
            .build();
}
