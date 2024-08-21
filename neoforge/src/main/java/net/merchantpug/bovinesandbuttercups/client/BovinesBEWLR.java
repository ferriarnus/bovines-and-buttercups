package net.merchantpug.bovinesandbuttercups.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomFlowerItemRendererHelper;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomHugeMushroomItemRendererHelper;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomMushroomItemRendererHelper;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.NectarBowlItemRendererHelper;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class BovinesBEWLR extends BlockEntityWithoutLevelRenderer {
    public static final BlockEntityWithoutLevelRenderer BLOCK_ENTITY_WITHOUT_LEVEL_RENDERER = new BovinesBEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    public static final IClientItemExtensions ITEM_EXTENSIONS = new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return BovinesBEWLR.BLOCK_ENTITY_WITHOUT_LEVEL_RENDERER;
        }
    };

    public BovinesBEWLR(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        switch (stack.getItem()) {
            case CustomFlowerItem customFlowerItem ->
                    CustomFlowerItemRendererHelper.render(stack, poseStack, bufferSource, light, overlay, transformType);
            case CustomMushroomItem customMushroomItem ->
                    CustomMushroomItemRendererHelper.render(stack, poseStack, bufferSource, light, overlay, transformType);
            case CustomHugeMushroomItem customHugeMushroomItem ->
                    CustomHugeMushroomItemRendererHelper.render(stack, poseStack, bufferSource, light, overlay, transformType);
            case NectarBowlItem nectarBowlItem ->
                    NectarBowlItemRendererHelper.render(stack, poseStack, bufferSource, light, overlay, transformType);
            default -> {
            }
        }
    }
}
