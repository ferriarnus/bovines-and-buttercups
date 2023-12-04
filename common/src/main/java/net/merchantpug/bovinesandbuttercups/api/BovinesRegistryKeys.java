package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class BovinesRegistryKeys {

    public static final ResourceKey<Registry<CowType<?>>> COW_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("cow_type"));
    public static final ResourceKey<Registry<ConfiguredCowType<?, ?>>> CONFIGURED_COW_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("configured_cow_type"));
    public static final ResourceKey<Registry<CustomFlowerType>> CUSTOM_FLOWER_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("flower_type"));
    public static final ResourceKey<Registry<CustomMushroomType>> CUSTOM_MUSHROOM_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("mushroom_type"));

}
