package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
     * Optional settings for your cow type, they are not in the base class, mainly for the sake of being optional.
     *
     * @param cowTexture         A {@link ResourceLocation} for where in the assets the cow's texture is located,
     *                           if not set, it'll default to a hardcoded value depending on the cow.
     * @param biomes             Either a biome or a biome tag where the cow can spawn.
     * @param naturalSpawnWeight The natural spawn weight for this cow in relation to other cows of its type. Any value below 1 should be ignored.
     * @param thunderConverts    A list of weighted cow types that this cow will/have a chance to convert into upon being struck by lightning.
     *                           Can be Optional.empty() to keep the default thunder behavior.
     */
    public record Settings(Optional<ResourceLocation> cowTexture, Optional<HolderSet<Biome>> biomes, int naturalSpawnWeight, Optional<List<WeightedConfiguredCowType>> thunderConverts) {
        public static final MapCodec<Settings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("texture_location").forGetter(Settings::cowTexture),
                HolderSetCodec.create(Registries.BIOME, Biome.CODEC, false).optionalFieldOf("spawn_biomes").forGetter(Settings::biomes),
                Codec.INT.optionalFieldOf("natural_spawn_weight", 0).forGetter(Settings::naturalSpawnWeight),
                Codec.list(WeightedConfiguredCowType.CODEC).optionalFieldOf("thunder_conversion_types").forGetter(Settings::thunderConverts)
        ).apply(instance, Settings::new));
    }

    public record WeightedConfiguredCowType(ResourceLocation configuredCowTypeResource, int weight) {
        public static final Codec<WeightedConfiguredCowType> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(WeightedConfiguredCowType::configuredCowTypeResource),
                Codec.INT.optionalFieldOf("weight", 1).forGetter(WeightedConfiguredCowType::weight)
        ).apply(builder, WeightedConfiguredCowType::new));

        public static final Codec<WeightedConfiguredCowType> CODEC = Codec.either(DIRECT_CODEC, ResourceLocation.CODEC)
                .xmap(objectResourceLocationEither -> objectResourceLocationEither.map(o -> o, location -> new WeightedConfiguredCowType(location, 1)), Either::left);
    }
}
