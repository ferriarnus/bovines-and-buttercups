package house.greenhouse.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.block.CustomFlowerType;
import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.model.MoobloomModel;
import house.greenhouse.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

public class MoobloomFlowerLayer extends RenderLayer<Moobloom, MoobloomModel> {
    private final BlockRenderDispatcher blockRenderer;
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public MoobloomFlowerLayer(RenderLayerParent<Moobloom, MoobloomModel> context, BlockRenderDispatcher blockRenderer) {
        super(context);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moobloom entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean bl;
        bl = Minecraft.getInstance().shouldEntityAppearGlowing(entity) && entity.isInvisible();
        if (entity.isInvisible() && !bl) return;

        MoobloomConfiguration configuration = entity.getCowType().value().configuration();

        int m = LivingEntityRenderer.getOverlayCoords(entity, 0.0f);

        Optional<BlockState> blockState;
        ResourceLocation resourceLocation = null;

        if (entity.isBaby()) {
            if (configuration.bud().modelLocation().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.bud().modelLocation().get(), BovineBlockstateTypes.GENERIC).map(rl -> rl.withPath(s -> s +"/")).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else if (configuration.bud().customType().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.bud().customType().map(holder -> holder.unwrapKey().orElse(CustomFlowerType.MISSING_KEY).location()).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower")), BovineBlockstateTypes.FLOWER).orElseGet(() -> BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower")).withPath(s -> s + "/");
            else if (configuration.flower().blockState().isEmpty())
                return;
            blockState = configuration.bud().blockState();
            handleMoobudRender(poseStack, buffer, entity, packedLight, bl, m, blockState, resourceLocation);
        } else {
            if (configuration.flower().modelLocation().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.bud().modelLocation().get(), BovineBlockstateTypes.GENERIC).map(rl -> rl.withPath(s -> s +"/")).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower/"));
            else if (configuration.flower().customType().isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getBlock(configuration.flower().customType().map(holder -> holder.unwrapKey().orElse(CustomFlowerType.MISSING_KEY).location()).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower")), BovineBlockstateTypes.FLOWER).orElseGet(() -> BovinesAndButtercups.asResource("bovinesandbuttercups/missing_flower")).withPath(s -> s + "/");
            else if (configuration.flower().blockState().isEmpty())
                return;
            blockState = configuration.flower().blockState();
            handleMoobloomRender(poseStack, buffer, entity, packedLight, bl, m, blockState, resourceLocation);
        }
    }

    private void handleMoobudRender(PoseStack poseStack, MultiBufferSource buffer, Moobloom entity, int i, boolean outlineAndInvisible, int overlay, Optional<BlockState> blockState, @Nullable ResourceLocation resourceLocation) {
        poseStack.pushPose();

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

    private void handleMoobloomRender(PoseStack poseStack, MultiBufferSource buffer, Moobloom entity, int i, boolean outlineAndInvisible, int overlay, Optional<BlockState> blockState, @Nullable ResourceLocation resourceLocation) {
        poseStack.pushPose();

        this.getParentModel().getBody().translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.12F, 0.33F);
        poseStack.mulPose(Axis.XN.rotationDegrees(90));
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

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return animationDefinition.looping() ? f % animationDefinition.lengthInSeconds() : f;
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
