package net.merchantpug.bovinesandbuttercups.api.cowtypes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.williambl.dfunc.api.DFunctions;
import com.williambl.vampilang.lang.VExpression;
import com.williambl.vampilang.stdlib.StandardVTypes;

import java.util.Optional;

public record OffspringConditionsConfiguration(Optional<VExpression> predicate, Optional<VExpression> otherPredicate) {
    public OffspringConditionsConfiguration {
        if (predicate.isEmpty() && otherPredicate.isEmpty())
            throw new NullPointerException("One of 'predicate', and 'other_predicate' in OffspringConfiguration must not be empty.");
    }

    public static final Codec<OffspringConditionsConfiguration> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            DFunctions.resolvedExpressionCodec(StandardVTypes.BOOLEAN, DFunctions.ENTITY).optionalFieldOf("predicate").forGetter(OffspringConditionsConfiguration::predicate),
            DFunctions.resolvedExpressionCodec(StandardVTypes.BOOLEAN, DFunctions.ENTITY).optionalFieldOf("other_predicate").forGetter(OffspringConditionsConfiguration::otherPredicate)
    ).apply(inst, OffspringConditionsConfiguration::new));

    public static final Codec<OffspringConditionsConfiguration> CODEC = Codec.either(OffspringConditionsConfiguration.DIRECT_CODEC, DFunctions.resolvedExpressionCodec(StandardVTypes.BOOLEAN, DFunctions.ENTITY))
            .xmap(either -> either.map(offspringConfiguration -> offspringConfiguration, vExpression -> new OffspringConditionsConfiguration(Optional.of(vExpression), Optional.empty())), Either::left);
}