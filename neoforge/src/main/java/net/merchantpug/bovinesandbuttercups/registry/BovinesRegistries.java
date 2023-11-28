package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class BovinesRegistries {

    public static final DeferredRegister<CowType<?>> COW_TYPE_DEFERRED = DeferredRegister.create(BovinesRegistryKeys.COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);
    public static final Registry<CowType<?>> COW_TYPE_REGISTRY = COW_TYPE_DEFERRED.makeRegistry(RegistryBuilder::create);
    public static final DeferredRegister<ConfiguredCowType<?, ?>> CONFIGURED_COW_TYPE_DEFERRED = DeferredRegister.create(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);
    public static final Registry<ConfiguredCowType<?, ?>> CONFIGURED_COW_TYPE_REGISTRY = CONFIGURED_COW_TYPE_DEFERRED.makeRegistry(RegistryBuilder::create);

    public static void init(IEventBus eventBus) {
        COW_TYPE_DEFERRED.register(eventBus);
        CONFIGURED_COW_TYPE_DEFERRED.register(eventBus);
    }

}
