package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.datafixers.util.Either;
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
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
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
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), List.of(), Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 7584494))),
                new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DIG_SPEED, 1200))),
                OffspringConditionsConfiguration.EMPTY)));

        Holder.Reference<CowType<MoobloomConfiguration>> chargelilyMoobloom = (Holder.Reference)context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MoobloomKeys.CHARGELILY);
        List<WeightedEntry.Wrapper<HolderSet<Biome>>> buttercupFlowerForestSet = List.of(WeightedEntry.wrap(context.lookup(Registries.BIOME).getOrThrow(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST), 7));
        List<WeightedEntry.Wrapper<HolderSet<Biome>>> pinkDaisyFlowerForestSet = List.of(WeightedEntry.wrap(context.lookup(Registries.BIOME).getOrThrow(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST), 1));
        List<CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration>> chargelilyWeighted = List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>(chargelilyMoobloom, 1));

        context.register(MoobloomKeys.BIRD_OF_PARADISE, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 15041842))),
                new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.SLOW_FALLING, 7200))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.left(Blocks.MELON.builtInRegistryHolder()), Either.left(Blocks.MELON_STEM.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.POPPY.builtInRegistryHolder()), Either.left(Blocks.POTTED_POPPY.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.ACACIA_LOG.builtInRegistryHolder()), Either.left(Blocks.ACACIA_WOOD.builtInRegistryHolder()), Either.left(Blocks.ACACIA_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_ACACIA_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.BIRD_OF_PARADISE.builtInRegistryHolder(), BovinesBlocks.POTTED_BIRD_OF_PARADISE.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.BUTTERCUP, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), buttercupFlowerForestSet, chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 16635136))),
                new BlockReference<>(Optional.of(BovinesBlocks.BUTTERCUP.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/buttercup_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.POISON, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.right(Blocks.SUNFLOWER.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                List.of(Either.left(Blocks.DANDELION.builtInRegistryHolder()), Either.left(Blocks.POTTED_DANDELION.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.BIRCH_LOG.builtInRegistryHolder()), Either.left(Blocks.BIRCH_WOOD.builtInRegistryHolder()), Either.left(Blocks.BIRCH_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_BIRCH_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.BUTTERCUP.builtInRegistryHolder(), BovinesBlocks.POTTED_BUTTERCUP.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.FREESIA, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 15614261))),
                new BlockReference<>(Optional.of(BovinesBlocks.FREESIA.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/freesia_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/freesia_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.WATER_BREATHING, 9600))),
                new OffspringConditionsConfiguration(List.of(createCondition(                            List.of(
                                List.of(Either.left(Blocks.LILY_PAD.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.BLUE_ORCHID.builtInRegistryHolder()), Either.left(Blocks.POTTED_BLUE_ORCHID.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.MANGROVE_LOG.builtInRegistryHolder()), Either.left(Blocks.MANGROVE_WOOD.builtInRegistryHolder()), Either.left(Blocks.MANGROVE_PROPAGULE.builtInRegistryHolder()), Either.left(Blocks.POTTED_MANGROVE_PROPAGULE.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.FREESIA.builtInRegistryHolder(), BovinesBlocks.POTTED_FREESIA.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.HYACINTH, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 7027382))),
                new BlockReference<>(Optional.of(BovinesBlocks.HYACINTH.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/hyacinth_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/hyacinth_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.WITHER, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.right(Blocks.ROSE_BUSH.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                List.of(Either.left(Blocks.CORNFLOWER.builtInRegistryHolder()), Either.left(Blocks.POTTED_CORNFLOWER.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.DARK_OAK_LOG.builtInRegistryHolder()), Either.left(Blocks.DARK_OAK_WOOD.builtInRegistryHolder()), Either.left(Blocks.DARK_OAK_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_DARK_OAK_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.HYACINTH.builtInRegistryHolder(), BovinesBlocks.POTTED_HYACINTH.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.LIMELIGHT, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 10876830))),
                new BlockReference<>(Optional.of(BovinesBlocks.LIMELIGHT.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/limelight_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/limelight_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.REGENERATION, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.left(Blocks.CAVE_VINES.builtInRegistryHolder()), Either.left(Blocks.CAVE_VINES_PLANT.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.BIG_DRIPLEAF.builtInRegistryHolder()), Either.left(Blocks.SMALL_DRIPLEAF.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.FLOWERING_AZALEA_LEAVES.builtInRegistryHolder()), Either.left(Blocks.FLOWERING_AZALEA.builtInRegistryHolder()), Either.left(Blocks.POTTED_FLOWERING_AZALEA.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.LIMELIGHT.builtInRegistryHolder(), BovinesBlocks.POTTED_LIMELIGHT.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.PINK_DAISY, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), pinkDaisyFlowerForestSet, chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 16631260))),
                new BlockReference<>(Optional.of(BovinesBlocks.PINK_DAISY.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/pink_daisy_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/pink_daisy_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DAMAGE_BOOST, 2400))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.right(Blocks.LILAC.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                List.of(Either.left(Blocks.ALLIUM.builtInRegistryHolder()), Either.left(Blocks.POTTED_ALLIUM.builtInRegistryHolder()), Either.left(Blocks.PINK_PETALS.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.OAK_LOG.builtInRegistryHolder()), Either.left(Blocks.OAK_WOOD.builtInRegistryHolder()), Either.left(Blocks.OAK_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_OAK_SAPLING.builtInRegistryHolder()),
                                        Either.left(Blocks.CHERRY_LOG.builtInRegistryHolder()), Either.left(Blocks.CHERRY_WOOD.builtInRegistryHolder()), Either.left(Blocks.CHERRY_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_CHERRY_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.PINK_DAISY.builtInRegistryHolder(), BovinesBlocks.POTTED_PINK_DAISY.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.SNOWDROP, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 12635883))),
                new BlockReference<>(Optional.of(BovinesBlocks.SNOWDROP.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/snowdrop_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/snowdrop_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.DIG_SLOWDOWN, 7200))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.left(Blocks.SNOW_BLOCK.builtInRegistryHolder()), Either.left(Blocks.SNOW.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.FERN.builtInRegistryHolder()), Either.left(Blocks.POTTED_FERN.builtInRegistryHolder()), Either.left(Blocks.PINK_PETALS.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.SPRUCE_LOG.builtInRegistryHolder()), Either.left(Blocks.SPRUCE_WOOD.builtInRegistryHolder()), Either.left(Blocks.SPRUCE_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_SPRUCE_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.SNOWDROP.builtInRegistryHolder(), BovinesBlocks.POTTED_SNOWDROP.builtInRegistryHolder()))),
                        List.of()))));
        context.register(MoobloomKeys.TROPICAL_BLUE, new CowType<>(BovinesCowTypeTypes.MOOBLOOM_TYPE, new MoobloomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.empty(), List.of(), chargelilyWeighted, Optional.of(ColorParticleOption.create(BovinesParticleTypes.BLOOM, 4279795))),
                new BlockReference<>(Optional.of(BovinesBlocks.TROPICAL_BLUE.defaultBlockState()), Optional.empty(), Optional.empty()),
                new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/tropical_blue_bud")), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/tropical_blue_nectar_bowl")),
                new NectarEffects(List.of(new NectarEffects.Entry(MobEffects.FIRE_RESISTANCE, 9600))),
                new OffspringConditionsConfiguration(List.of(createCondition(
                        List.of(
                                List.of(Either.left(Blocks.COCOA.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.BAMBOO.builtInRegistryHolder()), Either.left(Blocks.BAMBOO_SAPLING.builtInRegistryHolder()), Either.left(Blocks.PINK_PETALS.builtInRegistryHolder())),
                                List.of(Either.left(Blocks.JUNGLE_LOG.builtInRegistryHolder()), Either.left(Blocks.JUNGLE_WOOD.builtInRegistryHolder()), Either.left(Blocks.JUNGLE_SAPLING.builtInRegistryHolder()), Either.left(Blocks.POTTED_JUNGLE_SAPLING.builtInRegistryHolder()))
                        ),
                        List.of(BovinesBlocks.TROPICAL_BLUE.builtInRegistryHolder(), BovinesBlocks.POTTED_TROPICAL_BLUE.builtInRegistryHolder()))),
                        List.of()))));

        // Mooshroom Types
        context.register(MooshroomKeys.RED_MUSHROOM, new CowType<>(BovinesCowTypeTypes.MOOSHROOM_TYPE, new MooshroomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.of(new ResourceLocation("cow/brown_mooshroom")), List.of(), List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>((Holder.Reference)context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MooshroomKeys.BROWN_MUSHROOM), 1)), Optional.of(ColorParticleOption.create(BovinesParticleTypes.SHROOM, 11014162))),
                new BlockReference<>(Optional.of(Blocks.RED_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/mooshroom_mycelium"), false)),
                false,
                true
        )));
        context.register(MooshroomKeys.BROWN_MUSHROOM, new CowType<>(BovinesCowTypeTypes.MOOSHROOM_TYPE, new MooshroomConfiguration(
                new CowTypeConfiguration.Settings<>(Optional.of(new ResourceLocation("cow/brown_mooshroom")), List.of(), List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>((Holder.Reference)context.lookup(BovinesRegistryKeys.COW_TYPE).getOrThrow(MooshroomKeys.RED_MUSHROOM), 1)), Optional.of(ColorParticleOption.create(BovinesParticleTypes.SHROOM, 9397834))),
                new BlockReference<>(Optional.of(Blocks.BROWN_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/mooshroom_mycelium"), false)),
                true,
                false
        )));
    }

    private static LootItemCondition createCondition(List<List<Either<Holder<Block>, BlockState>>> blocks, List<Holder<Block>> flowerBlocks) {
        return AnyOfCondition.anyOf(
                AllOfCondition.allOf(blocks.stream().map(eithers -> AnyOfCondition.anyOf(eithers.stream().map(either -> new BlockInRadiusCondition.Builder(either).withRadius(12, 6).withOffset(0, 5, 0)).toArray(LootItemCondition.Builder[]::new))).toArray(LootItemCondition.Builder[]::new)),
                AnyOfCondition.anyOf(flowerBlocks.stream().map(blockHolder -> new BlockInRadiusCondition.Builder(blockHolder).withRadius(12, 6).withOffset(0, 5, 0)).toArray(LootItemCondition.Builder[]::new))
        ).build();
    }

}
