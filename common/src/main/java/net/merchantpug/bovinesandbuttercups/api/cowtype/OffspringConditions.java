package net.merchantpug.bovinesandbuttercups.api.cowtype;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.List;

public record OffspringConditions(List<LootItemCondition> thisConditions, List<LootItemCondition> otherConditions) {
    public static final Codec<OffspringConditions> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            LootItemConditions.DIRECT_CODEC.listOf().optionalFieldOf("this_conditions", List.of()).forGetter(OffspringConditions::thisConditions),
            LootItemConditions.DIRECT_CODEC.listOf().optionalFieldOf("other_conditions", List.of()).forGetter(OffspringConditions::otherConditions)
    ).apply(inst, OffspringConditions::new));
    public static final OffspringConditions EMPTY = new OffspringConditions(List.of(), List.of());
}