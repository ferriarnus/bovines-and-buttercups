package net.merchantpug.bovinesandbuttercups.api.cowtype.color;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface TextureModifier {
    default int color(Entity entity, int previous) {
        return previous;
    }

    default float alpha(Entity entity, float previous) {
        return 1.0F;
    }

    default RenderType renderType(ResourceLocation location, RenderType previous) {
        return previous;
    }
}
