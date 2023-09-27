package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.platform.Services;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistryObject;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class BovinesCowTypes {


    public static final RegistrationProvider<CowType<?>> COW_TYPE = RegistrationProvider.get(BovinesRegistryKeys.COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);

    public static final Supplier<CowType<MoobloomConfiguration>> MOOBLOOM_TYPE = register("moobloom", () -> new CowType<>("moobloom", MoobloomConfiguration.CODEC, BovinesAndButtercups.asResource("missing_moobloom"), MoobloomConfiguration.DEFAULT));

    public static void register() {
    }

    private static <CTC extends CowTypeConfiguration> RegistryObject<CowType<CTC>> register(String name, Supplier<CowType<CTC>> cowType) {
        return COW_TYPE.register(name, cowType);
    }

    public static RegistrySetBuilder.RegistryBootstrap<ConfiguredCowType<?, ?>> bootstrap() {
        return bootstapContext -> Services.PLATFORM.ctEntrySet().forEach(entry -> bootstapContext.register(ResourceKey.create(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, entry.getValue().defaultConfiguredId()), entry.getValue().defaultConfigured().get()));
    }

}
