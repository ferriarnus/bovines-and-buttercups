package net.merchantpug.bovinesandbuttercups.api.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.phys.AABB;

public class BovinesCodecs {
    public static final Codec<AABB> AABB = RecordCodecBuilder.create(inst -> inst.group(
            Codec.DOUBLE.optionalFieldOf("min_x", 0.0).forGetter(aabb -> aabb.minX),
            Codec.DOUBLE.optionalFieldOf("min_y", 0.0).forGetter(aabb -> aabb.minY),
            Codec.DOUBLE.optionalFieldOf("min_z", 0.0).forGetter(aabb -> aabb.minZ),
            Codec.DOUBLE.optionalFieldOf("max_x", 0.0).forGetter(aabb -> aabb.maxX),
            Codec.DOUBLE.optionalFieldOf("max_y", 0.0).forGetter(aabb -> aabb.maxY),
            Codec.DOUBLE.optionalFieldOf("max_z", 0.0).forGetter(aabb -> aabb.maxZ)
    ).apply(inst, AABB::new));

    public static <T> Codec<WeightedEntry.Wrapper<T>> weightedEntryCodec(Codec<T> codec, String fieldName) {
        Codec<WeightedEntry.Wrapper<T>> direct = RecordCodecBuilder.create(inst -> inst.group(
                codec.fieldOf(fieldName).forGetter(WeightedEntry.Wrapper::data),
                Weight.CODEC.optionalFieldOf("weight", Weight.of(1)).forGetter(WeightedEntry.Wrapper::weight)
        ).apply(inst, WeightedEntry.Wrapper::new));

        return Codec.either(codec, direct)
                .xmap(either -> either.map(t -> new WeightedEntry.Wrapper<>(t, Weight.of(1)), weighted -> weighted),
                wrapper -> {
                    if (wrapper.weight().asInt() == 1)
                        return Either.left(wrapper.data());
                    return Either.right(wrapper);
                });
    }
}
