package house.greenhouse.bovinesandbuttercups.api.cowtype;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.function.Function;

public record OffspringConditions(List<LootItemCondition> thisConditions, List<LootItemCondition> otherConditions) {
    public static final Codec<OffspringConditions> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("this_conditions", List.of()).forGetter(OffspringConditions::thisConditions),
            LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("other_conditions", List.of()).forGetter(OffspringConditions::otherConditions)
    ).apply(inst, OffspringConditions::new));
    public static final Codec<OffspringConditions> CODEC = Codec.either(LootItemCondition.DIRECT_CODEC.listOf(), DIRECT_CODEC).flatComapMap(either -> either.map(lootItemConditions -> new OffspringConditions(lootItemConditions, List.of()), Function.identity()), conditions -> {
        if (conditions.otherConditions.isEmpty())
            return DataResult.success(Either.left(conditions.thisConditions));
        return DataResult.success(Either.right(conditions));
    });

    public static final OffspringConditions EMPTY = new OffspringConditions(List.of(), List.of());
}