package house.greenhouse.bovinesandbuttercups.content.data.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.CowTypeConfiguration;
import house.greenhouse.bovinesandbuttercups.api.block.BlockReference;
import house.greenhouse.bovinesandbuttercups.api.block.CustomMushroomType;
import house.greenhouse.bovinesandbuttercups.api.cowtype.CowModelLayer;
import house.greenhouse.bovinesandbuttercups.api.cowtype.OffspringConditions;
import net.minecraft.core.Holder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;

import java.util.List;
import java.util.Optional;

public record MooshroomConfiguration(Settings settings,
                                     BlockReference<Holder<CustomMushroomType>> mushroom,
                                     List<CowModelLayer> layers,
                                     boolean canEatFlowers,
                                     Optional<MushroomCow.MushroomType> vanillaType,
                                     OffspringConditions offspringConditions) implements CowTypeConfiguration {

    public static final MooshroomConfiguration DEFAULT = new MooshroomConfiguration(new Settings(Optional.of(BovinesAndButtercups.asResource("bovinesandbuttercups/moobloom/missing_mooshroom")), SimpleWeightedRandomList.empty(), SimpleWeightedRandomList.empty(), Optional.empty()), new BlockReference<>(Optional.empty(), Optional.empty(), Optional.of(Holder.direct(CustomMushroomType.MISSING))), List.of(new CowModelLayer(BovinesAndButtercups.asResource("bovinesandbuttercups/mooshroom/mooshroom_mycelium_layer"), List.of())), false, Optional.empty(), OffspringConditions.EMPTY);

    public static final MapCodec<MooshroomConfiguration> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Settings.CODEC.forGetter(MooshroomConfiguration::settings),
            BlockReference.createCodec(CustomMushroomType.CODEC, "custom_mushroom").fieldOf("mushroom").forGetter(MooshroomConfiguration::mushroom),
            CowModelLayer.CODEC.listOf().optionalFieldOf("layers", List.of()).forGetter(MooshroomConfiguration::layers),
            Codec.BOOL.fieldOf("can_eat_flowers").forGetter(MooshroomConfiguration::canEatFlowers),
            MushroomCow.MushroomType.CODEC.optionalFieldOf("vanilla_type").forGetter(MooshroomConfiguration::vanillaType),
            OffspringConditions.CODEC.optionalFieldOf("offspring_conditions", OffspringConditions.EMPTY).forGetter(MooshroomConfiguration::offspringConditions)
    ).apply(inst, MooshroomConfiguration::new));

    public void tick(Entity entity) {
        layers.forEach(cowModelLayer -> cowModelLayer.tickTextureModifiers(entity));
    }
}
