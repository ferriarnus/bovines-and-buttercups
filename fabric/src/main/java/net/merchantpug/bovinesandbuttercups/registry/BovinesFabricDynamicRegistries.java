package net.merchantpug.bovinesandbuttercups.registry;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;

public class BovinesFabricDynamicRegistries {

    public static void init() {
        DynamicRegistries.registerSynced(BovinesResourceKeys.CONFIGURED_COW_TYPE, ConfiguredCowType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesResourceKeys.CUSTOM_FLOWER_TYPE, CustomFlowerType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE, CustomMushroomType.DIRECT_CODEC);
    }

}
