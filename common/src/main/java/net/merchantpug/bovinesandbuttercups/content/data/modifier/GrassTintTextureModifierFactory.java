package net.merchantpug.bovinesandbuttercups.content.data.modifier;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.client.renderer.modifier.GrassTintTextureModifier;

public class GrassTintTextureModifierFactory extends TextureModifierFactory<GrassTintTextureModifier> {
    public static final MapCodec<TextureModifierFactory<?>> CODEC = MapCodec.unit(GrassTintTextureModifierFactory::new);

    @Override
    protected GrassTintTextureModifier createProvider() {
        return new GrassTintTextureModifier();
    }

    @Override
    public MapCodec<? extends TextureModifierFactory<?>> codec() {
        return CODEC;
    }

}
