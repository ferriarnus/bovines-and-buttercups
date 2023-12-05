package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomFlowerBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomFlowerPotBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomHugeMushroomBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomMushroomBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomMushroomPotBlockEntity;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BovinesBlockEntityTypes {
    private static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, BovinesAndButtercups.MOD_ID);


    public static final Holder<BlockEntityType<CustomFlowerBlockEntity>> CUSTOM_FLOWER = register("custom_flower", () -> BlockEntityType.Builder.of(CustomFlowerBlockEntity::new, BovinesBlocks.CUSTOM_FLOWER.value()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, BovinesAndButtercups.asResource("custom_flower").toString())));
    public static final Holder<BlockEntityType<CustomMushroomBlockEntity>> CUSTOM_MUSHROOM = register("custom_mushroom", () -> BlockEntityType.Builder.of(CustomMushroomBlockEntity::new, BovinesBlocks.CUSTOM_MUSHROOM.value()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, BovinesAndButtercups.asResource("custom_mushroom").toString())));
    public static final Holder<BlockEntityType<CustomFlowerPotBlockEntity>> POTTED_CUSTOM_FLOWER = register("potted_custom_flower", () -> BlockEntityType.Builder.of(CustomFlowerPotBlockEntity::new, BovinesBlocks.POTTED_CUSTOM_FLOWER.value()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, BovinesAndButtercups.asResource("custom_potted_flower").toString())));
    public static final Holder<BlockEntityType<CustomMushroomPotBlockEntity>> POTTED_CUSTOM_MUSHROOM = register("potted_custom_mushroom", () -> BlockEntityType.Builder.of(CustomMushroomPotBlockEntity::new, BovinesBlocks.POTTED_CUSTOM_MUSHROOM.value()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, BovinesAndButtercups.asResource("custom_potted_mushroom").toString())));
    public static final Holder<BlockEntityType<CustomHugeMushroomBlockEntity>> CUSTOM_MUSHROOM_BLOCK = register("custom_mushroom_block", () -> BlockEntityType.Builder.of(CustomHugeMushroomBlockEntity::new, BovinesBlocks.CUSTOM_MUSHROOM_BLOCK.value()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, BovinesAndButtercups.asResource("custom_mushroom_block").toString())));

    public static void register() {

    }

    private static <T extends BlockEntity> Holder<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return BLOCK_ENTITY_TYPES.register(name, type);
    }
}
