package net.merchantpug.bovinesandbuttercups.content.configuration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.cowtype.BackGrassConfiguration;
import net.merchantpug.bovinesandbuttercups.api.cowtype.OffspringConditionsConfiguration;
import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record MoobloomConfiguration(Settings settings,
                                    BlockReference<Holder<CustomFlowerType>> flower,
                                    BlockReference<Holder<CustomFlowerType>> bud,
                                    Optional<BackGrassConfiguration> backGrass,
                                    Optional<ResourceLocation> nectarPalette,
                                    NectarEffects nectarEffects,
                                    OffspringConditionsConfiguration offspringConditions) implements CowTypeConfiguration {

    public static final MoobloomConfiguration DEFAULT = new MoobloomConfiguration(new CowTypeConfiguration.Settings(Optional.empty(), List.of(), List.of(), Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true)), Optional.empty(), NectarEffects.EMPTY, OffspringConditionsConfiguration.EMPTY);

    public static final MapCodec<MoobloomConfiguration> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Settings.CODEC.forGetter(MoobloomConfiguration::settings),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("flower").forGetter(MoobloomConfiguration::flower),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("bud").forGetter(MoobloomConfiguration::bud),
            BackGrassConfiguration.codec(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/moobloom_grass.png"), true).optionalFieldOf("back_grass").forGetter(MoobloomConfiguration::backGrass),
            ResourceLocation.CODEC.optionalFieldOf("nectar_palette").forGetter(MoobloomConfiguration::nectarPalette),
            NectarEffects.CODEC.optionalFieldOf("nectar_effects", NectarEffects.EMPTY).forGetter(MoobloomConfiguration::nectarEffects),
            OffspringConditionsConfiguration.CODEC.optionalFieldOf("offspring_conditions", OffspringConditionsConfiguration.EMPTY).forGetter(MoobloomConfiguration::offspringConditions)
    ).apply(builder, MoobloomConfiguration::new));

}
