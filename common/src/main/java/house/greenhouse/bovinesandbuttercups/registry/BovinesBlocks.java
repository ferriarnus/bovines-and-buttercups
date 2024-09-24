package house.greenhouse.bovinesandbuttercups.registry;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.block.CandleCupcakeBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CupcakeBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CustomFlowerBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CustomFlowerPotBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CustomHugeMushroomBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CustomMushroomBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CustomMushroomPotBlock;
import house.greenhouse.bovinesandbuttercups.content.block.RichHoneyBlock;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BovinesBlocks {
    public static final FlowerBlock BUTTERCUP = new FlowerBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock PINK_DAISY = new FlowerBlock(MobEffects.DAMAGE_BOOST, 3, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock LIMELIGHT = new FlowerBlock(MobEffects.REGENERATION, 8, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock BIRD_OF_PARADISE = new FlowerBlock(MobEffects.SLOW_FALLING, 6, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock CHARGELILY = new FlowerBlock(MobEffects.MOVEMENT_SPEED, 4, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock HYACINTH = new FlowerBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock SNOWDROP = new FlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 5, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock TROPICAL_BLUE = new FlowerBlock(MobEffects.FIRE_RESISTANCE, 4, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock FREESIA = new FlowerBlock(MobEffects.WATER_BREATHING, 8, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final FlowerBlock LINGHOLM = new FlowerBlock(MobEffects.MOVEMENT_SPEED, 4, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));

    public static final FlowerPotBlock POTTED_BUTTERCUP = new FlowerPotBlock(BUTTERCUP, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_PINK_DAISY = new FlowerPotBlock(PINK_DAISY, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_LIMELIGHT = new FlowerPotBlock(LIMELIGHT, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_BIRD_OF_PARADISE = new FlowerPotBlock(BIRD_OF_PARADISE, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_CHARGELILY = new FlowerPotBlock(CHARGELILY, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_HYACINTH = new FlowerPotBlock(HYACINTH, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_SNOWDROP = new FlowerPotBlock(SNOWDROP, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_TROPICAL_BLUE = new FlowerPotBlock(TROPICAL_BLUE, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_FREESIA = new FlowerPotBlock(FREESIA, BlockBehaviour.Properties.of().instabreak().noOcclusion());
    public static final FlowerPotBlock POTTED_LINGHOLM = new FlowerPotBlock(LINGHOLM, BlockBehaviour.Properties.of().instabreak().noOcclusion());

    public static final CupcakeBlock BUTTERCUP_CUPCAKE = new CupcakeBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY));
    public static final CandleCupcakeBlock CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock WHITE_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.WHITE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock ORANGE_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock MAGENTA_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIGHT_BLUE_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock YELLOW_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIME_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.LIME_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock PINK_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.PINK_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock GRAY_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.GRAY_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIGHT_GRAY_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock CYAN_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.CYAN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock PURPLE_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BLUE_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.BLUE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BROWN_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.BROWN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock GREEN_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.GREEN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock RED_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.RED_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BLACK_CANDLE_BUTTERCUP_CUPCAKE = new CandleCupcakeBlock(BUTTERCUP_CUPCAKE, Blocks.BLACK_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(BUTTERCUP_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));

    public static final CupcakeBlock PINK_DAISY_CUPCAKE = new CupcakeBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY));
    public static final CandleCupcakeBlock CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock WHITE_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.WHITE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock ORANGE_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock MAGENTA_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIGHT_BLUE_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock YELLOW_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIME_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.LIME_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock PINK_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.PINK_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock GRAY_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.GRAY_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock LIGHT_GRAY_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock CYAN_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.CYAN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock PURPLE_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BLUE_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.BLUE_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BROWN_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.BROWN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock GREEN_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.GREEN_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock RED_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.RED_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));
    public static final CandleCupcakeBlock BLACK_CANDLE_PINK_DAISY_CUPCAKE = new CandleCupcakeBlock(PINK_DAISY_CUPCAKE, Blocks.BLACK_CANDLE, BlockBehaviour.Properties.ofLegacyCopy(PINK_DAISY_CUPCAKE).lightLevel(CandleBlock.LIGHT_EMISSION));

    public static final Block RICH_HONEY_BLOCK = new RichHoneyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).speedFactor(0.4F).jumpFactor(0.5F).noOcclusion().sound(SoundType.HONEY_BLOCK));

    public static final CustomFlowerBlock CUSTOM_FLOWER = new CustomFlowerBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final CustomFlowerPotBlock POTTED_CUSTOM_FLOWER = new CustomFlowerPotBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion());

    public static final CustomMushroomBlock CUSTOM_MUSHROOM = new CustomMushroomBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((value) -> 1));
    public static final CustomHugeMushroomBlock CUSTOM_MUSHROOM_BLOCK = new CustomHugeMushroomBlock(BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.WOOD));
    public static final CustomMushroomPotBlock POTTED_CUSTOM_MUSHROOM = new CustomMushroomPotBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion());

    public static void registerAll(RegistrationCallback<Block> callback) {
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("buttercup"), BUTTERCUP);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("pink_daisy"), PINK_DAISY);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("limelight"), LIMELIGHT);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("bird_of_paradise"), BIRD_OF_PARADISE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("chargelily"), CHARGELILY);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("hyacinth"), HYACINTH);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("snowdrop"), SNOWDROP);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("tropical_blue"), TROPICAL_BLUE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("freesia"), FREESIA);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("lingholm"), LINGHOLM);

        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("buttercup_cupcake"), BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("candle_buttercup_cupcake"), CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("white_candle_buttercup_cupcake"), WHITE_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("orange_candle_buttercup_cupcake"), ORANGE_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("magenta_candle_buttercup_cupcake"), MAGENTA_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("light_blue_candle_buttercup_cupcake"), LIGHT_BLUE_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("yellow_candle_buttercup_cupcake"), YELLOW_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("lime_candle_buttercup_cupcake"), LIME_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("pink_candle_buttercup_cupcake"), PINK_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("gray_candle_buttercup_cupcake"), GRAY_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("light_gray_candle_buttercup_cupcake"), LIGHT_GRAY_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("cyan_candle_buttercup_cupcake"), CYAN_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("purple_candle_buttercup_cupcake"), PURPLE_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("blue_candle_buttercup_cupcake"), BLUE_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("brown_candle_buttercup_cupcake"), BROWN_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("green_candle_buttercup_cupcake"), GREEN_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("red_candle_buttercup_cupcake"), RED_CANDLE_BUTTERCUP_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("black_candle_buttercup_cupcake"), BLACK_CANDLE_BUTTERCUP_CUPCAKE);

        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("pink_daisy_cupcake"), PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("candle_pink_daisy_cupcake"), CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("white_candle_pink_daisy_cupcake"), WHITE_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("orange_candle_pink_daisy_cupcake"), ORANGE_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("magenta_candle_pink_daisy_cupcake"), MAGENTA_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("light_blue_candle_pink_daisy_cupcake"), LIGHT_BLUE_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("yellow_candle_pink_daisy_cupcake"), YELLOW_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("lime_candle_pink_daisy_cupcake"), LIME_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("pink_candle_pink_daisy_cupcake"), PINK_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("gray_candle_pink_daisy_cupcake"), GRAY_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("light_gray_candle_pink_daisy_cupcake"), LIGHT_GRAY_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("cyan_candle_pink_daisy_cupcake"), CYAN_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("purple_candle_pink_daisy_cupcake"), PURPLE_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("blue_candle_pink_daisy_cupcake"), BLUE_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("brown_candle_pink_daisy_cupcake"), BROWN_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("green_candle_pink_daisy_cupcake"), GREEN_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("red_candle_pink_daisy_cupcake"), RED_CANDLE_PINK_DAISY_CUPCAKE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("black_candle_pink_daisy_cupcake"), BLACK_CANDLE_PINK_DAISY_CUPCAKE);

        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_buttercup"), POTTED_BUTTERCUP);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_pink_daisy"), POTTED_PINK_DAISY);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_limelight"), POTTED_LIMELIGHT);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_bird_of_paradise"), POTTED_BIRD_OF_PARADISE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_chargelily"), POTTED_CHARGELILY);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_hyacinth"), POTTED_HYACINTH);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_snowdrop"), POTTED_SNOWDROP);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_tropical_blue"), POTTED_TROPICAL_BLUE);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_freesia"), POTTED_FREESIA);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_lingholm"), POTTED_LINGHOLM);

        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("rich_honey_block"), RICH_HONEY_BLOCK);

        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("custom_flower"), CUSTOM_FLOWER);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_custom_flower"), POTTED_CUSTOM_FLOWER);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("custom_mushroom"), CUSTOM_MUSHROOM);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("custom_mushroom_block"), CUSTOM_MUSHROOM_BLOCK);
        callback.register(BuiltInRegistries.BLOCK, BovinesAndButtercups.asResource("potted_custom_mushroom"), POTTED_CUSTOM_MUSHROOM);
    }
}