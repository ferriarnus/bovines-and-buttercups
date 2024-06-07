package net.merchantpug.bovinesandbuttercups.client.renderer.modifier;

import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifier;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.Entity;

import java.awt.*;
import java.util.Locale;

public class GrassTintTextureModifier implements TextureModifier {
    @Override
    public int color(Entity entity, int previousColor) {
        int argb = BiomeColors.getAverageGrassColor(entity.level(), entity.blockPosition());
        return (argb & 0xffffff) | (255 << 24);
    }
}
