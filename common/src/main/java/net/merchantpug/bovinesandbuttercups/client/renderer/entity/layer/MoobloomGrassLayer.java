package net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.bovinesandbuttercups.api.cowtype.CowModelLayer;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifier;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class MoobloomGrassLayer<T extends Moobloom, M extends CowModel<T>> extends RenderLayer<T, M> {

    public MoobloomGrassLayer(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, Moobloom entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.isInvisible() || !entity.getCowType().isBound() || entity.getCowType().value().configuration().layers().isEmpty())
            return;

        loop: for (CowModelLayer cowLayer : entity.getCowType().value().configuration().layers()) {
            RenderType renderType = RenderType.entityTranslucent(cowLayer.textureLocation().withPath(string -> "textures/entity/" + string + ".png"));
            int color = 0xFFFFFF;
            float a = 1.0F;
            for (TextureModifierFactory<?> factory : cowLayer.textureModifiers()) {
                if (!factory.canDisplay(entity))
                    continue loop;
                TextureModifier provider = factory.getOrCreateProvider();
                color = provider.color(entity, color);
                a = provider.alpha(entity, a);
                renderType = provider.renderType(cowLayer.textureLocation(), renderType);
            }
            float r = (color >> 16 & 0xFF) / 255.0F;
            float g = (color >> 8 & 0xFF) / 255.0F;
            float b = (color & 0xFF) / 255.0f;

            this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(renderType), light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), r, g, b, a);
        }
    }
}
