package house.greenhouse.bovinesandbuttercups.client.platform;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface BovinesClientHelper {
    BakedModel getModel(ResourceLocation resourceLocation);

    ItemStack getEquippedFlowerCrownForRendering(LivingEntity entity);
}
