package house.greenhouse.bovinesandbuttercups.registry;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import house.greenhouse.bovinesandbuttercups.api.CowType;
import house.greenhouse.bovinesandbuttercups.api.block.CustomFlowerType;
import house.greenhouse.bovinesandbuttercups.api.block.CustomMushroomType;
import house.greenhouse.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;

public class BovinesFabricDynamicRegistries {

    public static void init() {
        DynamicRegistries.registerSynced(BovinesRegistryKeys.COW_TYPE, CowType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE, CustomFlowerType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE, CustomMushroomType.DIRECT_CODEC);
        DynamicRegistries.registerSynced(BovinesRegistryKeys.FLOWER_CROWN_PETAL, FlowerCrownPetal.DIRECT_CODEC);
    }

}
