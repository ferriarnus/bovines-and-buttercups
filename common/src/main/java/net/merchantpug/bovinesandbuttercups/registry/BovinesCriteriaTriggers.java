package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.MutationTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class BovinesCriteriaTriggers {
    public static final LockEffectTrigger LOCK_EFFECT = CriteriaTriggers.register(LockEffectTrigger.ID.toString(), new LockEffectTrigger());
    public static final PreventEffectTrigger PREVENT_EFFECT = CriteriaTriggers.register(PreventEffectTrigger.ID.toString(), new PreventEffectTrigger());
    public static final MutationTrigger MUTATION = CriteriaTriggers.register(MutationTrigger.ID.toString(), new MutationTrigger());

    public static void registerAll() {

    }
}
