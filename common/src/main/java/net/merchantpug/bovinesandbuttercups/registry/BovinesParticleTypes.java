package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.particle.ModelLocationParticleOptions;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class BovinesParticleTypes {
    public static final ParticleType<ModelLocationParticleOptions> MODEL_LOCATION = create(false, type -> ModelLocationParticleOptions.CODEC, type -> ModelLocationParticleOptions.STREAM_CODEC);
    public static final ParticleType<ColorParticleOption> BLOOM = create(false, ColorParticleOption::codec, ColorParticleOption::streamCodec);
    public static final ParticleType<ColorParticleOption> SHROOM = create(false, ColorParticleOption::codec, ColorParticleOption::streamCodec);

    public static void registerAll(RegistrationCallback<ParticleType<?>> callback) {
        callback.register(BuiltInRegistries.PARTICLE_TYPE, BovinesAndButtercups.asResource("model_location"), MODEL_LOCATION);
        callback.register(BuiltInRegistries.PARTICLE_TYPE, BovinesAndButtercups.asResource("bloom"), BLOOM);
        callback.register(BuiltInRegistries.PARTICLE_TYPE, BovinesAndButtercups.asResource("shroom"), SHROOM);
    }

    private static <T extends ParticleOptions> ParticleType<T> create(boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codec, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec) {
        return new ParticleType<T>(alwaysShow) {
            public MapCodec<T> codec() {
                return codec.apply(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec.apply(this);
            }
        };
    }
}
