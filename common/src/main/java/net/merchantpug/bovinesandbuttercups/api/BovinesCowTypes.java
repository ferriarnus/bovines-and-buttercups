package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistryObject;

import java.util.function.Supplier;

public class BovinesCowTypes {


    public static final RegistrationProvider<CowType<?>> COW_TYPE = RegistrationProvider.get(BovinesRegistryKeys.COW_TYPE_KEY, BovinesAndButtercups.MOD_ID);

    public static final Supplier<CowType<MoobloomConfiguration>> MOOBLOOM_TYPE = register("moobloom", () -> new CowType<>("moobloom", MoobloomConfiguration.CODEC, BovinesAndButtercups.asResource("missing_moobloom"), MoobloomConfiguration.DEFAULT));

    public static void register() {
    }

    private static <CTC extends CowTypeConfiguration> RegistryObject<CowType<CTC>> register(String name, Supplier<CowType<CTC>> cowType) {
        return COW_TYPE.register(name, cowType);
    }

}
