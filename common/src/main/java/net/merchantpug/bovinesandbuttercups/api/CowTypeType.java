package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

public class CowTypeType<C extends CowTypeConfiguration> {
    public static final Codec<Holder<CowTypeType<?>>> CODEC = RegistryFixedCodec.create(BovinesRegistryKeys.COW_TYPE_TYPE);

    private final MapCodec<CowType<C>> configuredCodec;
    private final ResourceKey<CowType<?>> defaultKey;
    private final C defaultConfig;

    public CowTypeType(MapCodec<C> codec,
                       ResourceKey<CowType<?>> defaultKey, C defaultConfig) {
        this.configuredCodec = RecordCodecBuilder.mapCodec(inst -> inst.group(
                codec.forGetter(CowType::configuration)
        ).apply(inst, (ctc) -> new CowType<>(this, ctc)));
        this.defaultKey = defaultKey;
        this.defaultConfig = defaultConfig;
    }

    public MapCodec<CowType<C>> cowCodec() {
        return configuredCodec;
    }

    public ResourceKey<CowType<?>> defaultKey() {
        return defaultKey;
    }

    public C defaultConfig() {
        return defaultConfig;
    }

}