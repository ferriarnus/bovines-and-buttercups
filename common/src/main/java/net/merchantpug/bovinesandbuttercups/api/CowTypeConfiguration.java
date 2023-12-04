package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;

/**
 * The generic cow type interface, it's moreso here to make sure that
 * the game knows that your cow types are cow types.
 */
public interface CowTypeConfiguration {

    /**
     * Optional settings for your cow type, they are not in the base class as you may not want them.
     *
     * @param cowTexture         A {@link ResourceLocation} for where in the assets the cow's texture is located,
     *                           if not set, it'll default to a hardcoded value depending on the cow.
     * @param biomes             Either a biome or a biome tag where the cow can spawn.
     * @param naturalSpawnWeight The natural spawn weight for this cow in relation to other cows of its type. Any value below 1 should be ignored.
     * @param thunderConverts    A list of weighted cow types that this cow will/have a chance to convert into upon being struck by lightning.
     *                           Can be Optional.empty() to keep the default thunder behavior.
     */
    record Settings(Optional<ResourceLocation> cowTexture, Optional<HolderSet<Biome>> biomes, int naturalSpawnWeight, Optional<List<WeightedConfiguredCowType>> thunderConverts) {
        public static final MapCodec<Settings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("texture_location").forGetter(Settings::cowTexture),
                HolderSetCodec.create(Registries.BIOME, Biome.CODEC, false).optionalFieldOf("spawn_biomes").forGetter(Settings::biomes),
                Codec.INT.optionalFieldOf("natural_spawn_weight", 0).forGetter(Settings::naturalSpawnWeight),
                Codec.list(WeightedConfiguredCowType.CODEC).optionalFieldOf("thunder_conversion_types").forGetter(Settings::thunderConverts)
        ).apply(instance, Settings::new));
    }

    /**
     * A reference to a {@link ConfiguredCowType}, as well as an integer weight. Used for weight based random chance selection.
     *
     * @param configuredCowType A {@link Holder} that should contain a configured cow type.
     * @param weight            The weight of this WeightedConfiguredCowType.
     */
    record WeightedConfiguredCowType(Holder<ConfiguredCowType<?, ?>> configuredCowType, int weight) {
        public static final Codec<WeightedConfiguredCowType> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
                ConfiguredCowType.CODEC.fieldOf("type").forGetter(WeightedConfiguredCowType::configuredCowType),
                Codec.INT.optionalFieldOf("weight", 1).forGetter(WeightedConfiguredCowType::weight)
        ).apply(builder, WeightedConfiguredCowType::new));

        public static final Codec<WeightedConfiguredCowType> CODEC = Codec.either(DIRECT_CODEC, ConfiguredCowType.CODEC)
                .xmap(either -> either.map(o -> o, type -> new WeightedConfiguredCowType(type, 1)), Either::left);
    }
}
