package house.greenhouse.bovinesandbuttercups.registry;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.component.ItemCustomFlower;
import house.greenhouse.bovinesandbuttercups.content.component.ItemCustomMushroom;
import house.greenhouse.bovinesandbuttercups.content.component.FlowerCrown;
import house.greenhouse.bovinesandbuttercups.content.component.ItemNectar;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class BovinesDataComponents {
    public static final DataComponentType<ItemNectar> NECTAR = DataComponentType.<ItemNectar>builder()
            .persistent(ItemNectar.CODEC)
            .networkSynchronized(ItemNectar.STREAM_CODEC)
            .build();
    public static final DataComponentType<ItemCustomFlower> CUSTOM_FLOWER = DataComponentType.<ItemCustomFlower>builder()
            .persistent(ItemCustomFlower.CODEC)
            .networkSynchronized(ItemCustomFlower.STREAM_CODEC)
            .build();
    public static final DataComponentType<ItemCustomMushroom> CUSTOM_MUSHROOM = DataComponentType.<ItemCustomMushroom>builder()
            .persistent(ItemCustomMushroom.CODEC)
            .networkSynchronized(ItemCustomMushroom.STREAM_CODEC)
            .build();
    public static final DataComponentType<FlowerCrown> FLOWER_CROWN = DataComponentType.<FlowerCrown>builder()
            .persistent(FlowerCrown.CODEC)
            .networkSynchronized(FlowerCrown.STREAM_CODEC)
            .build();

    public static void registerAll(RegistrationCallback<DataComponentType<?>> callback) {
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("custom_flower"), CUSTOM_FLOWER);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("custom_mushroom"), CUSTOM_MUSHROOM);
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("nectar"), NECTAR);;
        callback.register(BuiltInRegistries.DATA_COMPONENT_TYPE, BovinesAndButtercups.asResource("flower_crown"), FLOWER_CROWN);
    }
}
