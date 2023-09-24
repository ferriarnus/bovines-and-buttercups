package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class BovinesRegistryKeys {

    public static final ResourceKey<Registry<CowType<?>>> COW_TYPE_KEY = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("cow_type"));
    public static final ResourceKey<Registry<ConfiguredCowType<?, ?>>> CONFIGURED_COW_TYPE_KEY = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("configured_cow_type"));

}
