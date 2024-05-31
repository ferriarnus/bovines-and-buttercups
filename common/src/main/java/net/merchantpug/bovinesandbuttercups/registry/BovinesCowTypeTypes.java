package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.configuration.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;

public class BovinesCowTypeTypes {
    public static final CowTypeType<MoobloomConfiguration> MOOBLOOM_TYPE = new CowTypeType<>(MoobloomConfiguration.CODEC, MoobloomConfiguration.DEFAULT);
    public static final CowTypeType<MooshroomConfiguration> MOOSHROOM_TYPE = new CowTypeType<>(MooshroomConfiguration.CODEC, MooshroomConfiguration.DEFAULT);

    public static void registerAll(RegistrationCallback<CowTypeType<?>> callback) {
        callback.register(BovinesRegistries.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"), MOOBLOOM_TYPE);
        callback.register(BovinesRegistries.COW_TYPE_TYPE, BovinesAndButtercups.asResource("mooshroom"), MOOSHROOM_TYPE);
    }
}