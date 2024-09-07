package house.greenhouse.bovinesandbuttercups.client.platform;

import house.greenhouse.bovinesandbuttercups.client.platform.BovinesClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class BovinesClientHelperNeo implements BovinesClientHelper {
    @Override
    public BakedModel getModel(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(resourceLocation));
    }
}
