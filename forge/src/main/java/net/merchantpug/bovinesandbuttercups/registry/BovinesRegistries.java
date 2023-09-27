package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class BovinesRegistries {

    public static final DeferredRegister<CowType<?>> COW_TYPE_DEFERRED = DeferredRegister.create(BovinesRegistryKeys.COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);
    public static Supplier<IForgeRegistry<CowType<?>>> COW_TYPE_REGISTRY;
    public static final DeferredRegister<ConfiguredCowType<?, ?>> CONFIGURED_COW_TYPE_DEFERRED = DeferredRegister.create(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);
    public static Supplier<IForgeRegistry<ConfiguredCowType<?, ?>>> CONFIGURED_COW_TYPE_REGISTRY;

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        COW_TYPE_REGISTRY = COW_TYPE_DEFERRED.makeRegistry(RegistryBuilder::new);
        CONFIGURED_COW_TYPE_REGISTRY = CONFIGURED_COW_TYPE_DEFERRED.makeRegistry(() -> new RegistryBuilder<ConfiguredCowType<?, ?>>().disableSaving().hasTags());
        COW_TYPE_DEFERRED.register(bus);
        CONFIGURED_COW_TYPE_DEFERRED.register(bus);
    }

}
