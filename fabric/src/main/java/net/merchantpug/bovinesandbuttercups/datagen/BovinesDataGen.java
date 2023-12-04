package net.merchantpug.bovinesandbuttercups.datagen;

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
import net.merchantpug.bovinesandbuttercups.content.cowtypes.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.cowtypes.MooshroomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

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
                            Optional.of(new MobEffectInstance(MobEffects.DIG_SPEED, 1200)))));

            Optional<HolderSet<Biome>> flowerForestHolderSet = Optional.of(HolderSet.direct(registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.FLOWER_FOREST)));
            Optional<List<CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>>> chargelilyWeighted = Optional.of(List.of(new CowTypeConfiguration.WeightedConfiguredCowType<>(chargelilyMoobloom, 1)));

            entries.add(BovinesResourceKeys.MoobloomKeys.BIRD_OF_PARADISE, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.BIRD_OF_PARADISE.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/bird_of_paradise_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/bird_of_paradise_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.SLOW_FALLING, 7200)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.BUTTERCUP, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 7, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.BUTTERCUP.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/buttercup_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.POISON, 2400)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.FREESIA, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.FREESIA.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/freesia_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/freesia_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.WATER_BREATHING, 9600)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.HYACINTH, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.HYACINTH.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/hyacinth_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/hyacinth_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.WITHER, 2400)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.LIMELIGHT, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.LIMELIGHT.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/limelight_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/limelight_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.REGENERATION, 2400)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.PINK_DAISY, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 1, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.PINK_DAISY.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/pink_daisy_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/pink_daisy_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.SNOWDROP, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), flowerForestHolderSet, 1, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.SNOWDROP.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/snowdrop_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/snowdrop_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 7200)))));
            entries.add(BovinesResourceKeys.MoobloomKeys.TROPICAL_BLUE, new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(
                    new CowTypeConfiguration.Settings<>(Optional.empty(), Optional.empty(), 0, chargelilyWeighted),
                    new BlockReference<>(Optional.of(BovinesBlocks.TROPICAL_BLUE.value().defaultBlockState()), Optional.empty(), Optional.empty()),
                    new BlockReference<>(Optional.empty(), Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/tropical_blue_bud")), Optional.empty()),
                    Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass"), true)),
                    Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/item/tropical_blue_nectar_bowl")),
                    Optional.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9600)))));


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
