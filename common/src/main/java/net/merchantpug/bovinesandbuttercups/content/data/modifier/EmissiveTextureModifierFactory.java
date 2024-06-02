package net.merchantpug.bovinesandbuttercups.content.data.modifier;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.client.renderer.modifier.EmissiveTextureModifier;

public class EmissiveTextureModifierFactory extends TextureModifierFactory<EmissiveTextureModifier> {
    public static final MapCodec<TextureModifierFactory<?>> CODEC = MapCodec.unit(EmissiveTextureModifierFactory::new);

    @Override
    protected EmissiveTextureModifier createProvider() {
        return new EmissiveTextureModifier();
    }

    @Override
    public MapCodec<? extends TextureModifierFactory<?>> codec() {
        return CODEC;
    }

}
