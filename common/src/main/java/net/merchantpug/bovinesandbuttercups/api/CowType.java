package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;

public record CowType<C extends CowTypeConfiguration>(CowTypeType<C> type, C configuration) {
    public static final Codec<CowType<?>> DIRECT_CODEC = BovinesRegistries.COW_TYPE_TYPE.byNameCodec().dispatch(CowType::type, CowTypeType::cowCodec);
    public static final Codec<Holder<CowType<?>>> CODEC = RegistryFileCodec.create(BovinesRegistryKeys.COW_TYPE, DIRECT_CODEC);

    public static <C extends CowTypeConfiguration, T extends CowTypeType<C>> Codec<Holder<CowType<C>>> filteredCodec(T cowType) {
        return new Codec<>() {
            @Override
            public <TOps> DataResult<Pair<Holder<CowType<C>>, TOps>> decode(DynamicOps<TOps> ops, TOps input) {
                if (ops instanceof RegistryOps<TOps>) {
                    var original = CowType.CODEC.decode(ops, input);
                    if (original.error().isPresent()) {
                        return DataResult.error(() -> original.error().get().message());
                    }
                    var result = original.result().get().getFirst();
                    if (original.result().isPresent() && cowType.equals(result.value().type())) {
                        return original.map(holderTPair -> Pair.of((Holder)holderTPair.getFirst(), holderTPair.getSecond()));
                    }
                    return DataResult.error(() -> "Specified ConfiguredCowType '" + original.result().get().getFirst().unwrapKey().map(ResourceKey::location).orElse(null) + " + is not assignable to the cow type of '" + BovinesRegistries.COW_TYPE_TYPE.getKey(cowType) + "'.");
                }
                return DataResult.error(() -> "Filtered Cow Type codec is not supported by non RegistryOps ops.");
            }

            @Override
            public <TOps> DataResult<TOps> encode(Holder<CowType<C>> input, DynamicOps<TOps> ops, TOps prefix) {
                return CowType.CODEC.xmap(holder -> (Holder)holder, holder -> holder).encode(input, ops, prefix);
            }
        };
    }

}
