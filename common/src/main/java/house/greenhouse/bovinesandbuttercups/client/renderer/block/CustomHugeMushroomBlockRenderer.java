package house.greenhouse.bovinesandbuttercups.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import house.greenhouse.bovinesandbuttercups.client.util.BovineStateModelUtil;
import house.greenhouse.bovinesandbuttercups.content.block.entity.CustomHugeMushroomBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public class CustomHugeMushroomBlockRenderer implements BlockEntityRenderer<CustomHugeMushroomBlockEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public CustomHugeMushroomBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void render(CustomHugeMushroomBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ResourceLocation resourceLocation = BovinesAndButtercups.asResource("bovinesandbuttercups/missing_mushroom_block/" + BovineStateModelUtil.acceptedStateProperties(BlockModelShaper.statePropertiesToString(blockEntity.getBlockState().getValues())));

        if (blockEntity.getMushroomType() != null) {
            Optional<ResourceLocation> modelLocationWithoutVariant = BovineStatesAssociationRegistry.getBlock(blockEntity.getMushroomType().holder().unwrapKey().get().location(), BovineBlockstateTypes.MUSHROOM_BLOCK);
            if (modelLocationWithoutVariant.isPresent())
                resourceLocation = modelLocationWithoutVariant.get();
        }

        BakedModel pottedMushroomModel = BovinesAndButtercupsClient.getHelper().getModel(resourceLocation);
        if (pottedMushroomModel == null)
            pottedMushroomModel = BovinesAndButtercupsClient.getHelper().getModel(BovinesAndButtercups.asResource("bovinesandbuttercups/missing_mushroom_block/" + BovineStateModelUtil.acceptedStateProperties(BlockModelShaper.statePropertiesToString(blockEntity.getBlockState().getValues()))));

        blockRenderDispatcher.getModelRenderer().tesselateBlock(blockEntity.getLevel(), pottedMushroomModel, blockEntity.getBlockState(), blockEntity.getBlockPos(), poseStack, bufferSource.getBuffer(RenderType.cutout()), false, RandomSource.create(), blockEntity.getBlockState().getSeed(blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY);
    }
}