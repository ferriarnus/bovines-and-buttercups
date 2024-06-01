package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.MutationTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

public class BovinesCriteriaTriggers {

    public static void registerAll(RegistrationCallback<CriterionTrigger<?>> callback) {
        callback.register(BuiltInRegistries.TRIGGER_TYPES, LockEffectTrigger.ID, LockEffectTrigger.INSTANCE);
        callback.register(BuiltInRegistries.TRIGGER_TYPES, MutationTrigger.ID, MutationTrigger.INSTANCE);
        callback.register(BuiltInRegistries.TRIGGER_TYPES, PreventEffectTrigger.ID, PreventEffectTrigger.INSTANCE);
    }
}
