package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesTags;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.cowtype.BackGrassConfiguration;
import net.merchantpug.bovinesandbuttercups.api.cowtype.OffspringConditionsConfiguration;
import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.configuration.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.predicate.BlockInRadiusCondition;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BovinesCowTypes {
    public static class MoobloomKeys {
        public static final ResourceKey<CowType<?>> BIRD_OF_PARADISE = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("bird_of_paradise"));
        public static final ResourceKey<CowType<?>> BUTTERCUP = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("buttercup"));
        public static final ResourceKey<CowType<?>> CHARGELILY = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("chargelily"));
        public static final ResourceKey<CowType<?>> FREESIA = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("freesia"));
        public static final ResourceKey<CowType<?>> HYACINTH = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("hyacinth"));
        public static final ResourceKey<CowType<?>> LIMELIGHT = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("limelight"));
        public static final ResourceKey<CowType<?>> PINK_DAISY = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("pink_daisy"));
        public static final ResourceKey<CowType<?>> SNOWDROP = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("snowdrop"));
        public static final ResourceKey<CowType<?>> TROPICAL_BLUE = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("tropical_blue"));
        public static final ResourceKey<CowType<?>> MISSING_MOOBLOOM = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("missing_moobloom"));
    }

    public static class MooshroomKeys {
        public static final ResourceKey<CowType<?>> RED_MUSHROOM = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("red_mushroom"));
        public static final ResourceKey<CowType<?>> BROWN_MUSHROOM = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("brown_mushroom"));
        public static final ResourceKey<CowType<?>> MISSING_MOOSHROOM = ResourceKey.create(BovinesRegistryKeys.COW_TYPE, BovinesAndButtercups.asResource("missing_mooshroom"));
    }

    public static void bootstrap(BootstrapContext<CowType<?>> context) {
        // Moobloom Types
        context.register(MoobloomKeys.CHARGELILY, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), List.of(), Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 7584494))),
                new BlockReference<>(Optional.of(BovinesBlocks.CHARGELILY.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/chargelily_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/chargelily_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DIG_SPEED, 1200))),
                OffspringConditionsConfiguration.EMPTY)));

        Holder.Reference<CowType<?>> chargelilyMoobloom = context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MoobloomKeys.CHARGELILY);
        List<WeightedEntry.Wrapper<HolderSet<Biome>>> buttercupFlowerForestSet = List.of(WeightedEntry.wrap(context.lookup(Registries.BIOME).getOrThrow(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST), 7));
        List<WeightedEntry.Wrapper<HolderSet<Biome>>> pinkDaisyFlowerForestSet = List.of(WeightedEntry.wrap(context.lookup(Registries.BIOME).getOrThrow(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST), 1));
        List<WeightedEntry.Wrapper<Holder<CowType<?>>>> chargelilyWeighted = List.of(WeightedEntry.wrap(chargelilyMoobloom, 1));

        context.register(MoobloomKeys.BIRD_OF_PARADISE, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 15041842))),
                new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.SLOW_FALLING, 7200))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.MELON, Blocks.MELON_STEM),
                                BlockPredicate.Builder.block().of(Blocks.POPPY, Blocks.POTTED_POPPY),
                                BlockPredicate.Builder.block().of(Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, Blocks.ACACIA_SAPLING, Blocks.POTTED_ACACIA_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.BIRD_OF_PARADISE, BovinesBlocks.POTTED_BIRD_OF_PARADISE))),
                        List.of()))));
        context.register(MoobloomKeys.BUTTERCUP, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), buttercupFlowerForestSet, chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 16635136))),
                new BlockReference<>(Optional.of(BovinesBlocks.BUTTERCUP.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/buttercup_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.POISON, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.SUNFLOWER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)),
                                BlockPredicate.Builder.block().of(Blocks.DANDELION, Blocks.POTTED_DANDELION),
                                BlockPredicate.Builder.block().of(Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, Blocks.BIRCH_SAPLING, Blocks.POTTED_BIRCH_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.BUTTERCUP, BovinesBlocks.POTTED_BUTTERCUP))),
                        List.of()))));
        context.register(MoobloomKeys.FREESIA, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 15614261))),
                new BlockReference<>(Optional.of(BovinesBlocks.FREESIA.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/freesia_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/freesia_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.WATER_BREATHING, 9600))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.LILY_PAD),
                                BlockPredicate.Builder.block().of(Blocks.BLUE_ORCHID, Blocks.POTTED_BLUE_ORCHID),
                                BlockPredicate.Builder.block().of(Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD, Blocks.MANGROVE_PROPAGULE, Blocks.POTTED_MANGROVE_PROPAGULE)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.FREESIA, BovinesBlocks.POTTED_FREESIA))),
                        List.of()))));
        context.register(MoobloomKeys.HYACINTH, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 7027382))),
                new BlockReference<>(Optional.of(BovinesBlocks.HYACINTH.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/hyacinth_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/hyacinth_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.WITHER, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.ROSE_BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)),
                                BlockPredicate.Builder.block().of(Blocks.CORNFLOWER, Blocks.POTTED_CORNFLOWER),
                                BlockPredicate.Builder.block().of(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.HYACINTH, BovinesBlocks.POTTED_HYACINTH))),
                        List.of()))));
        context.register(MoobloomKeys.LIMELIGHT, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 10876830))),
                new BlockReference<>(Optional.of(BovinesBlocks.LIMELIGHT.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/limelight_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/limelight_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.REGENERATION, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT),
                                BlockPredicate.Builder.block().of(Blocks.BIG_DRIPLEAF, Blocks.SMALL_DRIPLEAF),
                                BlockPredicate.Builder.block().of(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.FLOWERING_AZALEA, Blocks.POTTED_FLOWERING_AZALEA)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.LIMELIGHT, BovinesBlocks.POTTED_LIMELIGHT))),
                        List.of()))));
        context.register(MoobloomKeys.PINK_DAISY, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), pinkDaisyFlowerForestSet, chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 16631260))),
                new BlockReference<>(Optional.of(BovinesBlocks.PINK_DAISY.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/pink_daisy_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/pink_daisy_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DAMAGE_BOOST, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.LILAC).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)),
                                BlockPredicate.Builder.block().of(Blocks.ALLIUM, Blocks.POTTED_ALLIUM, Blocks.PINK_TULIP, Blocks.POTTED_PINK_TULIP, Blocks.PINK_PETALS),
                                BlockPredicate.Builder.block().of(Blocks.OAK_LOG, Blocks.OAK_WOOD, Blocks.OAK_SAPLING, Blocks.POTTED_OAK_SAPLING,
                                        Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD, Blocks.CHERRY_SAPLING, Blocks.POTTED_CHERRY_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.PINK_DAISY, BovinesBlocks.POTTED_PINK_DAISY))),
                        List.of()))));
        context.register(MoobloomKeys.SNOWDROP, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 12635883))),
                new BlockReference<>(Optional.of(BovinesBlocks.SNOWDROP.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/snowdrop_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/snowdrop_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DIG_SLOWDOWN, 7200))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.SNOW_BLOCK, Blocks.SNOW),
                                BlockPredicate.Builder.block().of(Blocks.FERN, Blocks.POTTED_FERN),
                                BlockPredicate.Builder.block().of(Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, Blocks.SPRUCE_SAPLING, Blocks.POTTED_SPRUCE_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.SNOWDROP, BovinesBlocks.POTTED_SNOWDROP))),
                        List.of()))));
        context.register(MoobloomKeys.TROPICAL_BLUE, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 4279795))),
                new BlockReference<>(Optional.of(BovinesBlocks.TROPICAL_BLUE.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/tropical_blue_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/tropical_blue_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.FIRE_RESISTANCE, 9600))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                BlockPredicate.Builder.block().of(Blocks.COCOA),
                                BlockPredicate.Builder.block().of(Blocks.BAMBOO, Blocks.BAMBOO_SAPLING),
                                BlockPredicate.Builder.block().of(Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, Blocks.JUNGLE_SAPLING, Blocks.POTTED_JUNGLE_SAPLING)
                        ),
                        BlockPredicate.Builder.block().of(BovinesBlocks.TROPICAL_BLUE, BovinesBlocks.POTTED_TROPICAL_BLUE))),
                        List.of()))));

        // Mooshroom Types
        context.register(MooshroomKeys.RED_MUSHROOM, new CowType<>(BovinesCowTypeTypes.MOOSHROOM_TYPE, new MooshroomConfiguration(
                new CowTypeConfiguration.Settings(Optional.of(new ResourceLocation("cow/brown_mooshroom")), List.of(), List.of(WeightedEntry.wrap(context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MooshroomKeys.BROWN_MUSHROOM), 1)), Optional.of(ColorParticleOption.create(BovinesParticleTypes.SHROOM, 11014162))),
                new BlockReference<>(Optional.of(Blocks.RED_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/mooshroom/mooshroom_mycelium"), false)),
                false,
                true
        )));
        context.register(MooshroomKeys.BROWN_MUSHROOM, new CowType<>(BovinesCowTypeTypes.MOOSHROOM_TYPE, new MooshroomConfiguration(
                new CowTypeConfiguration.Settings(Optional.of(new ResourceLocation("cow/brown_mooshroom")), List.of(), List.of(WeightedEntry.wrap(context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MooshroomKeys.RED_MUSHROOM), 1)), Optional.of(ColorParticleOption.create(BovinesParticleTypes.SHROOM, 9397834))),
                new BlockReference<>(Optional.of(Blocks.BROWN_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/mooshroom/mooshroom_mycelium"), false)),
                true,
                false
        )));
    }

    private static LootItemCondition createCondition(List<BlockPredicate.Builder> blocks, BlockPredicate.Builder flowerBlocks) {
        return AnyOfCondition.anyOf(
                AllOfCondition.allOf(blocks.stream().map(predicate -> AnyOfCondition.anyOf(new BlockInRadiusCondition.Builder(predicate).withRadius(12, 6).withOffset(0, 1, 0))).toArray(LootItemCondition.Builder[]::new)),
                new BlockInRadiusCondition.Builder(flowerBlocks).withRadius(12, 6).withOffset(0, 1, 0)
        ).build();
    }

}
