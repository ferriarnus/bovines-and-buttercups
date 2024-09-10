package house.greenhouse.bovinesandbuttercups.client.platform;

import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class BovinesClientHelperNeoForge implements BovinesClientHelper {
    @Override
    public BakedModel getModel(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(resourceLocation));
    }

    @Override
    public ItemStack getEquippedFlowerCrownForRendering(LivingEntity entity) {
        if (ModList.get().isLoaded("accessories")) {
            var accessoriesCapability = AccessoriesCapability.getOptionally(entity);
            if (accessoriesCapability.isPresent()) {
                var flowerCrowns = accessoriesCapability.get().getEquipped(stack -> stack.has(BovinesDataComponents.FLOWER_CROWN));
                if (!flowerCrowns.isEmpty())
                    return flowerCrowns.getFirst().stack();
            }
        }

        if (ModList.get().isLoaded("curios")) {
            var curiosInventory = CuriosApi.getCuriosInventory(entity);
            if (curiosInventory.isPresent()) {
                var flowerCrown = curiosInventory.get().findFirstCurio(stack -> stack.has(BovinesDataComponents.FLOWER_CROWN));
                if (flowerCrown.isPresent())
                    return flowerCrown.get().stack();
            }
        }

        if (entity.getItemBySlot(EquipmentSlot.HEAD).has(BovinesDataComponents.FLOWER_CROWN))
            return entity.getItemBySlot(EquipmentSlot.HEAD);

        return ItemStack.EMPTY;
    }
}
