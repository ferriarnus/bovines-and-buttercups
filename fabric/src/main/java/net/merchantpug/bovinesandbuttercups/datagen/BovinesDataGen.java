package net.merchantpug.bovinesandbuttercups.datagen;

import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.api.functions.BlockInWorldDFunctions;
import com.williambl.vampilang.lang.VExpression;
import com.williambl.vampilang.stdlib.LogicVFunctions;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.BackGrassConfiguration;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.OffspringConditionsConfiguration;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.configuration.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesVFunctions;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BovinesDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(DynamicRegistryGenerator::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ChestLootTableProvider::new);
        pack.addProvider(EntityLootTableProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(BovinesResourceKeys.CONFIGURED_COW_TYPE, v -> {});
    }

    private static class DynamicRegistryGenerator extends FabricDynamicRegistryProvider {

        public DynamicRegistryGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            // Moobloom Types
            Holder<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> chargelilyMoobloom = (Holder<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>>)(Object)
                    entries.add(BovinesResourceKeys.MoobloomKeys.CHARGELILY, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                            new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, Optional.empty()),
                            new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                            new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                            Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true)),
                            Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                            Optional.of(new MobEffectInstance(MobEffects.DIG_SPEED, 1200)),
                            Optional.empty())));

            Optional<HolderSet<Biome>> flowerForestHolderSet = Optional.of(HolderSet.direct(registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.FLOWER_FOREST)));
            Optional<List<CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>>> chargelilyWeighted = Optional.of(List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>(chargelilyMoobloom, 1)));

            BiFunction<List<Map<Block, List<BlockStateProperty<?>>>>, List<Block>, OffspringConditionsConfiguration> expressionCreator = (list, flowerBlocks) -> new OffspringConditionsConfiguration(Optional.of(VExpression.functionApplication(LogicVFunctions.OR, Map.of("operands", VExpression.list(List.of(VExpression.functionApplication(StandardVFunctions.GREATER_THAN_OR_EQUAL, Map.of("a", VExpression.functionApplication(StandardVFunctions.GREATER_THAN_OR_EQUAL, Map.of("a", VExpression.functionApplication(BovinesVFunctions.EntityVFunctions.BLOCK_IN_RADIUS, Map.of("entity", VExpression.variable("entity"), "block_predicate", VExpression.functionApplication(BlockInWorldDFunctions.ADVANCEMENT_PREDICATE, Map.of("predicate", VExpression.value(DTypes.BLOCK_ADVANCEMENT_PREDICATE, BlockPredicate.Builder.block().of(flowerBlocks)), "block", VExpression.variable("block"))))))), "b", VExpression.value(StandardVTypes.NUMBER, 1))),
                    VExpression.functionApplication(StandardVFunctions.GREATER_THAN_OR_EQUAL, Map.of("a", VExpression.functionApplication(BovinesVFunctions.EntityVFunctions.BLOCK_IN_RADIUS, Map.of("entity", VExpression.variable("entity"), "block_predicate", VExpression.functionApplication(LogicVFunctions.OR, Map.of("operands", VExpression.list(list.stream().map(map -> VExpression.functionApplication(LogicVFunctions.OR, Map.of("operands", VExpression.list(list.stream().map(value -> VExpression.functionApplication(LogicVFunctions.OR, Map.of("operands", VExpression.list(value.entrySet().stream().map(entry -> {
                        BlockPredicate.Builder predicateBuilder = BlockPredicate.Builder.block().of(entry.getKey());
                        if (!entry.getValue().isEmpty()) {
                            StatePropertiesPredicate.Builder statePropertiesBuilder = StatePropertiesPredicate.Builder.properties();
                            entry.getValue().forEach(blockStateProperty -> blockStateProperty.buildWithThis(statePropertiesBuilder));
                            predicateBuilder.setProperties(statePropertiesBuilder);
                        }
                        return VExpression.functionApplication(StandardVFunctions.GREATER_THAN_OR_EQUAL, Map.of("a", VExpression.functionApplication(BlockInWorldDFunctions.ADVANCEMENT_PREDICATE, Map.of("predicate", VExpression.value(DTypes.BLOCK_ADVANCEMENT_PREDICATE, predicateBuilder.build()), "block", VExpression.variable("block"), "radius", VExpression.value(DTypes.VEC3, new Vec3(6, 6, 6)), "offset", VExpression.value(DTypes.VEC3, new Vec3(0, 4, 0)))), "b", VExpression.value(StandardVTypes.NUMBER, 1)));
                    }).toList())))).toList())))).toList())))))))))))), Optional.empty());

            entries.add(BovinesResourceKeys.MoobloomKeys.BIRD_OF_PARADISE, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.SLOW_FALLING, 7200)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.MELON, List.of(), Blocks.MELON_STEM, List.of()),
                                    Map.of(Blocks.POPPY, List.of(), Blocks.POTTED_POPPY, List.of()),
                                    Map.of(Blocks.ACACIA_LOG, List.of(), Blocks.ACACIA_WOOD, List.of(), Blocks.ACACIA_SAPLING, List.of(), Blocks.POTTED_ACACIA_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.BIRD_OF_PARADISE.value(), BovinesBlocks.POTTED_BIRD_OF_PARADISE.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.BUTTERCUP, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 7, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.BUTTERCUP.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/buttercup_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.POISON, 2400)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.SUNFLOWER, List.of(new BlockStateProperty<>(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                    Map.of(Blocks.DANDELION, List.of(), Blocks.POTTED_DANDELION, List.of()),
                                    Map.of(Blocks.BIRCH_LOG, List.of(), Blocks.BIRCH_WOOD, List.of(), Blocks.BIRCH_SAPLING, List.of(), Blocks.POTTED_BIRCH_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.BUTTERCUP.value(), BovinesBlocks.POTTED_BUTTERCUP.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.FREESIA, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.FREESIA.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/freesia_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/freesia_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.WATER_BREATHING, 9600)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.LILY_PAD, List.of()),
                                    Map.of(Blocks.BLUE_ORCHID, List.of(), Blocks.POTTED_BLUE_ORCHID, List.of()),
                                    Map.of(Blocks.MANGROVE_LOG, List.of(), Blocks.MANGROVE_WOOD, List.of(), Blocks.MANGROVE_PROPAGULE, List.of(), Blocks.POTTED_MANGROVE_PROPAGULE, List.of())
                            ),
                            List.of(BovinesBlocks.FREESIA.value(), BovinesBlocks.POTTED_FREESIA.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.HYACINTH, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.HYACINTH.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/hyacinth_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/hyacinth_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.WITHER, 2400)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.ROSE_BUSH, List.of(new BlockStateProperty<>(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                    Map.of(Blocks.CORNFLOWER, List.of(), Blocks.POTTED_CORNFLOWER, List.of()),
                                    Map.of(Blocks.DARK_OAK_LOG, List.of(), Blocks.DARK_OAK_WOOD, List.of(), Blocks.DARK_OAK_SAPLING, List.of(), Blocks.POTTED_DARK_OAK_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.HYACINTH.value(), BovinesBlocks.POTTED_HYACINTH.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.LIMELIGHT, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.LIMELIGHT.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/limelight_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/limelight_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.REGENERATION, 2400)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.MOSS_BLOCK, List.of(), Blocks.MOSS_CARPET, List.of()),
                                    Map.of(Blocks.BIG_DRIPLEAF, List.of(), Blocks.SMALL_DRIPLEAF, List.of(new BlockStateProperty<>(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                    Map.of(Blocks.FLOWERING_AZALEA_LEAVES, List.of(), Blocks.FLOWERING_AZALEA, List.of(), Blocks.POTTED_FLOWERING_AZALEA, List.of())
                            ),
                            List.of(BovinesBlocks.LIMELIGHT.value(), BovinesBlocks.POTTED_LIMELIGHT.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.PINK_DAISY, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 1, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.PINK_DAISY.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/pink_daisy_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/pink_daisy_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.LILAC, List.of(new BlockStateProperty<>(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))),
                                    Map.of(Blocks.ALLIUM, List.of(), Blocks.POTTED_ALLIUM, List.of(), Blocks.PINK_TULIP, List.of(), Blocks.POTTED_PINK_TULIP, List.of()),
                                    Map.of(Blocks.OAK_LOG, List.of(), Blocks.OAK_WOOD, List.of(), Blocks.OAK_SAPLING, List.of(), Blocks.POTTED_OAK_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.PINK_DAISY.value(), BovinesBlocks.POTTED_PINK_DAISY.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.SNOWDROP, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 1, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.SNOWDROP.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/snowdrop_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/snowdrop_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 7200)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.SNOW_BLOCK, List.of(), Blocks.SNOW, List.of()),
                                    Map.of(Blocks.FERN, List.of(), Blocks.POTTED_FERN, List.of()),
                                    Map.of(Blocks.SPRUCE_LOG, List.of(), Blocks.SPRUCE_WOOD, List.of(), Blocks.SPRUCE_SAPLING, List.of(), Blocks.POTTED_SPRUCE_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.SNOWDROP.value(), BovinesBlocks.POTTED_SNOWDROP.value()))))));
            entries.add(BovinesResourceKeys.MoobloomKeys.TROPICAL_BLUE, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.TROPICAL_BLUE.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/tropical_blue_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/tropical_blue_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9600)),
                    Optional.of(expressionCreator.apply(
                            List.of(
                                    Map.of(Blocks.COCOA, List.of()),
                                    Map.of(Blocks.BAMBOO, List.of(), Blocks.BAMBOO_SAPLING, List.of(), Blocks.POTTED_BAMBOO, List.of()),
                                    Map.of(Blocks.JUNGLE_LOG, List.of(), Blocks.JUNGLE_WOOD, List.of(), Blocks.JUNGLE_SAPLING, List.of(), Blocks.POTTED_JUNGLE_SAPLING, List.of())
                            ),
                            List.of(BovinesBlocks.TROPICAL_BLUE.value(), BovinesBlocks.POTTED_TROPICAL_BLUE.value()))))));


            // Mooshroom Types
            entries.add(BovinesResourceKeys.MooshroomKeys.RED_MUSHROOM, new ConfiguredCowType<>(BovinesCowTypes.MOOSHROOM_TYPE.value(), new MooshroomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.of(new ResourceLocation("cow/brown_mooshroom")), Optional.empty(), 0, Optional.of(List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>((Holder<ConfiguredCowType<MooshroomConfiguration, CowType<MooshroomConfiguration>>>)(Object)entries.ref(BovinesResourceKeys.MooshroomKeys.BROWN_MUSHROOM), 1)))),
                    new BlockReference<>(Optional.of(Blocks.RED_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/mooshroom_mycelium"), false)),
                    false,
                    true
            )));
            entries.add(BovinesResourceKeys.MooshroomKeys.BROWN_MUSHROOM, new ConfiguredCowType<>(BovinesCowTypes.MOOSHROOM_TYPE.value(), new MooshroomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.of(new ResourceLocation("cow/brown_mooshroom")), Optional.empty(), 0, Optional.of(List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>((Holder<ConfiguredCowType<MooshroomConfiguration, CowType<MooshroomConfiguration>>>)(Object)entries.ref(BovinesResourceKeys.MooshroomKeys.RED_MUSHROOM), 1)))),
                    new BlockReference<>(Optional.of(Blocks.BROWN_MUSHROOM.defaultBlockState()), Optional.empty(), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/mooshroom_mycelium"), false)),
                    true,
                    false
            )));
        }

        @Override
        public @NotNull String getName() {
            return "Dynamic Registry Objects";
        }

        private record BlockStateProperty<T extends Comparable<T>>(Property<T> property, T value) {
            @SuppressWarnings("unchecked")
            private StatePropertiesPredicate.Builder buildWithThis(StatePropertiesPredicate.Builder builder) {
                if (this.value() instanceof String) {
                    builder.hasProperty(property, (String) value);
                } else if (this.value() instanceof Integer) {
                    builder.hasProperty((Property<Integer>)property, (int) value);
                } else if (this.value() instanceof Boolean) {
                    builder.hasProperty((Property<Boolean>)property, (boolean) value);
                } else if (value instanceof StringRepresentable stringRepresentable) {
                    builder.hasProperty(property, stringRepresentable.getSerializedName());
                }
                return builder;
            }

            private static <T extends StringRepresentable & Comparable<T>> void buildStringRepresentable(StatePropertiesPredicate.Builder builder, Property<T> property, T value) {
                builder.hasProperty(property, value);
            }
        }
    }

    private static class BlockLootTableProvider extends FabricBlockLootTableProvider {

        protected BlockLootTableProvider(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            this.dropSelf(BovinesBlocks.BIRD_OF_PARADISE.value());
            this.dropSelf(BovinesBlocks.BUTTERCUP.value());
            this.dropSelf(BovinesBlocks.CHARGELILY.value());
            this.dropSelf(BovinesBlocks.FREESIA.value());
            this.dropSelf(BovinesBlocks.HYACINTH.value());
            this.dropSelf(BovinesBlocks.LIMELIGHT.value());
            this.dropSelf(BovinesBlocks.PINK_DAISY.value());
            this.dropSelf(BovinesBlocks.SNOWDROP.value());
            this.dropSelf(BovinesBlocks.TROPICAL_BLUE.value());

            this.dropOther(BovinesBlocks.POTTED_BIRD_OF_PARADISE.value(), BovinesBlocks.BIRD_OF_PARADISE.value());
            this.dropOther(BovinesBlocks.POTTED_BUTTERCUP.value(), BovinesBlocks.BUTTERCUP.value());
            this.dropOther(BovinesBlocks.POTTED_CHARGELILY.value(), BovinesBlocks.CHARGELILY.value());
            this.dropOther(BovinesBlocks.POTTED_FREESIA.value(), BovinesBlocks.FREESIA.value());
            this.dropOther(BovinesBlocks.POTTED_HYACINTH.value(), BovinesBlocks.HYACINTH.value());
            this.dropOther(BovinesBlocks.POTTED_LIMELIGHT.value(), BovinesBlocks.LIMELIGHT.value());
            this.dropOther(BovinesBlocks.POTTED_PINK_DAISY.value(), BovinesBlocks.PINK_DAISY.value());
            this.dropOther(BovinesBlocks.POTTED_SNOWDROP.value(), BovinesBlocks.SNOWDROP.value());
            this.dropOther(BovinesBlocks.POTTED_TROPICAL_BLUE.value(), BovinesBlocks.TROPICAL_BLUE.value());
        }

    }


    private static class ChestLootTableProvider extends SimpleFabricLootTableProvider {

        public ChestLootTableProvider(FabricDataOutput output) {
            super(output, LootContextParamSets.CHEST);
        }

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            biConsumer.accept(BovinesAndButtercups.asResource("moobloom"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Blocks.GRASS)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                                    .setWeight(2))
                            .add(LootItem.lootTableItem(Items.POTATO)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 12.0F)))
                                    .setWeight(2))
                            .add(LootItem.lootTableItem(Items.CARROT)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F)))
                                    .setWeight(2))
                            .add(LootItem.lootTableItem(Items.HAY_BLOCK)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .setWeight(1))
                            .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .setWeight(1)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(2.0F))
                            .setBonusRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL)
                                    .setWeight(3))
                            .add(LootItem.lootTableItem(Items.WOODEN_HOE)
                                    .setWeight(3))
                            .add(LootItem.lootTableItem(Items.SHEARS)
                                    .setWeight(2))
                            .add(LootItem.lootTableItem(Items.STONE_HOE)
                                    .setWeight(2))
                            .add(LootItem.lootTableItem(Items.COMPOSTER)
                                    .setWeight(1))
                            .add(LootItem.lootTableItem(Items.SMOKER)
                                    .setWeight(1))
                            .add(LootItem.lootTableItem(Items.SADDLE)
                                    .setWeight(1)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BOWL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
        }
    }

    private static class EntityLootTableProvider extends SimpleFabricLootTableProvider {

        public EntityLootTableProvider(FabricDataOutput output) {
            super(output, LootContextParamSets.ENTITY);
        }

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            biConsumer.accept(BovinesAndButtercups.asResource("moobloom"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.LEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    ).withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BEEF)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                    )
            );
        }
    }
}
