package net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.merchantpug.bovinesandbuttercups.client.registry.BovinesModelLayers;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.model.FlowerCrownModel;
import net.merchantpug.bovinesandbuttercups.client.util.BovinesAtlases;
import net.merchantpug.bovinesandbuttercups.content.component.FlowerCrown;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class FlowerCrownLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
    private final TextureAtlas petalsTextureAtlas;
    private final FlowerCrownModel<T> xModel;
    private final FlowerCrownModel<T> zModel;

    public FlowerCrownLayer(RenderLayerParent<T, M> renderer, Function<ModelLayerLocation, ModelPart> baker, ModelManager modelManager) {
        super(renderer);
        this.xModel = new FlowerCrownModel<>(baker.apply(BovinesModelLayers.FLOWER_CROWN_MODEL_LAYER));
        this.zModel = new FlowerCrownModel<>(baker.apply(BovinesModelLayers.FLOWER_CROWN_MODEL_LAYER));
        petalsTextureAtlas = modelManager.getAtlas(BovinesAtlases.PETALS_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (!stack.has(BovinesDataComponents.FLOWER_CROWN))
            return;
        FlowerCrown component = stack.get(BovinesDataComponents.FLOWER_CROWN);

        getParentModel().copyPropertiesTo(zModel);
        getParentModel().copyPropertiesTo(xModel);
        zModel.getHead().copyFrom(getParentModel().getHead());
        xModel.getHead().copyFrom(getParentModel().getHead());

        renderPart(zModel, poseStack, bufferSource, packedLight, 0, component);
        renderPart(zModel, poseStack, bufferSource, packedLight, 1, component);
        renderPart(zModel, poseStack, bufferSource, packedLight, 2, component);
        renderPart(zModel, poseStack, bufferSource, packedLight, 3, component);

        renderPart(xModel, poseStack, bufferSource, packedLight, 4, component);
        renderPart(xModel, poseStack, bufferSource, packedLight, 5, component);
        renderPart(xModel, poseStack, bufferSource, packedLight, 6, component);
        renderPart(xModel, poseStack, bufferSource, packedLight, 7, component);
    }

    private void renderPart(FlowerCrownModel<T> model, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int index, FlowerCrown crown) {
        TextureAtlasSprite sprite = this.petalsTextureAtlas.getSprite(crown.getEquippedTexture(index));
        VertexConsumer consumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(BovinesAtlases.PETALS_SHEET)));
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}