package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class BovinesCowTypeTypes {
    public static final CowTypeType<MoobloomConfiguration> MOOBLOOM_TYPE = new CowTypeType<>(MoobloomConfiguration.CODEC, List.of(BovinesEntityTypes.MOOBLOOM), BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM, MoobloomConfiguration.DEFAULT);
    public static final CowTypeType<MooshroomConfiguration> MOOSHROOM_TYPE = new CowTypeType<>(MooshroomConfiguration.CODEC, List.of(EntityType.MOOSHROOM), BovinesCowTypes.MooshroomKeys.MISSING_MOOSHROOM, MooshroomConfiguration.DEFAULT);

    public static void registerAll(RegistrationCallback<CowTypeType<?>> callback) {
        callback.register(BovinesRegistries.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"), MOOBLOOM_TYPE);
        callback.register(BovinesRegistries.COW_TYPE_TYPE, BovinesAndButtercups.asResource("mooshroom"), MOOSHROOM_TYPE);
    }
}