package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.api.codec.BovinesCodecs;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;

/**
 * The generic cow type interface, it's mostly here to make sure that
 * the game knows that your cow types are cow types.
 */
public interface CowTypeConfiguration {
    /**
     * Optional settings for your cow type, they are not in the base class as you may not want them.
     *
     * @param cowTexture         A {@link ResourceLocation} for where in the assets the cow's texture is located,
     *                           if not set, it'll default to a hardcoded value depending on the cow.
     * @param biomes             A set of weighted biomes to determine whether the entity can spawn in the specified biome.
     * @param thunderConverts    A list of weighted cow types that this cow will/have a chance to convert into upon being struck by lightning.
     *                           Can be Optional.empty() to keep the default thunder behavior.
     */
    record Settings(Optional<ResourceLocation> cowTexture,
                    List<WeightedEntry.Wrapper<HolderSet<Biome>>> biomes,
                    List<WeightedCowType> thunderConverts,
                    Optional<ParticleOptions> particle) {
        public static final MapCodec<Settings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("texture_location").forGetter(Settings::cowTexture),
                BovinesCodecs.wrapperCodec(HolderSetCodec.create(Registries.BIOME, Biome.CODEC, false), "biomes").listOf().fieldOf("natural_spawns").forGetter(Settings::biomes),
                WeightedCowType.CODEC.listOf().optionalFieldOf("thunder_conversion_types", List.of()).forGetter(Settings::thunderConverts),
                ParticleTypes.CODEC.optionalFieldOf("particle").forGetter(Settings::particle)
        ).apply(instance, Settings::new));
    }

    /**
     * A reference to a {@link CowTypeType}, as well as an integer weight. Used for weight based random chance selection.
     *
     * @param cowType   A {@link Holder} that should contain a configured cow type.
     * @param weight    The weight of this WeightedConfiguredCowType.
     */
    record WeightedCowType(Holder<CowType<?>> cowType, int weight) {
        public static final Codec<WeightedCowType> DIRECT_CODEC =RecordCodecBuilder.create(builder -> builder.group(
                CowType.CODEC.fieldOf("type").forGetter(WeightedCowType::cowType),
                    Codec.INT.optionalFieldOf("weight", 1).forGetter(WeightedCowType::weight)
            ).apply(builder, WeightedCowType::new));

        public static final Codec<WeightedCowType> CODEC = Codec.either(DIRECT_CODEC, CowType.CODEC)
                .xmap(either -> either.map(o -> o, type -> new WeightedCowType(type, 1)), Either::left);;
    }
}
