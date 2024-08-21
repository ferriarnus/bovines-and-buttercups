package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.content.modifier.AddCowTypeSpawnsModifier;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BovinesBiomeModifierSerializers {
    public static void registerAll(RegistrationCallback<MapCodec<? extends BiomeModifier>> callback) {
        callback.register(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, AddCowTypeSpawnsModifier.ID, AddCowTypeSpawnsModifier.CODEC);
    }
}
