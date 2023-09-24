package net.merchantpug.bovinesandbuttercups.api.cowtypes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.BovinesCowTypes;

import java.util.Optional;
import java.util.function.Supplier;

public record MoobloomConfiguration(Settings settings) implements CowTypeConfiguration {

    public static final Supplier<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> DEFAULT = () -> new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.get(), new MoobloomConfiguration(new CowTypeConfiguration.Settings(Optional.empty(), Optional.empty(), 0, Optional.empty())));

    public static final MapCodec<MoobloomConfiguration> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Settings.CODEC.forGetter(MoobloomConfiguration::settings)
    ).apply(builder, MoobloomConfiguration::new));

}
