package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;

public record ConfiguredCowType<CTC extends CowTypeConfiguration, CT extends CowType<CTC>>(CT cowType, CTC configuration) {

    public static final Codec<ConfiguredCowType<?, ?>> DIRECT_CODEC = CowType.CODEC.dispatch(ConfiguredCowType::cowType, CowType::codec);

    public static final Codec<Holder<ConfiguredCowType<?, ?>>> CODEC = RegistryFileCodec.create(BovinesResourceKeys.CONFIGURED_COW_TYPE, DIRECT_CODEC);

    public static <CTC extends CowTypeConfiguration, CT extends CowType<CTC>> Codec<Holder<ConfiguredCowType<CTC, CT>>> filteredCodec(Holder<CT> cowType) {
        return new Codec<>() {
            @Override
            public <T> DataResult<Pair<Holder<ConfiguredCowType<CTC, CT>>, T>> decode(DynamicOps<T> ops, T input) {
                var original = CODEC.decode(ops, input);
                if (original.error().isPresent()) {
                    return DataResult.error(() -> original.error().get().message());
                }
                if (original.result().isPresent() && cowType.isBound() && cowType.value().getClass().isAssignableFrom(original.result().get().getFirst().value().cowType().getClass())) {
                    return original.map(holderTPair -> Pair.of(Holder.direct((ConfiguredCowType<CTC, CT>) holderTPair.getFirst().value()), holderTPair.getSecond()));
                }
                return DataResult.error(() -> "Specified ConfiguredCowType '" + original.result().get().getFirst().unwrapKey().map(ResourceKey::location).orElse(null) + " + is not assignable to the cow type of '" + BovinesRegistries.COW_TYPE_REGISTRY.getKey(cowType.value()) + "'.");
            }

            @Override
            public <T> DataResult<T> encode(Holder<ConfiguredCowType<CTC, CT>> input, DynamicOps<T> ops, T prefix) {
                return CODEC.xmap(holder -> (Holder)holder, holder -> holder).encode(input, ops, prefix);
            }
        };
    }

}