package net.merchantpug.bovinesandbuttercups.api.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import org.joml.Vector3f;

import java.util.List;

public class BovinesCodecs {
    public static final Codec<MobEffectInstance> MOB_EFFECT_INSTANCE = RecordCodecBuilder.create((builder) -> builder.group(
            BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("effect").forGetter(MobEffectInstance::getEffect),
            Codec.INT.fieldOf("duration").forGetter(MobEffectInstance::getDuration),
            Codec.INT.optionalFieldOf("amplifier", 0).forGetter(MobEffectInstance::getAmplifier),
            Codec.BOOL.optionalFieldOf("ambient", false).forGetter(MobEffectInstance::isAmbient),
            Codec.BOOL.optionalFieldOf("visible", false).forGetter(MobEffectInstance::isVisible),
            Codec.BOOL.optionalFieldOf("show_icon", true).forGetter(MobEffectInstance::showIcon)
    ).apply(builder, MobEffectInstance::new));

    public static final Codec<Vector3f> CODEC = Codec.either(ExtraCodecs.VECTOR3F, Codec.STRING).xmap(vec3StringEither -> vec3StringEither.map(vector3f -> vector3f, s -> {
        String hex = s;
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        } else if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        try {
            String r = "#" + hex.substring(0, 2);
            String g = "#" + hex.substring(2, 4);
            String b = "#" + hex.substring(4, 6);
            return new Vector3f(Integer.decode(r) / 255.0F, Integer.decode(g) / 255.0F, Integer.decode(b) / 255.0F);
        } catch (Throwable e) {
            BovinesAndButtercups.LOG.warn("Could not serialize Color data type '{}'. (Skipping.) {}.", hex, e.getMessage());
        }
        return new Vector3f(1.0F, 1.0F, 1.0F);
    }), Either::left);

    public static <T> Codec<List<T>> singleOrListCodec(Codec<T> codec) {
        return Codec.either(codec.listOf(), codec).xmap((list) -> list.map((list1) -> list1, List::of), (t) -> t.size() == 1 ? Either.right(t.get(0)) : Either.left(t));
    }

}
