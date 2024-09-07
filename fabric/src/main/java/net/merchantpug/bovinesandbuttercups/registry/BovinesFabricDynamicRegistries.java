package net.merchantpug.bovinesandbuttercups.registry;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;

public class BovinesFabricDynamicRegistries {

    public static void init() {
        DynamicRegistries.registerSynced(BovinesRegistryKeys.COW_TYPE, CowType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE, CustomFlowerType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE, CustomMushroomType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.FLOWER_CROWN_PETAL, FlowerCrownPetal.DIRECT_CODEC);
    }

}
