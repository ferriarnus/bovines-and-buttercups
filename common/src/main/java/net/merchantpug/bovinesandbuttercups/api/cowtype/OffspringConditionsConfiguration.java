package net.merchantpug.bovinesandbuttercups.api.cowtype;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.List;

public record OffspringConditionsConfiguration(List<LootItemCondition> thisConditions, List<LootItemCondition> otherConditions) {
    public static final Codec<OffspringConditionsConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            LootItemConditions.DIRECT_CODEC.listOf().optionalFieldOf("this_conditions", List.of()).forGetter(OffspringConditionsConfiguration::thisConditions),
            LootItemConditions.DIRECT_CODEC.listOf().optionalFieldOf("other_conditions", List.of()).forGetter(OffspringConditionsConfiguration::otherConditions)
    ).apply(inst, OffspringConditionsConfiguration::new));
    public static final OffspringConditionsConfiguration EMPTY = new OffspringConditionsConfiguration(List.of(), List.of());
}