package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

public record ConfiguredCowType<CTC extends CowTypeConfiguration, CT extends CowType<CTC>>(CT cowType, CTC configuration) {

    public static final Codec<ConfiguredCowType<?, ?>> DIRECT_CODEC = CowType.CODEC.dispatch(ConfiguredCowType::cowType, CowType::codec);

    public static final Codec<Holder<ConfiguredCowType<?, ?>>> CODEC = RegistryFileCodec.create(BovinesRegistryKeys.CONFIGURED_COW_TYPE, DIRECT_CODEC);

}