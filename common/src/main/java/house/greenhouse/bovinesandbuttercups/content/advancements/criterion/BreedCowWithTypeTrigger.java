package house.greenhouse.bovinesandbuttercups.content.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.CowType;
import house.greenhouse.bovinesandbuttercups.api.CowTypeType;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class BreedCowWithTypeTrigger extends SimpleCriterionTrigger<BreedCowWithTypeTrigger.TriggerInstance> {
    public static final BreedCowWithTypeTrigger INSTANCE = new BreedCowWithTypeTrigger();
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("breed_cow_with_type");
    public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CowTypeType.CODEC.optionalFieldOf("cow_type_type").forGetter(TriggerInstance::typeType),
            RegistryCodecs.homogeneousList(BovinesRegistryKeys.COW_TYPE).optionalFieldOf("cow_types", HolderSet.direct()).forGetter(TriggerInstance::types),
            ContextAwarePredicate.CODEC.optionalFieldOf("parent").forGetter(TriggerInstance::parent),
            ContextAwarePredicate.CODEC.optionalFieldOf("partner").forGetter(TriggerInstance::partner),
            ContextAwarePredicate.CODEC.optionalFieldOf("child").forGetter(TriggerInstance::child)
    ).apply(inst, TriggerInstance::new));

    private BreedCowWithTypeTrigger() {}

    public void trigger(ServerPlayer serverPlayer, Animal parent, Animal partner, AgeableMob child, Holder<CowType<?>> moobloomType) {
        LootContext parentContext = EntityPredicate.createContext(serverPlayer, parent);
        LootContext partnerContext = EntityPredicate.createContext(serverPlayer, partner);
        LootContext childContext = child != null ? EntityPredicate.createContext(serverPlayer, child) : null;
        trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(parentContext, partnerContext, childContext, moobloomType));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return CODEC;
    }

    public record TriggerInstance(Optional<Holder<CowTypeType<?>>> typeType,
                                  HolderSet<CowType<?>> types,
                                  Optional<ContextAwarePredicate> parent,
                                  Optional<ContextAwarePredicate> partner,
                                  Optional<ContextAwarePredicate> child) implements SimpleInstance {

        public boolean matches(LootContext parentContext, LootContext partnerContext, LootContext childContext, Holder<CowType<?>> type) {
            return type.isBound() && (typeType.isEmpty() && types.size() == 0 || typeType.isPresent() && typeType.get() == type.value().type() && types.contains(type)) && (this.child.isEmpty() || this.child.get().matches(childContext)) && (this.parent.isEmpty() || this.parent.get().matches(parentContext)) && (this.partner.isEmpty() || this.partner.get().matches(partnerContext)) || (this.parent.isEmpty() || this.parent.get().matches(partnerContext)) && (this.partner.isEmpty() || this.partner.get().matches(parentContext));
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}