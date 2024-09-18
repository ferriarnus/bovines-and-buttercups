package house.greenhouse.bovinesandbuttercups.content.data.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.NoOpTextureModifier;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.network.clientbound.SyncConditionedTextureModifier;
import house.greenhouse.bovinesandbuttercups.network.clientbound.SyncCowTypeClientboundPacket;
import house.greenhouse.bovinesandbuttercups.registry.BovinesLootContextParamSets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;

public class FallbackModifierFactory extends TextureModifierFactory<NoOpTextureModifier> {
    public static final MapCodec<FallbackModifierFactory> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(FallbackModifierFactory::conditions)
    ).apply(inst, FallbackModifierFactory::new));

    private final List<ResourceLocation> conditions;

    public FallbackModifierFactory(List<ResourceLocation> conditions) {
        this.conditions = conditions;
    }

    public List<ResourceLocation> conditions() {
        return conditions;
    }

    @Override
    protected NoOpTextureModifier createProvider() {
        return new NoOpTextureModifier();
    }

    @Override
    public boolean canDisplay(Entity entity) {
        if (conditions.isEmpty())
            return ConditionedModifierFactory.isConditionalDisplaying(entity);
        return conditions.stream().allMatch(condition -> ConditionedModifierFactory.shouldDisplayConditional(entity, condition));
    }

    @Override
    public MapCodec<? extends TextureModifierFactory<?>> codec() {
        return CODEC;
    }

}
