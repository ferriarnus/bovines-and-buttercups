package net.merchantpug.bovinesandbuttercups.content.data.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.NoOpTextureModifier;
import net.merchantpug.bovinesandbuttercups.api.cowtype.color.TextureModifierFactory;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncConditionedTextureModifier;
import net.merchantpug.bovinesandbuttercups.registry.BovinesLootContextParamSets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;

public class ConditionedModifierFactory extends TextureModifierFactory<NoOpTextureModifier> {
    public static final MapCodec<ConditionedModifierFactory> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(f -> f.id),
            LootItemConditions.DIRECT_CODEC.listOf().fieldOf("condition").forGetter(f -> f.condition),
            Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("tick_rate", 1).forGetter(f -> f.tickRate)
    ).apply(inst, ConditionedModifierFactory::new));
    private static final WeakHashMap<UUID, Map<ResourceLocation, Boolean>> CONDITION_VALUES = new WeakHashMap<>();

    private final ResourceLocation id;
    private final List<LootItemCondition> condition;
    private final int tickRate;

    public ConditionedModifierFactory(ResourceLocation id, List<LootItemCondition> condition, int tickRate) {
        this.id = id;
        this.condition = condition;
        this.tickRate = tickRate;
    }

    public ResourceLocation getConditionId() {
        return id;
    }

    public void setConditionValue(Entity entity, boolean value) {
        CONDITION_VALUES.computeIfAbsent(entity.getUUID(), uuid -> new HashMap<>()).put(id, value);
    }

    @Override
    protected NoOpTextureModifier createProvider() {
        return new NoOpTextureModifier();
    }

    @Override
    public boolean canDisplay(Entity entity) {
        return CONDITION_VALUES.getOrDefault(entity.getUUID(), new HashMap<>()).getOrDefault(id, false);
    }

    public void tick(Entity entity) {
        if (entity.level().isClientSide() || entity.tickCount % tickRate != 0)
            return;
        LootParams.Builder params = new LootParams.Builder((ServerLevel) entity.level());
        params.withParameter(LootContextParams.THIS_ENTITY, entity);
        params.withParameter(LootContextParams.ORIGIN, entity.position());
        boolean conditionValue = condition.stream().allMatch(condition1 -> condition1.test(new LootContext.Builder(params.create(BovinesLootContextParamSets.ENTITY)).create(Optional.empty())));
        boolean oldConditionValue = canDisplay(entity);
        setConditionValue(entity, conditionValue);
        if (conditionValue != oldConditionValue)
            BovinesAndButtercups.getHelper().sendTrackingClientboundPacket(new SyncConditionedTextureModifier(entity.getId(), getConditionId(), conditionValue), entity);
    }

    @Override
    public MapCodec<? extends TextureModifierFactory<?>> codec() {
        return CODEC;
    }

}
