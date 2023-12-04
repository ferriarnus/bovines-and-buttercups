package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;

public class BovinesCustomFlowerTypes {
    public static RegistrySetBuilder.RegistryBootstrap<CustomFlowerType> bootstrap() {
        return bootstapContext -> bootstapContext.register(ResourceKey.create(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE, BovinesAndButtercups.asResource("missing")), CustomFlowerType.MISSING);
    }

}
