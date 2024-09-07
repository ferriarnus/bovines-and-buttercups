package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class BovinesRegistryKeys {
    public static final ResourceKey<Registry<CowTypeType<?>>> COW_TYPE_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("cow_type_type"));
    public static final ResourceKey<Registry<MapCodec<? extends TextureModifierFactory<?>>>> TEXTURE_MODIFIER = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("texture_modifier"));

    public static final ResourceKey<Registry<CowType<?>>> COW_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("cow_type"));
    public static final ResourceKey<Registry<CustomFlowerType>> CUSTOM_FLOWER_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("flower_type"));
    public static final ResourceKey<Registry<CustomMushroomType>> CUSTOM_MUSHROOM_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("mushroom_type"));
    public static final ResourceKey<Registry<FlowerCrownPetal>> FLOWER_CROWN_PETAL = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("flower_crown_petal"));
}
