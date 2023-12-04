package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.content.cowtypes.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.cowtypes.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class BovinesCowTypes {


    public static final RegistrationProvider<CowType<?>> COW_TYPE = RegistrationProvider.get(BovinesResourceKeys.COW_TYPE, BovinesAndButtercups.MOD_ID);

    public static final Holder<CowType<MoobloomConfiguration>> MOOBLOOM_TYPE = register("moobloom", () -> new CowType<>(MoobloomConfiguration.CODEC, BovinesResourceKeys.MoobloomKeys.MISSING_MOOBLOOM.location(), MoobloomConfiguration.DEFAULT));
    public static final Holder<CowType<MooshroomConfiguration>> MOOSHROOM_TYPE = register("mooshroom", () -> new CowType<>(MooshroomConfiguration.CODEC, BovinesAndButtercups.asResource("missing_mooshroom"), MooshroomConfiguration.DEFAULT));


    public static void init() {
    }

    private static <CTC extends CowTypeConfiguration> Holder<CowType<CTC>> register(String name, Supplier<CowType<CTC>> cowType) {
        return COW_TYPE.register(name, cowType);
    }

    public static RegistrySetBuilder.RegistryBootstrap<ConfiguredCowType<?, ?>> bootstrap() {
        return bootstapContext -> BovinesRegistries.COW_TYPE_REGISTRY.entrySet().forEach(entry -> bootstapContext.register(ResourceKey.create(BovinesResourceKeys.CONFIGURED_COW_TYPE, entry.getValue().defaultConfiguredId()), entry.getValue().defaultConfigured().get()));
    }

}
