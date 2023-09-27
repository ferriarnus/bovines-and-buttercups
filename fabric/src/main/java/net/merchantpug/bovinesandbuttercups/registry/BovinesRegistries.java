package net.merchantpug.bovinesandbuttercups.registry;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableMappedRegistry;
import net.minecraft.core.Registry;

public class BovinesRegistries {

    public static final Registry<CowType<?>> COW_TYPE_REGISTRY = FabricRegistryBuilder.createSimple(BovinesRegistryKeys.COW_TYPE_KEY).buildAndRegister();

    public static void init() {
        ReloadableMappedRegistry.RELOADABLE_KEYS.add(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY);
        DynamicRegistries.register(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, ConfiguredCowType.CODEC);
    }

}
