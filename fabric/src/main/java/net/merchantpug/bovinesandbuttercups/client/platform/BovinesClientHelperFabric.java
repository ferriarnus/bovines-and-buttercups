package net.merchantpug.bovinesandbuttercups.client.platform;

import net.merchantpug.bovinesandbuttercups.mixin.fabric.client.ModelManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class BovinesClientHelperFabric implements BovinesClientHelper {
    @Override
    public BakedModel getModel(ResourceLocation resourceLocation) {
        ModelManagerAccessor accessor = ((ModelManagerAccessor) Minecraft.getInstance().getModelManager());
        return accessor.getBakedRegistry().getOrDefault(resourceLocation, accessor.getMissingModel());
    }
}
