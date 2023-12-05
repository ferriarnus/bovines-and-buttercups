package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class BovinesResourceKeys {

    public static final ResourceKey<Registry<CowType<?>>> COW_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("cow_type"));
    public static final ResourceKey<Registry<ConfiguredCowType<?, ?>>> CONFIGURED_COW_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("configured_cow_type"));
    public static final ResourceKey<Registry<CustomFlowerType>> CUSTOM_FLOWER_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("flower_type"));
    public static final ResourceKey<Registry<CustomMushroomType>> CUSTOM_MUSHROOM_TYPE = ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("mushroom_type"));

    public static class MoobloomKeys {
        public static final ResourceKey<ConfiguredCowType<?, ?>> BIRD_OF_PARADISE = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("bird_of_paradise"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> BUTTERCUP = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("buttercup"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> CHARGELILY = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("chargelily"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> FREESIA = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("freesia"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> HYACINTH = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("hyacinth"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> LIMELIGHT = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("limelight"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> PINK_DAISY = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("pink_daisy"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> SNOWDROP = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("snowdrop"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> TROPICAL_BLUE = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("tropical_blue"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> MISSING_MOOBLOOM = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("missing_moobloom"));
    }

    public static class MooshroomKeys {
        public static final ResourceKey<ConfiguredCowType<?, ?>> RED_MUSHROOM = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("red_mushroom"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> BROWN_MUSHROOM = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("brown_mushroom"));
        public static final ResourceKey<ConfiguredCowType<?, ?>> MISSING_MOOSHROOM = ResourceKey.create(CONFIGURED_COW_TYPE, BovinesAndButtercups.asResource("missing_mooshroom"));
    }

}
