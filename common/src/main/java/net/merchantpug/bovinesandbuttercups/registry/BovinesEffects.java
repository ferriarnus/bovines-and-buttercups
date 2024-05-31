package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.registry.internal.HolderRegistrationCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class BovinesEffects {
    public static Holder<MobEffect> LOCKDOWN;

    public static void registerAll(HolderRegistrationCallback<MobEffect> callback) {
        LOCKDOWN = callback.register(BuiltInRegistries.MOB_EFFECT, BovinesAndButtercups.asResource("lockdown"), BovinesAndButtercups.getHelper().getRegistryHelper().createLockdownEffect());
    }
}
