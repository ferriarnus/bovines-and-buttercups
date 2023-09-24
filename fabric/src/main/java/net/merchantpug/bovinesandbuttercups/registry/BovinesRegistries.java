package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableMappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.registries.VanillaRegistries;

public class BovinesRegistries {

    public static final Registry<CowType<?>> COW_TYPE_REGISTRY = FabricRegistryBuilder.createSimple(BovinesRegistryKeys.COW_TYPE_KEY).buildAndRegister();
    public static final Registry<ConfiguredCowType<?, ?>> CONFIGURED_COW_TYPE_REGISTRY = FabricRegistryBuilder.from(new ReloadableMappedRegistry<>(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, Lifecycle.stable())).buildAndRegister();

    public static void init() {
        DynamicRegistries.register(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, ConfiguredCowType.CODEC);

    }

}
