package house.greenhouse.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.content.data.modifier.ConditionedTextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.content.data.modifier.EmissiveTextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.content.data.modifier.FallbackTextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.content.data.modifier.GrassTintTextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;

public class BovinesTextureModificationFactories {

    public static void registerAll(RegistrationCallback<MapCodec<? extends TextureModifierFactory<?>>> callback) {
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("conditioned"), ConditionedTextureModifierFactory.CODEC);
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("emissive"), EmissiveTextureModifierFactory.CODEC);
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("fallback"), FallbackTextureModifierFactory.CODEC);
        callback.register(BovinesRegistries.TEXTURE_MODIFIER, BovinesAndButtercups.asResource("grass_tint"), GrassTintTextureModifierFactory.CODEC);
    }
}
