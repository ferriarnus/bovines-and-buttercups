package net.merchantpug.bovinesandbuttercups.content.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.block.BlockReference;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.api.cowtype.BackGrassConfiguration;
import net.minecraft.core.Holder;

import java.util.List;
import java.util.Optional;

public record MooshroomConfiguration(Settings settings,
                                     BlockReference<Holder<CustomMushroomType>> mushroom,
                                     Optional<BackGrassConfiguration> backGrass,
                                     boolean canEatFlowers,
                                     boolean vanillaSpawningHack) implements CowTypeConfiguration {

    public static final MooshroomConfiguration DEFAULT = new MooshroomConfiguration(new Settings(Optional.empty(), List.of(), List.of(), Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomMushroomType.MISSING))), Optional.of(new BackGrassConfiguration(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/mooshroom/mooshroom_mycelium.png"), false)), false, false);

    public static final MapCodec<MooshroomConfiguration> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Settings.CODEC.forGetter(MooshroomConfiguration::settings),
            BlockReference.createCodec(CustomMushroomType.CODEC, "custom_mushroom").fieldOf("mushroom").forGetter(MooshroomConfiguration::mushroom),
            BackGrassConfiguration.codec(BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/mooshroom/mooshroom_mycelium.png"), false).optionalFieldOf("back_grass").forGetter(MooshroomConfiguration::backGrass),
            Codec.BOOL.fieldOf("can_eat_flowers").forGetter(MooshroomConfiguration::canEatFlowers),
            Codec.BOOL.fieldOf("vanilla_spawning_hack").forGetter(MooshroomConfiguration::vanillaSpawningHack)
    ).apply(inst, MooshroomConfiguration::new));

}
