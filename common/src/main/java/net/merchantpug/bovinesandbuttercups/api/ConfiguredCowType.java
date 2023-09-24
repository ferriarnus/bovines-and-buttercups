package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;

public record ConfiguredCowType<CTC extends CowTypeConfiguration, CT extends CowType<CTC>>(CT cowType, CTC configuration) {

    public static final Codec<ConfiguredCowType<?, ?>> CODEC = CowType.CODEC.dispatch(ConfiguredCowType::cowType, CowType::codec);

}