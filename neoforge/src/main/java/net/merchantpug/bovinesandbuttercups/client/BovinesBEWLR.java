package net.merchantpug.bovinesandbuttercups.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomFlowerItemRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomHugeMushroomItemRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.CustomMushroomItemRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.FlowerCrownItemRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.NectarBowlItemRenderer;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.FlowerCrownItem;
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

    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        switch (stack.getItem()) {
            case CustomFlowerItem customFlowerItem ->
                    CustomFlowerItemRenderer.render(stack, context, poseStack, bufferSource, light, overlay);
            case CustomMushroomItem customMushroomItem ->
                    CustomMushroomItemRenderer.render(stack, context, poseStack, bufferSource, light, overlay);
            case CustomHugeMushroomItem customHugeMushroomItem ->
                    CustomHugeMushroomItemRenderer.render(stack, context, poseStack, bufferSource, light, overlay);
            case NectarBowlItem nectarBowlItem ->
                    NectarBowlItemRenderer.render(stack, context, poseStack, bufferSource, light, overlay);
            case FlowerCrownItem flowerCrownItem ->
                    FlowerCrownItemRenderer.render(stack, context, poseStack, bufferSource, light, overlay);
            default -> {
            }
        }
    }
}
