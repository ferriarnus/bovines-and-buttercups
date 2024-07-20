package net.merchantpug.bovinesandbuttercups.datagen;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.merchantpug.bovinesandbuttercups.api.BovinesTags;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesLootTables;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
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
        pack.addProvider(DynamicRegistryGenerator::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ChestLootTableProvider::new);
        pack.addProvider(EntityLootTableProvider::new);
        pack.addProvider(BiomeTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(BovinesRegistryKeys.COW_TYPE, BovinesCowTypes::bootstrap);
    }

    private static class DynamicRegistryGenerator extends FabricDynamicRegistryProvider {

        public DynamicRegistryGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            BovinesCowTypes.bootstrap(createContext(registries, entries));
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
            this.dropSelf(BovinesBlocks.PINK_DAISY);
            this.dropSelf(BovinesBlocks.SNOWDROP);
            this.dropSelf(BovinesBlocks.TROPICAL_BLUE);

            this.dropOther(BovinesBlocks.POTTED_BIRD_OF_PARADISE, BovinesBlocks.BIRD_OF_PARADISE);
            this.dropOther(BovinesBlocks.POTTED_BUTTERCUP, BovinesBlocks.BUTTERCUP);
            this.dropOther(BovinesBlocks.POTTED_CHARGELILY, BovinesBlocks.CHARGELILY);
            this.dropOther(BovinesBlocks.POTTED_FREESIA, BovinesBlocks.FREESIA);
            this.dropOther(BovinesBlocks.POTTED_HYACINTH, BovinesBlocks.HYACINTH);
            this.dropOther(BovinesBlocks.POTTED_LIMELIGHT, BovinesBlocks.LIMELIGHT);
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
                            .setRolls(ConstantValue.exactly(1.0F))
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
            tag(BovinesTags.BiomeTags.HAS_MOOBLOOM_FLOWER_FOREST)
                    .add(Biomes.FLOWER_FOREST);
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
            tag(BovinesTags.BlockTags.MOOBLOOM_FLOWERS)
                    .add(
                            reverseLookup(BovinesBlocks.BIRD_OF_PARADISE),
                            reverseLookup(BovinesBlocks.BUTTERCUP),
                            reverseLookup(BovinesBlocks.CHARGELILY),
                            reverseLookup(BovinesBlocks.CUSTOM_FLOWER),
                            reverseLookup(BovinesBlocks.FREESIA),
                            reverseLookup(BovinesBlocks.HYACINTH),
                            reverseLookup(BovinesBlocks.LIMELIGHT),
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

    private static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            tag(BovinesTags.ItemTags.MOOBLOOM_FLOWERS)
                    .add(
                            reverseLookup(BovinesItems.BIRD_OF_PARADISE),
                            reverseLookup(BovinesItems.BUTTERCUP),
                            reverseLookup(BovinesItems.CHARGELILY),
                            reverseLookup(BovinesItems.CUSTOM_FLOWER),
                            reverseLookup(BovinesItems.FREESIA),
                            reverseLookup(BovinesItems.HYACINTH),
                            reverseLookup(BovinesItems.LIMELIGHT),
                            reverseLookup(BovinesItems.PINK_DAISY),
                            reverseLookup(BovinesItems.SNOWDROP),
                            reverseLookup(BovinesItems.TROPICAL_BLUE)
                    );
        }
    }
}
