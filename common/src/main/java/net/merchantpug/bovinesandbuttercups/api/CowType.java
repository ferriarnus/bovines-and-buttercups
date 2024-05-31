package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;

public record CowType<C extends CowTypeConfiguration>(CowTypeType<C> type, C configuration) {
    public static final Codec<CowType<?>> DIRECT_CODEC = BovinesRegistries.COW_TYPE_TYPE.byNameCodec().dispatch(CowType::type, CowTypeType::cowCodec);
    public static final Codec<Holder<CowType<?>>> CODEC = RegistryFixedCodec.create(BovinesRegistryKeys.COW_TYPE);
}
