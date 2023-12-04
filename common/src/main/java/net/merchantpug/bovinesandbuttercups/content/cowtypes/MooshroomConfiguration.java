package net.merchantpug.bovinesandbuttercups.content.cowtypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.api.codec.BovinesCodecs;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.BackGrassConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;
import java.util.function.Supplier;

public record MooshroomConfiguration(Settings settings,
                                     BlockReference<Holder<CustomMushroomType>> mushroom,
                                     Optional<BackGrassConfiguration> backGrass,
                                     boolean canEatFlowers,
                                     boolean vanillaSpawningHack) implements CowTypeConfiguration {

    public static final Supplier<ConfiguredCowType<MooshroomConfiguration, CowType<MooshroomConfiguration>>> DEFAULT = () -> new ConfiguredCowType<>(BovinesCowTypes.MOOSHROOM_TYPE.value(), new MooshroomConfiguration(new Settings(Optional.empty(), Optional.empty(), 0, Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomMushroomType.MISSING))), Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/mooshroom/mooshroom_mycelium.png"), false)), false, false));

    public static final MapCodec<MooshroomConfiguration> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Settings.CODEC.forGetter(MooshroomConfiguration::settings),
            BlockReference.createCodec(CustomMushroomType.CODEC, "custom_mushroom").fieldOf("mushroom").forGetter(MooshroomConfiguration::mushroom),
            ExtraCodecs.strictOptionalField(BackGrassConfiguration.codec(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/mooshroom/mooshroom_mycelium.png"), false), "back_grass").forGetter(MooshroomConfiguration::backGrass),
            Codec.BOOL.fieldOf("can_eat_flowers").forGetter(MooshroomConfiguration::canEatFlowers),
            Codec.BOOL.fieldOf("vanilla_spawning_hack").forGetter(MooshroomConfiguration::vanillaSpawningHack)
    ).apply(builder, MooshroomConfiguration::new));

}
