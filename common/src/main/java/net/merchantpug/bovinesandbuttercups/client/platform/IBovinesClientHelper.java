package net.merchantpug.bovinesandbuttercups.client.platform;

import net.merchantpug.bovinesandbuttercups.platform.ServiceUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public interface IBovinesClientHelper {
    IBovinesClientHelper INSTANCE = ServiceUtil.load(IBovinesClientHelper.class);

    BakedModel getModel(ResourceLocation resourceLocation);
    void setRenderLayer(Block block, RenderType renderType);
}
