package net.merchantpug.bovinesandbuttercups.content.data.configuration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.cowtype.CowModelLayer;
import net.merchantpug.bovinesandbuttercups.api.cowtype.OffspringConditions;
import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.merchantpug.bovinesandbuttercups.content.data.modifier.GrassTintTextureModifierFactory;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Optional;

public record MoobloomConfiguration(Settings settings,
                                    BlockReference<Holder<CustomFlowerType>> flower,
                                    BlockReference<Holder<CustomFlowerType>> bud,
                                    List<CowModelLayer> layers,
                                    Optional<ResourceLocation> nectarPalette,
                                    NectarEffects nectarEffects,
                                    OffspringConditions offspringConditions) implements CowTypeConfiguration {

    public static final MoobloomConfiguration DEFAULT = new MoobloomConfiguration(new CowTypeConfiguration.Settings(Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/missing_moobloom")), List.of(), List.of(), Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomFlowerType.MISSING))), List.of(new CowModelLayer(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/moobloom_grass_layer"), List.of(new GrassTintTextureModifierFactory()))), Optional.empty(), NectarEffects.EMPTY, OffspringConditions.EMPTY);

    public static final MapCodec<MoobloomConfiguration> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Settings.CODEC.forGetter(MoobloomConfiguration::settings),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("flower").forGetter(MoobloomConfiguration::flower),
            BlockReference.createCodec(CustomFlowerType.CODEC, "custom_flower").fieldOf("bud").forGetter(MoobloomConfiguration::bud),
            CowModelLayer.CODEC.listOf().optionalFieldOf("layers", List.of()).forGetter(MoobloomConfiguration::layers),
            ResourceLocation.CODEC.optionalFieldOf("nectar_palette").forGetter(MoobloomConfiguration::nectarPalette),
            NectarEffects.CODEC.optionalFieldOf("nectar_effects", NectarEffects.EMPTY).forGetter(MoobloomConfiguration::nectarEffects),
            OffspringConditions.CODEC.optionalFieldOf("offspring_conditions", OffspringConditions.EMPTY).forGetter(MoobloomConfiguration::offspringConditions)
    ).apply(builder, MoobloomConfiguration::new));

    public void tick(Entity entity) {
        layers.forEach(cowModelLayer -> cowModelLayer.tickTextureModifiers(entity));
    }
}
