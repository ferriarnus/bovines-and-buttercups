package net.merchantpug.bovinesandbuttercups.client.renderer.modifier;

import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifier;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.entity.Entity;

public class GrassTintTextureModifier implements TextureModifier {
    @Override
    public int color(Entity entity, int previousColor) {
        return BiomeColors.getAverageGrassColor(entity.level(), entity.blockPosition());
    }
}
