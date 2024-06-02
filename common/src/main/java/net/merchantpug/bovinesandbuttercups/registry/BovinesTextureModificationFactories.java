package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.content.data.modifier.ConditionedModifierFactory;
import net.merchantpug.bovinesandbuttercups.content.data.modifier.EmissiveTextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.content.data.modifier.GrassTintTextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;

public class BovinesTextureModificationFactories {

    public static void registerAll(RegistrationCallback<MapCodec<? extends TextureModifierFactory<?>>> callback) {
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("emissive"), EmissiveTextureModifierFactory.CODEC);
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("grass_tint"), GrassTintTextureModifierFactory.CODEC);
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("conditioned"), ConditionedModifierFactory.CODEC);
    }
}
