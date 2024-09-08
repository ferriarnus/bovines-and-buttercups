package house.greenhouse.bovinesandbuttercups.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.BovinesCowTypeTypes;
import house.greenhouse.bovinesandbuttercups.api.CowType;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.api.block.CustomMushroomType;
import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import house.greenhouse.bovinesandbuttercups.content.data.configuration.MooshroomConfiguration;
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
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MooshroomDatapackMushroomLayer<T extends MushroomCow> extends RenderLayer<T, CowModel<T>> {
    private final BlockRenderDispatcher blockRenderer;

    public MooshroomDatapackMushroomLayer(RenderLayerParent<T, CowModel<T>> context, BlockRenderDispatcher blockRenderer) {
        super(context);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean bl = Minecraft.getInstance().shouldEntityAppearGlowing(entity) && entity.isInvisible();
        Holder<CowType<MooshroomConfiguration>> cowType = CowTypeAttachment.getCowTypeHolderFromEntity(entity, BovinesCowTypeTypes.MOOSHROOM_TYPE);
        if (cowType == null || entity.isInvisible() && !bl
                || entity.isBaby()
                || cowType.value().configuration().mushroom().blockState().isPresent() && cowType.value().configuration().mushroom().blockState().get().equals(cowType.value().configuration().vanillaType().get().getBlockState()))
            return;

        int m = LivingEntityRenderer.getOverlayCoords(entity, 0.0f);

        ResourceLocation modelResourceLocation = null;
        if (cowType.value().configuration().mushroom().modelLocation().isPresent())
            modelResourceLocation = cowType.value().configuration().mushroom().modelLocation().get().withPath(str -> str + "/");
        else if (cowType.value().configuration().mushroom().customType().isPresent())
            modelResourceLocation = BovineStatesAssociationRegistry.getBlock(cowType.value().configuration().mushroom().customType().get().unwrapKey().orElse(CustomMushroomType.MISSING_KEY).location(), BovineBlockstateTypes.MUSHROOM).orElseGet(() -> BovinesAndButtercups.asResource("bovinesandbuttercups/missing_mushroom")).withPath(str -> str + "/");
        else if (cowType.value().configuration().mushroom().blockState().isEmpty())
            return;

        handleMooshroomRender(poseStack, buffer, packedLight, bl, m, cowType.value().configuration().mushroom().blockState(), modelResourceLocation);
    }

    private void handleMooshroomRender(PoseStack poseStack, MultiBufferSource buffer, int i, boolean outlineAndInvisible, int overlay, Optional<BlockState> blockState, @Nullable ResourceLocation modelResourceLocation) {
        poseStack.pushPose();
        poseStack.translate(0.2F, -0.35F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        this.renderMushroomBlock(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, modelResourceLocation);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.2F, -0.35F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(42.0F));
        poseStack.translate(0.1F, 0.0F, -0.6F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        this.renderMushroomBlock(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, modelResourceLocation);
        poseStack.popPose();

        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.7F, -0.2F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-78.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        this.renderMushroomBlock(poseStack, buffer, i, outlineAndInvisible, blockRenderer, overlay, blockState, modelResourceLocation);
        poseStack.popPose();
    }

    private void renderMushroomBlock(PoseStack poseStack, MultiBufferSource buffer, int light, boolean outlineAndInvisible, BlockRenderDispatcher blockRenderDispatcher, int overlay, Optional<BlockState> mushroomState, ResourceLocation resourceLocation) {
        BakedModel mushroomModel = mushroomState.map(blockRenderDispatcher::getBlockModel).orElseGet(() -> BovinesAndButtercupsClient.getHelper().getModel(resourceLocation));

        if (outlineAndInvisible) {
            blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.outline(InventoryMenu.BLOCK_ATLAS)), null, mushroomModel, 0.0f, 0.0f, 0.0f, light, overlay);
        } else {
            if (mushroomState.isPresent()) {
                blockRenderDispatcher.renderSingleBlock(mushroomState.get(), poseStack, buffer, light, overlay);
            } else {
                blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), null, mushroomModel, 1.0F, 1.0F, 1.0F, light, overlay);
            }
        }
    }
}