package house.greenhouse.bovinesandbuttercups.datagen;

import com.mojang.serialization.Lifecycle;
import house.greenhouse.bovinesandbuttercups.api.BovinesConventionalTags;
import house.greenhouse.bovinesandbuttercups.api.CowType;
import house.greenhouse.bovinesandbuttercups.api.CowTypeType;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.BreedCowWithTypeTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.block.CandleCupcakeBlock;
import house.greenhouse.bovinesandbuttercups.content.block.CupcakeBlock;
import house.greenhouse.bovinesandbuttercups.content.component.ItemNectar;
import house.greenhouse.bovinesandbuttercups.content.data.nectar.Nectar;
import house.greenhouse.bovinesandbuttercups.content.item.FlowerCrownItem;
import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import house.greenhouse.bovinesandbuttercups.registry.BovinesNectars;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.BovinesTags;
import house.greenhouse.bovinesandbuttercups.content.data.flowercrown.FlowerCrownMaterial;
import house.greenhouse.bovinesandbuttercups.content.recipe.FlowerCrownRecipe;
import house.greenhouse.bovinesandbuttercups.registry.BovinesBlocks;
import house.greenhouse.bovinesandbuttercups.api.BovinesCowTypes;
import house.greenhouse.bovinesandbuttercups.registry.BovinesFlowerCrownMaterials;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import house.greenhouse.bovinesandbuttercups.registry.BovinesLootTables;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BovinesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(DynamicRegistryProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ChestLootTableProvider::new);
        pack.addProvider(EntityLootTableProvider::new);
        pack.addProvider(BiomeTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(ConfiguredFeatureTagProvider::new);
        pack.addProvider(EntityTypeTagProvider::new);
        pack.addProvider(FlowerCrownMaterialTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(NectarTagProvider::new);
        pack.addProvider(ModelProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(BovinesRegistryKeys.NECTAR, BovinesNectars::bootstrap);
        registryBuilder.add(BovinesRegistryKeys.COW_TYPE, BovinesCowTypes::bootstrap);
        registryBuilder.add(BovinesRegistryKeys.FLOWER_CROWN_MATERIAL, BovinesFlowerCrownMaterials::bootstrap);
    }

    private static class ModelProvider extends FabricModelProvider {

        public ModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generators) {
            createCupcake(BovinesBlocks.BUTTERCUP_CUPCAKE, generators);
            createCupcake(BovinesBlocks.PINK_DAISY_CUPCAKE, generators);
            createCupcake(BovinesBlocks.LIMELIGHT_CUPCAKE, generators);
            createCupcake(BovinesBlocks.BIRD_OF_PARADISE_CUPCAKE, generators);
            createCupcake(BovinesBlocks.CHARGELILY_CUPCAKE, generators);
            createCupcake(BovinesBlocks.HYACINTH_CUPCAKE, generators);
            createCupcake(BovinesBlocks.SNOWDROP_CUPCAKE, generators);
            createCupcake(BovinesBlocks.TROPICAL_BLUE_CUPCAKE, generators);
            createCupcake(BovinesBlocks.FREESIA_CUPCAKE, generators);
            createCupcake(BovinesBlocks.LINGHOLM_CUPCAKE, generators);

            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.BUTTERCUP_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.PINK_DAISY_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.LIMELIGHT_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.BIRD_OF_PARADISE_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.CHARGELILY_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.HYACINTH_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.SNOWDROP_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.TROPICAL_BLUE_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.FREESIA_CUPCAKE, block -> createCandleCupcake(block, generators));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.LINGHOLM_CUPCAKE, block -> createCandleCupcake(block, generators));
        }

        private void createCupcake(CupcakeBlock block, BlockModelGenerators generators) {
            generators.skipAutoItemBlock(block);
            var mapping = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block));

            var oneTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_cupcake")), Optional.empty(), TextureSlot.ALL);
            var one = oneTemplate.create(block, mapping, generators.modelOutput);

            var plural = BuiltInRegistries.BLOCK.getKey(block).getPath() + "s";

            var twoTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_two_cupcakes")), Optional.empty(), TextureSlot.ALL);
            var two = twoTemplate.create(BovinesAndButtercups.asResource("block/two_" + plural), mapping, generators.modelOutput);

            var threeTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_three_cupcakes")), Optional.empty(), TextureSlot.ALL);
            var three = threeTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural), mapping, generators.modelOutput);

            var fourTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_four_cupcakes")), Optional.empty(), TextureSlot.ALL);
            var four = fourTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural), mapping, generators.modelOutput);

            var generator = MultiVariantGenerator.multiVariant(block)
                    .with(PropertyDispatch.property(CupcakeBlock.COUNT)
                            .select(1, Variant.variant().with(VariantProperties.MODEL, one))
                            .select(2, Variant.variant().with(VariantProperties.MODEL, two))
                            .select(3, Variant.variant().with(VariantProperties.MODEL, three))
                            .select(4, Variant.variant().with(VariantProperties.MODEL, four)));
            generators.blockStateOutput.accept(generator);
        }

        private void createCandleCupcake(CandleCupcakeBlock block, BlockModelGenerators generators) {
            generators.skipAutoItemBlock(block);
            var unlitMapping = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block.base())).put(TextureSlot.CANDLE, TextureMapping.getBlockTexture(block.candle()));
            var litMapping = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block.base())).put(TextureSlot.CANDLE, TextureMapping.getBlockTexture(block.candle(), "_lit"));

            var path = BuiltInRegistries.BLOCK.getKey(block.base()).getPath();
            var candleKey = BuiltInRegistries.BLOCK.getKey(block.candle());
            var candleLocation = candleKey.getNamespace().equals("minecraft") ? candleKey.getPath() : candleKey.getNamespace() + "_" + candleKey.getPath();

            var oneTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_candle_cupcake")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var oneUnlit = oneTemplate.create(BovinesAndButtercups.asResource("block/" + candleLocation + "_" + path), unlitMapping, generators.modelOutput);
            var oneLit = oneTemplate.create(BovinesAndButtercups.asResource("block/" + candleLocation + "_" + path + "_lit"), litMapping, generators.modelOutput);

            var plural = path + "s";
            var candlePlural = candleLocation + "s";

            var twoOneCandleTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_two_cupcakes_one_candle")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var twoOneCandleUnlit = twoOneCandleTemplate.create(BovinesAndButtercups.asResource("block/two_" + plural + "_one_" + candleLocation), unlitMapping, generators.modelOutput);
            var twoOneCandleLit = twoOneCandleTemplate.create(BovinesAndButtercups.asResource("block/two_" + plural + "_one_" + candleLocation + "_lit"), litMapping, generators.modelOutput);

            var twoTwoCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_two_cupcakes_two_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var twoTwoCandlesUnlit = twoTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/two_" + plural + "_two_" + candlePlural), unlitMapping, generators.modelOutput);
            var twoTwoCandlesLit = twoTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/two_" + plural + "_two_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var threeOneCandleTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_three_cupcakes_one_candle")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var threeOneCandleUnlit = threeOneCandleTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_one_" + candleLocation), unlitMapping, generators.modelOutput);
            var threeOneCandleLit = threeOneCandleTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_one_" + candleLocation +"_lit"), litMapping, generators.modelOutput);

            var threeTwoCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_three_cupcakes_two_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var threeTwoCandlesUnlit = threeTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_two_" + candlePlural), unlitMapping, generators.modelOutput);
            var threeTwoCandlesLit = threeTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_two_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var threeThreeCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_three_cupcakes_three_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var threeThreeCandlesUnlit = threeThreeCandlesTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_three_" + candlePlural), unlitMapping, generators.modelOutput);
            var threeThreeCandlesLit = threeThreeCandlesTemplate.create(BovinesAndButtercups.asResource("block/three_" + plural + "_three_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var fourOneCandleTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_four_cupcakes_one_candle")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var fourOneCandleUnlit = fourOneCandleTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_one_" + candleLocation), unlitMapping, generators.modelOutput);
            var fourOneCandleLit = fourOneCandleTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_one_" + candleLocation + "_lit"), litMapping, generators.modelOutput);

            var fourTwoCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_four_cupcakes_two_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var fourTwoCandlesUnlit = fourTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_two_" + candlePlural), unlitMapping, generators.modelOutput);
            var fourTwoCandlesLit = fourTwoCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_two_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var fourThreeCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_four_cupcakes_three_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var fourThreeCandlesUnlit = fourThreeCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_three_" + candlePlural), unlitMapping, generators.modelOutput);
            var fourThreeCandlesLit = fourThreeCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_three_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var fourFourCandlesTemplate = new ModelTemplate(Optional.of(BovinesAndButtercups.asResource("block/template_four_cupcakes_four_candles")), Optional.empty(), TextureSlot.ALL, TextureSlot.CANDLE);
            var fourFourCandlesUnlit = fourFourCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_four_" + candlePlural), unlitMapping, generators.modelOutput);
            var fourFourCandlesLit = fourFourCandlesTemplate.create(BovinesAndButtercups.asResource("block/four_" + plural + "_four_" + candlePlural + "_lit"), litMapping, generators.modelOutput);

            var generator = MultiVariantGenerator.multiVariant(block)
                    .with(PropertyDispatch.properties(CandleCupcakeBlock.COUNT, CandleCupcakeBlock.CANDLES, CandleCupcakeBlock.LIT)
                            .select(1, 1, false, Variant.variant().with(VariantProperties.MODEL, oneUnlit))
                            .select(1, 1, true, Variant.variant().with(VariantProperties.MODEL, oneLit))
                            .select(1, 2, false, Variant.variant().with(VariantProperties.MODEL, oneUnlit))
                            .select(1, 2, true, Variant.variant().with(VariantProperties.MODEL, oneLit))
                            .select(1, 3, false, Variant.variant().with(VariantProperties.MODEL, oneUnlit))
                            .select(1, 3, true, Variant.variant().with(VariantProperties.MODEL, oneLit))
                            .select(1, 4, false, Variant.variant().with(VariantProperties.MODEL, oneUnlit))
                            .select(1, 4, true, Variant.variant().with(VariantProperties.MODEL, oneLit))
                            .select(2, 1, false, Variant.variant().with(VariantProperties.MODEL, twoOneCandleUnlit))
                            .select(2, 1, true, Variant.variant().with(VariantProperties.MODEL, twoOneCandleLit))
                            .select(2, 2, false, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesUnlit))
                            .select(2, 2, true, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesLit))
                            .select(2, 3, false, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesUnlit))
                            .select(2, 3, true, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesLit))
                            .select(2, 4, false, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesUnlit))
                            .select(2, 4, true, Variant.variant().with(VariantProperties.MODEL, twoTwoCandlesLit))
                            .select(3, 1, false, Variant.variant().with(VariantProperties.MODEL, threeOneCandleUnlit))
                            .select(3, 1, true, Variant.variant().with(VariantProperties.MODEL, threeOneCandleLit))
                            .select(3, 2, false, Variant.variant().with(VariantProperties.MODEL, threeTwoCandlesUnlit))
                            .select(3, 2, true, Variant.variant().with(VariantProperties.MODEL, threeTwoCandlesLit))
                            .select(3, 3, false, Variant.variant().with(VariantProperties.MODEL, threeThreeCandlesUnlit))
                            .select(3, 3, true, Variant.variant().with(VariantProperties.MODEL, threeThreeCandlesLit))
                            .select(3, 4, false, Variant.variant().with(VariantProperties.MODEL, threeThreeCandlesUnlit))
                            .select(3, 4, true, Variant.variant().with(VariantProperties.MODEL, threeThreeCandlesLit))
                            .select(4, 1, false, Variant.variant().with(VariantProperties.MODEL, fourOneCandleUnlit))
                            .select(4, 1, true, Variant.variant().with(VariantProperties.MODEL, fourOneCandleLit))
                            .select(4, 2, false, Variant.variant().with(VariantProperties.MODEL, fourTwoCandlesUnlit))
                            .select(4, 2, true, Variant.variant().with(VariantProperties.MODEL, fourTwoCandlesLit))
                            .select(4, 3, false, Variant.variant().with(VariantProperties.MODEL, fourThreeCandlesUnlit))
                            .select(4, 3, true, Variant.variant().with(VariantProperties.MODEL, fourThreeCandlesLit))
                            .select(4, 4, false, Variant.variant().with(VariantProperties.MODEL, fourFourCandlesUnlit))
                            .select(4, 4, true, Variant.variant().with(VariantProperties.MODEL, fourFourCandlesLit)));
            generators.blockStateOutput.accept(generator);
        }

        @Override
        public void generateItemModels(ItemModelGenerators generators) {

        }
    }

    private static class DynamicRegistryProvider extends FabricDynamicRegistryProvider {

        public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            BovinesNectars.bootstrap(createContext(registries, entries));
            BovinesCowTypes.bootstrap(createContext(registries, entries));
            BovinesFlowerCrownMaterials.bootstrap(createContext(registries, entries));
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

    private static class AdvancementProvider extends FabricAdvancementProvider {

        protected AdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        @SuppressWarnings("removal")
        public void generateAdvancement(HolderLookup.Provider lookup, Consumer<AdvancementHolder> consumer) {
            ItemStack lockEffectStack = new ItemStack(BovinesItems.NECTAR_BOWL);
            lockEffectStack.set(BovinesDataComponents.NECTAR, new ItemNectar(lookup.asGetterLookup().lookupOrThrow(BovinesRegistryKeys.NECTAR).getOrThrow(BovinesNectars.BUTTERCUP)));
            ItemStack preventEffectStack = new ItemStack(BovinesItems.NECTAR_BOWL);
            lockEffectStack.set(BovinesDataComponents.NECTAR, new ItemNectar(lookup.asGetterLookup().lookupOrThrow(BovinesRegistryKeys.NECTAR).getOrThrow(BovinesNectars.FREESIA)));
            consumer.accept(Advancement.Builder.advancement()
                    .display(new DisplayInfo(
                            lockEffectStack,
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.lock_effect.title"),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.lock_effect.description"),
                            Optional.empty(),
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    )
                    .parent(BovinesAndButtercups.asResource("husbandry/prevent_effect"))
                    .requirements(AdvancementRequirements.allOf(List.of("lock_effect")))
                    .addCriterion("lock_effect", LockEffectTrigger.INSTANCE.createCriterion(new LockEffectTrigger.TriggerInstance(Optional.empty(), Optional.empty())))
                    .build(BovinesAndButtercups.asResource("husbandry/lock_effect")));
            consumer.accept(Advancement.Builder.advancement()
                    .display(new DisplayInfo(
                            preventEffectStack,
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.prevent_effect.title"),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.prevent_effect.description"),
                            Optional.empty(),
                            AdvancementType.TASK,
                            true,
                            true,
                            true)
                    )
                    .parent(ResourceLocation.withDefaultNamespace("husbandry/root"))
                    .requirements(AdvancementRequirements.allOf(List.of("prevent_effect")))
                    .addCriterion("prevent_effect", PreventEffectTrigger.INSTANCE.createCriterion(new PreventEffectTrigger.TriggerInstance(Optional.empty(), Optional.empty())))
                    .build(BovinesAndButtercups.asResource("husbandry/prevent_effect")));
            consumer.accept(Advancement.Builder.advancement()
                    .display(new DisplayInfo(
                            new ItemStack(BovinesItems.PINK_DAISY),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.breed_new_moobloom.title"),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.breed_new_moobloom.description"),
                            Optional.empty(),
                            AdvancementType.TASK,
                            true,
                            true,
                            true)
                    )
                    .parent(ResourceLocation.withDefaultNamespace("husbandry/breed_an_animal"))
                    .requirements(AdvancementRequirements.allOf(List.of("breed_new_moobloom")))
                    .addCriterion("breed_new_moobloom", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true))))
                    .build(BovinesAndButtercups.asResource("husbandry/breed_new_moobloom")));

            HolderLookup.RegistryLookup<CowType<?>> cowTypeRegistry = lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE);

            consumer.accept(Advancement.Builder.advancement()
                    .display(new DisplayInfo(
                            new ItemStack(BovinesItems.LIMELIGHT),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.breed_all_mooblooms.title"),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.breed_all_mooblooms.description"),
                            Optional.empty(),
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false)
                    )
                    .parent(BovinesAndButtercups.asResource("husbandry/breed_new_moobloom"))
                    .requirements(AdvancementRequirements.allOf(List.of(
                            "bovinesandbuttercups:bird_of_paradise",
                            "bovinesandbuttercups:buttercup",
                            "bovinesandbuttercups:chargelily",
                            "bovinesandbuttercups:freesia",
                            "bovinesandbuttercups:hyacinth",
                            "bovinesandbuttercups:limelight",
                            "bovinesandbuttercups:lingholm",
                            "bovinesandbuttercups:pink_daisy",
                            "bovinesandbuttercups:snowdrop",
                            "bovinesandbuttercups:tropical_blue"
                    )))
                    .addCriterion("bovinesandbuttercups:bird_of_paradise", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.BIRD_OF_PARADISE)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:buttercup", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.BUTTERCUP)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:chargelily", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.CHARGELILY)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:freesia", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.FREESIA)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:hyacinth", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.HYACINTH)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:limelight", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.LIMELIGHT)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:lingholm", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.LINGHOLM)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:pink_daisy", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.PINK_DAISY)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:snowdrop", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.SNOWDROP)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .addCriterion("bovinesandbuttercups:tropical_blue", BreedCowWithTypeTrigger.INSTANCE.createCriterion(new BreedCowWithTypeTrigger.TriggerInstance(Optional.empty(), (Optional<Holder<CowTypeType<?>>>)(Optional<?>)lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE_TYPE).get(ResourceKey.create(BovinesRegistryKeys.COW_TYPE_TYPE, BovinesAndButtercups.asResource("moobloom"))), HolderSet.direct(cowTypeRegistry.getOrThrow(BovinesCowTypes.MoobloomKeys.TROPICAL_BLUE)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())))
                    .build(BovinesAndButtercups.asResource("husbandry/breed_all_mooblooms")));
            consumer.accept(Advancement.Builder.advancement()
                    .display(new DisplayInfo(
                            FlowerCrownItem.createRainbowCrown(lookup),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.obtain_flower_crown.title"),
                            Component.translatable("advancements.husbandry.bovinesandbuttercups.husbandry.obtain_flower_crown.description"),
                            Optional.empty(),
                            AdvancementType.TASK,
                            true,
                            true,
                            true)
                    )
                    .parent(ResourceLocation.withDefaultNamespace("husbandry/root"))
                    .requirements(AdvancementRequirements.allOf(List.of("get_flower_crown")))
                    .addCriterion("get_flower_crown", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(BovinesItems.FLOWER_CROWN)))
                    .build(BovinesAndButtercups.asResource("husbandry/obtain_flower_crown")));
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
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

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR, 3)
                    .requires(BovinesItems.RICH_HONEY_BOTTLE)
                    .group("sugar")
                    .unlockedBy("has_rich_honey_bottle", has(BovinesItems.RICH_HONEY_BOTTLE))
                    .save(output, getConversionRecipeName(Items.SUGAR, BovinesItems.RICH_HONEY_BOTTLE));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BovinesItems.RICH_HONEY_BOTTLE, 4)
                    .requires(BovinesItems.RICH_HONEY_BLOCK)
                    .requires(Items.GLASS_BOTTLE, 4)
                    .unlockedBy("has_rich_honey_block", has(BovinesBlocks.RICH_HONEY_BLOCK))
                    .save(output);
            twoByTwoPacker(output, RecipeCategory.REDSTONE, BovinesBlocks.RICH_HONEY_BLOCK, BovinesItems.RICH_HONEY_BOTTLE);

            SpecialRecipeBuilder.special(FlowerCrownRecipe::new).save(output, BovinesAndButtercups.asResource("flower_crown"));
        }
    }

    private static class BlockLootTableProvider extends FabricBlockLootTableProvider {

        protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> lookup) {
            super(dataOutput, lookup);
        }

        @Override
        public void generate() {
            dropSelf(BovinesBlocks.BIRD_OF_PARADISE);
            dropSelf(BovinesBlocks.BUTTERCUP);
            dropSelf(BovinesBlocks.CHARGELILY);
            dropSelf(BovinesBlocks.FREESIA);
            dropSelf(BovinesBlocks.HYACINTH);
            dropSelf(BovinesBlocks.LIMELIGHT);
            dropSelf(BovinesBlocks.LINGHOLM);
            dropSelf(BovinesBlocks.PINK_DAISY);
            dropSelf(BovinesBlocks.SNOWDROP);
            dropSelf(BovinesBlocks.TROPICAL_BLUE);

            dropPottedContents(BovinesBlocks.POTTED_BIRD_OF_PARADISE);
            dropPottedContents(BovinesBlocks.POTTED_BUTTERCUP);
            dropPottedContents(BovinesBlocks.POTTED_CHARGELILY);
            dropPottedContents(BovinesBlocks.POTTED_FREESIA);
            dropPottedContents(BovinesBlocks.POTTED_HYACINTH);
            dropPottedContents(BovinesBlocks.POTTED_LIMELIGHT);
            dropPottedContents(BovinesBlocks.POTTED_LINGHOLM);
            dropPottedContents(BovinesBlocks.POTTED_PINK_DAISY);
            dropPottedContents(BovinesBlocks.POTTED_SNOWDROP);
            dropPottedContents(BovinesBlocks.POTTED_TROPICAL_BLUE);

            dropSelf(BovinesBlocks.RICH_HONEY_BLOCK);

            add(BovinesBlocks.CUSTOM_FLOWER,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BovinesItems.CUSTOM_FLOWER))
                                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(BovinesDataComponents.CUSTOM_FLOWER))
                                    .when(ExplosionCondition.survivesExplosion())
                    )
            );
            add(BovinesBlocks.POTTED_CUSTOM_FLOWER,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(Items.FLOWER_POT))
                                    .when(ExplosionCondition.survivesExplosion())
                            ).withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BovinesItems.CUSTOM_FLOWER))
                                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(BovinesDataComponents.CUSTOM_FLOWER))
                                    .when(ExplosionCondition.survivesExplosion())
                    )
            );

            add(BovinesBlocks.CUSTOM_MUSHROOM,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BovinesItems.CUSTOM_MUSHROOM))
                                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(BovinesDataComponents.CUSTOM_MUSHROOM))
                                    .when(ExplosionCondition.survivesExplosion())
                    )
            );
            add(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BovinesItems.CUSTOM_MUSHROOM_BLOCK))
                                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(BovinesDataComponents.CUSTOM_MUSHROOM))
                                    .when(ExplosionCondition.survivesExplosion())
                    )
            );
            add(BovinesBlocks.POTTED_CUSTOM_MUSHROOM,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(Items.FLOWER_POT))
                                    .when(ExplosionCondition.survivesExplosion())
                            ).withPool(LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BovinesItems.CUSTOM_MUSHROOM))
                                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(BovinesDataComponents.CUSTOM_MUSHROOM))
                                    .when(ExplosionCondition.survivesExplosion())
                            )
            );
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
        public BiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, Registries.BIOME, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
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
            ((FabricTagBuilder)tag(BovinesTags.BiomeTags.HAS_MOOSHROOM_MUSHROOM))
                    .add(Biomes.MUSHROOM_FIELDS);
            tag(BovinesTags.BiomeTags.PREVENT_COW_SPAWNS)
                    .addTag(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST);
        }
    }

    private static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            ((FabricTagBuilder)tag(BlockTags.SMALL_FLOWERS))
                    .forceAddTag(BovinesTags.BlockTags.MOOBLOOM_FLOWERS);

            List<ResourceKey<Block>> candleVariants = new ArrayList<>();
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.BUTTERCUP_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.PINK_DAISY_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.LIMELIGHT_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.BIRD_OF_PARADISE_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.CHARGELILY_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.HYACINTH_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.SNOWDROP_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.TROPICAL_BLUE_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.FREESIA_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            CandleCupcakeBlock.forEachCandleVariant(BovinesBlocks.LINGHOLM_CUPCAKE, block -> candleVariants.add(reverseLookup(block)));
            tag(BovinesTags.BlockTags.CANDLE_CUPCAKES)
                    .add(candleVariants.toArray(ResourceKey[]::new));

            ((FabricTagBuilder)tag(BovinesTags.BlockTags.DOES_NOT_STICK_RICH_HONEY_BLOCK))
                    .add(reverseLookup(Blocks.SLIME_BLOCK))
                    .add(reverseLookup(Blocks.HONEY_BLOCK));
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
        public ConfiguredFeatureTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, Registries.CONFIGURED_FEATURE, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
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
        public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            tag(BovinesTags.EntityTypeTags.WILL_EQUIP_FLOWER_CROWN)
                    .add(reverseLookup(EntityType.PIGLIN))
                    .add(reverseLookup(EntityType.VILLAGER));
        }
    }

    private static class FlowerCrownMaterialTagProvider extends FabricTagProvider<FlowerCrownMaterial> {
        public FlowerCrownMaterialTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, BovinesRegistryKeys.FLOWER_CROWN_MATERIAL, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            tag(BovinesTags.FlowerCrownMaterialTags.CREATIVE_MENU_ORDER)
                    .add(BovinesFlowerCrownMaterials.FREESIA)
                    .add(BovinesFlowerCrownMaterials.BIRD_OF_PARADISE)
                    .add(BovinesFlowerCrownMaterials.BUTTERCUP)
                    .add(BovinesFlowerCrownMaterials.LIMELIGHT)
                    .add(BovinesFlowerCrownMaterials.LINGHOLM)
                    .add(BovinesFlowerCrownMaterials.CHARGELILY)
                    .add(BovinesFlowerCrownMaterials.TROPICAL_BLUE)
                    .add(BovinesFlowerCrownMaterials.HYACINTH)
                    .add(BovinesFlowerCrownMaterials.PINK_DAISY)
                    .add(BovinesFlowerCrownMaterials.SNOWDROP);
        }
    }

    private static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
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
            tag(ConventionalItemTags.FOODS)
                    .add(reverseLookup(BovinesItems.RICH_HONEY_BOTTLE));
            tag(BovinesConventionalTags.ConventionalItemTags.HONEY_FOODS)
                    .add(reverseLookup(Items.HONEY_BOTTLE))
                    .add(reverseLookup(BovinesItems.RICH_HONEY_BOTTLE));
        }
    }

    private static class NectarTagProvider extends FabricTagProvider<Nectar> {
        public NectarTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, BovinesRegistryKeys.NECTAR, lookup);
        }

        @Override
        protected void addTags(HolderLookup.Provider lookup) {
            tag(BovinesTags.NectarTags.CREATIVE_MENU_ORDER)
                    .add(BovinesNectars.FREESIA)
                    .add(BovinesNectars.BIRD_OF_PARADISE)
                    .add(BovinesNectars.BUTTERCUP)
                    .add(BovinesNectars.LIMELIGHT)
                    .add(BovinesNectars.LINGHOLM)
                    .add(BovinesNectars.CHARGELILY)
                    .add(BovinesNectars.TROPICAL_BLUE)
                    .add(BovinesNectars.HYACINTH)
                    .add(BovinesNectars.PINK_DAISY)
                    .add(BovinesNectars.SNOWDROP);
        }
    }
}
