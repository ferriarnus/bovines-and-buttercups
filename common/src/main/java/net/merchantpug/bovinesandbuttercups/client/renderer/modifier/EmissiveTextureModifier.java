package net.merchantpug.bovinesandbuttercups.client.renderer.modifier;

import net.merchantpug.bovinesandbuttercups.api.cowtype.modifier.TextureModifier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EmissiveTextureModifier implements TextureModifier {
    @Override
    public RenderType renderType(ResourceLocation location, RenderType previous) {
        return RenderType.entityTranslucentEmissive(location);
    }
}
