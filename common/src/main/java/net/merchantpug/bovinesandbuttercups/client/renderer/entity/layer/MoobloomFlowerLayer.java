package net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MoobloomFlowerLayer<T extends Moobloom, M extends CowModel<T>> extends RenderLayer<T, M> {
    private final BlockRenderDispatcher blockRenderer;

    public MoobloomFlowerLayer(RenderLayerParent<T, M> context, BlockRenderDispatcher blockRenderer) {
        super(context);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean bl;
        bl = Minecraft.getInstance().shouldEntityAppearGlowing(entity) && entity.isInvisible();
        if (entity.isInvisible() && !bl) return;

        MoobloomConfiguration configuration = entity.getCowType().value().configuration();

        int m = LivingEntityRenderer.getOverlayCoords(entity, 0.0f);

        Optional<BlockState> blockState;
        ResourceLocation resourceLocation;

        if (entity.isBaby()) {
            if (configuration.bud().modelLocation().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.bud().modelLocation().get(), BovineBlockstateTypes.GENERIC).map(rl -> rl.withPath(s -> s +"/")).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else if (configuration.bud().customType().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(entity.level().registryAccess().registry(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE).orElseThrow().getKey(configuration.bud().customType().get().value()), BovineBlockstateTypes.FLOWER).map(rl -> rl.withPath(s -> "bovinesandbuttercups/" + s +"/")).orElseGet(() -> BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else
                resourceLocation = BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/");
            blockState = configuration.bud().blockState();
            handleMoobudRender(poseStack, buffer, entity, packedLight, bl, m, blockState, resourceLocation);
        } else {
            if (configuration.flower().modelLocation().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.bud().modelLocation().get(), BovineBlockstateTypes.GENERIC).map(rl -> rl.withPath(s -> s +"/")).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else if (configuration.flower().customType().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(entity.level().registryAccess().registry(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE).orElseThrow().getKey(configuration.flower().customType().get().value()), BovineBlockstateTypes.FLOWER).map(rl -> rl.withPath(s -> "bovinesandbuttercups/" + s +"/")).orElseGet(() -> BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else
                resourceLocation = BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/");
            blockState = configuration.flower().blockState();
            handleMoobloomRender(poseStack, buffer, entity, packedLight, bl, m, blockState, resourceLocation);
        }
    }

    private void handleMoobudRender(PoseStack poseStack, MultiBufferSource buffer, T entity, int i, boolean outlineAndInvisible, int overlay, Optional<BlockState> blockState, @Nullable ResourceLocation resourceLocation) {
        poseStack.pushPose();
        if (entity.getStandingStillForBeeTicks() > 0) {
            poseStack.translate(0.0f, 11.0f / 16.0f, 0.0f);
        }

        poseStack.pushPose();
        poseStack.translate(0.2f, 0.35f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-1.0f, -1.0f, -1.0f);
        poseStack.translate(0.25f, 0.37, -0.25f);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.2f, 0.35f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-1.0f, -1.0f, -1.0f);
        poseStack.translate(0.25f, 0.37, 0.05f);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.0f, 0.625, 0.25f);
        this.getParentModel().getHead().translateAndRotate(poseStack);
        poseStack.translate(0.0, -0.7f, -0.2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(-0.05, -0.12, 0.15);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();
    }

    private void handleMoobloomRender(PoseStack poseStack, MultiBufferSource buffer, T entity, int i, boolean outlineAndInvisible, int overlay, Optional<BlockState> blockState, @Nullable ResourceLocation resourceLocation) {
        poseStack.pushPose();
        if (entity.getStandingStillForBeeTicks() > 0) {
            poseStack.translate(0.0f, 11.0f / 16.0f, 0.0f);
        }

        poseStack.pushPose();
        poseStack.translate(0.2f, -0.35f, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(-0.65, -0.18, -0.55);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.2f, -0.35f, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(-0.03, -0.18, -0.85);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.2f, -0.35f, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(0.15, -0.18, -0.2);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();
        poseStack.popPose();

        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        poseStack.translate(0.0, -0.7f, -0.2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        poseStack.scale(-0.75f, -0.75f, 0.75f);
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.translate(-0.05, -0.17, 0.15);
        this.renderFlowerOrBud(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, resourceLocation);
        poseStack.popPose();
    }

    private void renderFlowerOrBud(PoseStack poseStack, MultiBufferSource buffer, int light, boolean outlineAndInvisible, BlockRenderDispatcher blockRenderDispatcher, int overlay, Optional<BlockState> flowerState, ResourceLocation resourceLocation) {
        BakedModel flowerModel = flowerState.map(blockRenderDispatcher::getBlockModel).orElseGet(() -> BovinesAndButtercupsClient.getHelper().getModel(resourceLocation));

        if (flowerModel == null)
            flowerModel = BovinesAndButtercupsClient.getHelper().getModel(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));

        if (outlineAndInvisible)
            blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.outline(InventoryMenu.BLOCK_ATLAS)), null, flowerModel, 0.0f, 0.0f, 0.0f, light, overlay);
        else {
            if (flowerState.isPresent())
                blockRenderDispatcher.renderSingleBlock(flowerState.get(), poseStack, buffer, light, overlay);
            else
                blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), null, flowerModel, 1.0F, 1.0F, 1.0F, light, overlay);
        }
    }
}
