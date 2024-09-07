package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomFlower;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomMushroom;
import net.merchantpug.bovinesandbuttercups.content.component.FlowerCrown;
import net.merchantpug.bovinesandbuttercups.content.component.ItemMoobloomType;
import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class BovinesDataComponents {
    public static final DataComponentType<ItemMoobloomType> MOOBLOOM_TYPE = DataComponentType.<ItemMoobloomType>builder()
            .persistent(ItemMoobloomType.CODEC)
            .networkSynchronized(ItemMoobloomType.STREAM_CODEC)
            .build();
    public static final DataComponentType<ItemCustomFlower> CUSTOM_FLOWER = DataComponentType.<ItemCustomFlower>builder()
            .persistent(ItemCustomFlower.CODEC)
            .networkSynchronized(ItemCustomFlower.STREAM_CODEC)
            .build();
    public static final DataComponentType<ItemCustomMushroom> CUSTOM_MUSHROOM = DataComponentType.<ItemCustomMushroom>builder()
            .persistent(ItemCustomMushroom.CODEC)
            .networkSynchronized(ItemCustomMushroom.STREAM_CODEC)
            .build();
    public static final DataComponentType<NectarEffects> NECTAR_EFFECTS = DataComponentType.<NectarEffects>builder()
            .persistent(NectarEffects.CODEC)
            .networkSynchronized(NectarEffects.STREAM_CODEC)
            .build();
    public static final DataComponentType<FlowerCrown> FLOWER_CROWN = DataComponentType.<FlowerCrown>builder()
            .persistent(FlowerCrown.CODEC)
            .networkSynchronized(FlowerCrown.STREAM_CODEC)
            .build();

    public static void registerAll(RegistrationCallback<DataComponentType<?>> callback) {
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("custom_flower"), CUSTOM_FLOWER);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("custom_mushroom"), CUSTOM_MUSHROOM);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("moobloom_type"), MOOBLOOM_TYPE);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("nectar_effects"), NECTAR_EFFECTS);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("flower_crown"), FLOWER_CROWN);
    }
}
