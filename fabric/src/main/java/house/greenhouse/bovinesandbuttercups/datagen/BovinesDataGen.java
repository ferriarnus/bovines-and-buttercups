package house.greenhouse.bovinesandbuttercups.datagen;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.BovinesTags;
import house.greenhouse.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;
import house.greenhouse.bovinesandbuttercups.content.recipe.FlowerCrownRecipe;
import house.greenhouse.bovinesandbuttercups.registry.BovinesBlocks;
import house.greenhouse.bovinesandbuttercups.registry.BovinesCowTypes;
import house.greenhouse.bovinesandbuttercups.registry.BovinesFlowerCrownPetals;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import house.greenhouse.bovinesandbuttercups.registry.BovinesLootTables;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BovinesDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(DynamicRegistryProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ChestLootTableProvider::new);
        pack.addProvider(EntityLootTableProvider::new);
        pack.addProvider(BiomeTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(ConfiguredFeatureTagProvider::new);
        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(FlowerCrownPetalTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(BovinesRegistryKeys.COW_TYPE, BovinesCowTypes::bootstrap);
        registryBuilder.add(BovinesRegistryKeys.FLOWER_CROWN_PETAL, BovinesFlowerCrownPetals::bootstrap);
    }

    private static class DynamicRegistryProvider extends FabricDynamicRegistryProvider {

        public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            BovinesCowTypes.bootstrap(createContext(registries, entries));
            BovinesFlowerCrownPetals.bootstrap(createContext(registries, entries));
        }

        private static <T> BootstrapContext<T> createContext(HolderLookup.Provider registries, Entries entries) {
            return new BootstrapContext<>() {
                @Override
                public Holder.Reference<T> register(ResourceKey<T> resourceKey, T object, Lifecycle lifecycle) {
                    return (Holder.Reference<T>) entries.add(resourceKey, object);
                }

                @Override
                public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> resourceKey) {
                    return registries.lookupOrThrow(resourceKey);
                }
            };
        }

        @Override
        public @NotNull String getName() {
            return "Dynamic Registries";
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void buildRecipes(RecipeOutput output) {
            oneToOneConversionRecipe(output, Items.ORANGE_DYE, BovinesBlocks.BIRD_OF_PARADISE, "orange_dye");
            oneToOneConversionRecipe(output, Items.YELLOW_DYE, BovinesBlocks.BUTTERCUP, "yellow_dye");
            oneToOneConversionRecipe(output, Items.LIGHT_BLUE_DYE, BovinesBlocks.CHARGELILY, "light_blue_dye");
            oneToOneConversionRecipe(output, Items.RED_DYE, BovinesBlocks.FREESIA, "red_dye");
            oneToOneConversionRecipe(output, Items.PURPLE_DYE, BovinesBlocks.HYACINTH, "purple_dye");
            oneToOneConversionRecipe(output, Items.LIME_DYE, BovinesBlocks.LIMELIGHT, "lime_dye");
            oneToOneConversionRecipe(output, Items.CYAN_DYE, BovinesBlocks.LINGHOLM, "cyan_dye");
            oneToOneConversionRecipe(output, Items.PINK_DYE, BovinesBlocks.PINK_DAISY, "pink_dye");
            oneToOneConversionRecipe(output, Items.WHITE_DYE, BovinesBlocks.SNOWDROP, "white_dye");
            oneToOneConversionRecipe(output, Items.BLUE_DYE, BovinesBlocks.TROPICAL_BLUE, "blue_dye");

            SpecialRecipeBuilder.special(FlowerCrownRecipe::new).save(output, BovinesAndButtercups.asResource("flower_crown"));
        }
    }

    private static class BlockLootTableProvider extends FabricBlockLootTableProvider {

        protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> lookup) {
            super(dataOutput, lookup);
        }

        @Override
        public void generate() {
            this.dropSelf(BovinesBlocks.BIRD_OF_PARADISE);
            this.dropSelf(BovinesBlocks.BUTTERCUP);
            this.dropSelf(BovinesBlocks.CHARGELILY);
            this.dropSelf(BovinesBlocks.FREESIA);
            this.dropSelf(BovinesBlocks.HYACINTH);
            this.dropSelf(BovinesBlocks.LIMELIGHT);
            this.dropSelf(BovinesBlocks.LINGHOLM);
            this.dropSelf(BovinesBlocks.PINK_DAISY);
            this.dropSelf(BovinesBlocks.SNOWDROP);
            this.dropSelf(BovinesBlocks.TROPICAL_BLUE);

            this.dropOther(BovinesBlocks.POTTED_BIRD_OF_PARADISE, BovinesBlocks.BIRD_OF_PARADISE);
            this.dropOther(BovinesBlocks.POTTED_BUTTERCUP, BovinesBlocks.BUTTERCUP);
            this.dropOther(BovinesBlocks.POTTED_CHARGELILY, BovinesBlocks.CHARGELILY);
            this.dropOther(BovinesBlocks.POTTED_FREESIA, BovinesBlocks.FREESIA);
            this.dropOther(BovinesBlocks.POTTED_HYACINTH, BovinesBlocks.HYACINTH);
            this.dropOther(BovinesBlocks.POTTED_LIMELIGHT, BovinesBlocks.LIMELIGHT);
            this.dropOther(BovinesBlocks.POTTED_LINGHOLM, BovinesBlocks.LINGHOLM);
            this.dropOther(BovinesBlocks.POTTED_PINK_DAISY, BovinesBlocks.PINK_DAISY);
            this.dropOther(BovinesBlocks.POTTED_SNOWDROP, BovinesBlocks.SNOWDROP);
            this.dropOther(BovinesBlocks.POTTED_TROPICAL_BLUE, BovinesBlocks.TROPICAL_BLUE);
        }

    }

    private static class ChestLootTableProvider extends SimpleFabricLootTableProvider {

        public ChestLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup, LootContextParamSets.CHEST);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
            biConsumer.accept(BovinesLootTables.RANCH, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(2.0F))
                            .add(LootItem.lootTableItem(Blocks.SHORT_GRASS)
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
                                    .setWeight(1)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.SADDLE)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BOWL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
        }
    }

    private static class EntityLootTableProvider extends SimpleFabricLootTableProvider {
        private final CompletableFuture<HolderLookup.Provider> registries;

        public EntityLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup, LootContextParamSets.ENTITY);
            registries = lookup;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
            HolderLookup.Provider lookup = registries.join();

            biConsumer.accept(BovinesLootTables.MOOBLOOM, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.LEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(lookup, UniformGenerator.between(0.0F, 1.0F)))
                            )
                    ).withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BEEF)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(lookup, UniformGenerator.between(0.0F, 1.0F))))
                    )
            );

        }
    }

    private static class BiomeTagProvider extends FabricTagProvider<Biome> {
        public BiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.BIOME, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_BIRD_OF_PARADISE))
                    .forceAddTag(ConventionalBiomeTags.IS_SAVANNA);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_BUTTERCUP))
                    .forceAddTag(ConventionalBiomeTags.IS_FLOWER_FOREST);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_CHARGELILY))
                    .add(Biomes.STONY_PEAKS);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_FREESIA))
                    .add(Biomes.MANGROVE_SWAMP);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_HYACINTH))
                    .add(Biomes.DARK_FOREST);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_LIMELIGHT))
                    .add(Biomes.LUSH_CAVES);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_LINGHOLM))
                    .add(Biomes.TAIGA);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_PINK_DAISY))
                    .forceAddTag(ConventionalBiomeTags.IS_FLOWER_FOREST);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_SNOWDROP))
                    .add(Biomes.SNOWY_TAIGA);
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_RANCH_STRUCTURE_TROPICAL_BLUE))
                    .forceAddTag(ConventionalBiomeTags.IS_JUNGLE);

            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST))
                    .forceAddTag(ConventionalBiomeTags.IS_FLOWER_FOREST);
            tag(BovinesTags.BiomeTags.PREVENT_COW_SPAWNS)
                    .addTag(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST);
        }
    }

    private static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            ((FabricTagBuilder)tag(BlockTags.SMALL_FLOWERS))
                    .forceAddTag(BovinesTags.BlockTags.MOOBLOOM_FLOWERS);
            tag(BovinesTags.BlockTags.MOOBLOOM_FLOWERS)
                    .add(
                            reverseLookup(BovinesBlocks.BIRD_OF_PARADISE),
                            reverseLookup(BovinesBlocks.BUTTERCUP),
                            reverseLookup(BovinesBlocks.CHARGELILY),
                            reverseLookup(BovinesBlocks.CUSTOM_FLOWER),
                            reverseLookup(BovinesBlocks.FREESIA),
                            reverseLookup(BovinesBlocks.HYACINTH),
                            reverseLookup(BovinesBlocks.LIMELIGHT),
                            reverseLookup(BovinesBlocks.LINGHOLM),
                            reverseLookup(BovinesBlocks.PINK_DAISY),
                            reverseLookup(BovinesBlocks.SNOWDROP),
                            reverseLookup(BovinesBlocks.TROPICAL_BLUE)
                    );
            tag(BovinesTags.BlockTags.SNOWDROP_PLACEABLE)
                    .add(
                            reverseLookup(Blocks.SNOW_BLOCK)
                    );
        }
    }

    private static class ConfiguredFeatureTagProvider extends FabricTagProvider<ConfiguredFeature<?, ?>> {
        public ConfiguredFeatureTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.CONFIGURED_FEATURE, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            tag(BovinesTags.ConfiguredFeatureTags.RANCH_ALLOWED)
                    .add(CaveFeatures.GLOW_LICHEN)
                    .add(VegetationFeatures.SINGLE_PIECE_OF_GRASS)
                    .add(VegetationFeatures.PATCH_GRASS)
                    .add(VegetationFeatures.PATCH_GRASS_JUNGLE)
                    .add(VegetationFeatures.PATCH_TAIGA_GRASS)
                    .add(VegetationFeatures.PATCH_TALL_GRASS);
        }
    }

    private static class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            tag(BovinesTags.EntityTypeTags.WILL_EQUIP_FLOWER_CROWN)
                    .add(reverseLookup(EntityType.PIGLIN))
                    .add(reverseLookup(EntityType.VILLAGER));
        }
    }

    private static class FlowerCrownPetalTagProvider extends FabricTagProvider<FlowerCrownPetal> {
        public FlowerCrownPetalTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, BovinesRegistryKeys.FLOWER_CROWN_PETAL, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            tag(BovinesTags.FlowerCrownPetalTags.CREATIVE_MENU_ORDER)
                    .add(BovinesFlowerCrownPetals.FREESIA)
                    .add(BovinesFlowerCrownPetals.BIRD_OF_PARADISE)
                    .add(BovinesFlowerCrownPetals.BUTTERCUP)
                    .add(BovinesFlowerCrownPetals.LIMELIGHT)
                    .add(BovinesFlowerCrownPetals.LINGHOLM)
                    .add(BovinesFlowerCrownPetals.CHARGELILY)
                    .add(BovinesFlowerCrownPetals.TROPICAL_BLUE)
                    .add(BovinesFlowerCrownPetals.HYACINTH)
                    .add(BovinesFlowerCrownPetals.PINK_DAISY)
                    .add(BovinesFlowerCrownPetals.SNOWDROP);
        }
    }

    private static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            ((FabricTagBuilder)tag(ItemTags.SMALL_FLOWERS))
                    .forceAddTag(BovinesTags.ItemTags.MOOBLOOM_FLOWERS);
            tag(BovinesTags.ItemTags.MOOBLOOM_FLOWERS)
                    .add(
                            reverseLookup(BovinesItems.BIRD_OF_PARADISE),
                            reverseLookup(BovinesItems.BUTTERCUP),
                            reverseLookup(BovinesItems.CHARGELILY),
                            reverseLookup(BovinesItems.CUSTOM_FLOWER),
                            reverseLookup(BovinesItems.FREESIA),
                            reverseLookup(BovinesItems.HYACINTH),
                            reverseLookup(BovinesItems.LIMELIGHT),
                            reverseLookup(BovinesItems.LINGHOLM),
                            reverseLookup(BovinesItems.PINK_DAISY),
                            reverseLookup(BovinesItems.SNOWDROP),
                            reverseLookup(BovinesItems.TROPICAL_BLUE)
                    );
        }
    }
}
