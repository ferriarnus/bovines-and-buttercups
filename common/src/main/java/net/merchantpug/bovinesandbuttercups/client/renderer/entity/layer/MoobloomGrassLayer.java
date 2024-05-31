package net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.BiomeColors;
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
        if (entity.isInvisible() || !entity.getMoobloomType().isBound() || entity.getMoobloomType().value().configuration().backGrass().isEmpty())
            return;
        float r = 1.0F;
        float g = 1.0F;
        float b = 1.0F;

        if (entity.getMoobloomType().value().configuration().backGrass().isPresent() && entity.getMoobloomType().value().configuration().backGrass().get().grassTinted()) {
            int biomeColor = BiomeColors.getAverageGrassColor(entity.level(), entity.blockPosition());
            r = (biomeColor >> 16 & 0xFF) / 255.0F;
            g = (biomeColor >> 8 & 0xFF) / 255.0F;
            b = (biomeColor & 0xFF) / 255.0f;
        }
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(entity.getMoobloomType().value().configuration().backGrass().get().textureLocation().withPath(string -> "textures/entity/" + string + ".png"))), light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), r, g, b, 1.0F);
    }
}
