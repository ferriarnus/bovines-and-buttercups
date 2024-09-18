package house.greenhouse.bovinesandbuttercups.content.data.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.NoOpTextureModifier;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

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
            return !ConditionedModifierFactory.isConditionalDisplaying(entity);
        return conditions.stream().noneMatch(condition -> ConditionedModifierFactory.shouldDisplayConditional(entity, condition));
    }

    @Override
    public MapCodec<? extends TextureModifierFactory<?>> codec() {
        return CODEC;
    }

}
