package house.greenhouse.bovinesandbuttercups.content.data.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.CowTypeConfiguration;
import house.greenhouse.bovinesandbuttercups.api.block.BlockReference;
import house.greenhouse.bovinesandbuttercups.api.block.CustomMushroomType;
import house.greenhouse.bovinesandbuttercups.api.cowtype.CowModelLayer;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Optional;

public record MooshroomConfiguration(Settings settings,
                                     BlockReference<Holder<CustomMushroomType>> mushroom,
                                     List<CowModelLayer> layers,
                                     boolean canEatFlowers,
                                     boolean vanillaSpawningHack) implements CowTypeConfiguration {

    public static final MooshroomConfiguration DEFAULT = new MooshroomConfiguration(new Settings(Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/missing_mooshroom")), List.of(), List.of(), Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomMushroomType.MISSING))), List.of(new CowModelLayer(BovinesAndButtercups.asResource("bovinesandbuttercups/mooshroom/mooshroom_mycelium_layer"), List.of())), false, false);

    public static final MapCodec<MooshroomConfiguration> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Settings.CODEC.forGetter(MooshroomConfiguration::settings),
            BlockReference.createCodec(CustomMushroomType.CODEC, "custom_mushroom").fieldOf("mushroom").forGetter(MooshroomConfiguration::mushroom),
            CowModelLayer.CODEC.listOf().optionalFieldOf("layers", List.of()).forGetter(MooshroomConfiguration::layers),
            Codec.BOOL.fieldOf("can_eat_flowers").forGetter(MooshroomConfiguration::canEatFlowers),
            Codec.BOOL.fieldOf("vanilla_spawning_hack").forGetter(MooshroomConfiguration::vanillaSpawningHack)
    ).apply(inst, MooshroomConfiguration::new));

    public void tick(Entity entity) {
        layers.forEach(cowModelLayer -> cowModelLayer.tickTextureModifiers(entity));
    }
}
