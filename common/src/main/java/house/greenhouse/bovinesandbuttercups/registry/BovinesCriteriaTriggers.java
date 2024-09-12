package house.greenhouse.bovinesandbuttercups.registry;

import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.BreedCowWithTypeTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.MutationTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

public class BovinesCriteriaTriggers {

    public static void registerAll(RegistrationCallback<CriterionTrigger<?>> callback) {
        callback.register(BuiltInRegistries.TRIGGER_TYPES, BreedCowWithTypeTrigger.ID, BreedCowWithTypeTrigger.INSTANCE);
        callback.register(BuiltInRegistries.TRIGGER_TYPES, LockEffectTrigger.ID, LockEffectTrigger.INSTANCE);
        callback.register(BuiltInRegistries.TRIGGER_TYPES, MutationTrigger.ID, MutationTrigger.INSTANCE);
        callback.register(BuiltInRegistries.TRIGGER_TYPES, PreventEffectTrigger.ID, PreventEffectTrigger.INSTANCE);
    }
}
