package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.block.CustomFlowerBlock;
import net.merchantpug.bovinesandbuttercups.content.block.CustomFlowerPotBlock;
import net.merchantpug.bovinesandbuttercups.content.block.CustomHugeMushroomBlock;
import net.merchantpug.bovinesandbuttercups.content.block.CustomMushroomBlock;
import net.merchantpug.bovinesandbuttercups.content.block.CustomMushroomPotBlock;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class BovinesBlocks {
    private static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, BovinesAndButtercups.MOD_ID);

    public static final Holder<FlowerBlock> BUTTERCUP = register("buttercup", () -> new FlowerBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> PINK_DAISY = register("pink_daisy", () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 3, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> LIMELIGHT = register("limelight", () -> new FlowerBlock(MobEffects.REGENERATION, 8, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> BIRD_OF_PARADISE = register("bird_of_paradise", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 6, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> CHARGELILY = register("chargelily", () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 4, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> HYACINTH = register("hyacinth", () -> new FlowerBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> SNOWDROP = register("snowdrop", () -> new FlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 5, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> TROPICAL_BLUE = register("tropical_blue", () -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 4, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<FlowerBlock> FREESIA = register("freesia", () -> new FlowerBlock(MobEffects.WATER_BREATHING, 8, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final Holder<FlowerPotBlock> POTTED_BUTTERCUP = register("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_PINK_DAISY = register("potted_pink_daisy", () -> new FlowerPotBlock(PINK_DAISY.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_LIMELIGHT = register("potted_limelight", () -> new FlowerPotBlock(LIMELIGHT.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_BIRD_OF_PARADISE = register("potted_bird_of_paradise", () -> new FlowerPotBlock(BIRD_OF_PARADISE.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_CHARGELILY = register("potted_chargelily", () -> new FlowerPotBlock(CHARGELILY.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_HYACINTH = register("potted_hyacinth", () -> new FlowerPotBlock(HYACINTH.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_SNOWDROP = register("potted_snowdrop", () -> new FlowerPotBlock(SNOWDROP.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_TROPICAL_BLUE = register("potted_tropical_blue", () -> new FlowerPotBlock(TROPICAL_BLUE.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Holder<FlowerPotBlock> POTTED_FREESIA = register("potted_freesia", () -> new FlowerPotBlock(FREESIA.value(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));


    public static final Holder<CustomFlowerBlock> CUSTOM_FLOWER = register("custom_flower", () -> new CustomFlowerBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Holder<CustomFlowerPotBlock> POTTED_CUSTOM_FLOWER = register("potted_custom_flower", () -> new CustomFlowerPotBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    public static final Holder<CustomMushroomBlock> CUSTOM_MUSHROOM = register("custom_mushroom", () -> new CustomMushroomBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((value) -> 1)));
    public static final Holder<CustomHugeMushroomBlock> CUSTOM_MUSHROOM_BLOCK = register("custom_mushroom_block", () -> new CustomHugeMushroomBlock(BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.WOOD)));
    public static final Holder<CustomMushroomPotBlock> POTTED_CUSTOM_MUSHROOM = register("potted_custom_mushroom", () -> new CustomMushroomPotBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    public static void init() {

    }

    private static <T extends Block> Holder<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
}
