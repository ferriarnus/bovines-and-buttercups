package net.merchantpug.bovinesandbuttercups.content.cowtypes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.codec.BovinesCodecs;
import net.merchantpug.bovinesandbuttercups.api.cowtypes.BackGrassConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;
import java.util.function.Supplier;

public record MoobloomConfiguration(Settings<MoobloomConfiguration, CowType<MoobloomConfiguration>> settings,
                                    BlockReference<Holder<CustomFlowerType>> flower,
                                    BlockReference<Holder<CustomFlowerType>> bud,
                                    Optional<BackGrassConfiguration> backGrass,
                                    Optional<ResourceLocation> nectarTexture,
                                    Optional<MobEffectInstance> nectarEffect) implements CowTypeConfiguration {

    public static final Supplier<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> DEFAULT = () -> new ConfiguredCowType<>(BovinesCowTypes.MOOBLOOM_TYPE.value(), new MoobloomConfiguration(new CowTypeConfiguration.Settings(Optional.empty(), Optional.empty(), 0, Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true)), Optional.empty(), Optional.empty()));

    public static final MapCodec<MoobloomConfiguration> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Settings.codec(BovinesCowTypes.MOOBLOOM_TYPE).forGetter(MoobloomConfiguration::settings),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("flower").forGetter(MoobloomConfiguration::flower),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("bud").forGetter(MoobloomConfiguration::bud),
            ExtraCodecs.strictOptionalField(BackGrassConfiguration.codec(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true), "back_grass").forGetter(MoobloomConfiguration::backGrass),
            ResourceLocation.CODEC.optionalFieldOf("nectar_texture").forGetter(MoobloomConfiguration::nectarTexture),
            BovinesCodecs.MOB_EFFECT_INSTANCE.optionalFieldOf("nectar_effect").forGetter(MoobloomConfiguration::nectarEffect)
    ).apply(builder, MoobloomConfiguration::new));

}
