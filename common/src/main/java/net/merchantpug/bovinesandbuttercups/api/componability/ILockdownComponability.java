package net.merchantpug.bovinesandbuttercups.api.componability;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;

public interface ILockdownComponability {
    ResourceLocation ID = BovinesAndButtercups.asResource("lockdown");

    Map<MobEffect, Integer> getLockdownMobEffects();
    void addLockdownMobEffect(MobEffect effect, int duration);
    void removeLockdownMobEffect(MobEffect effect);
    void setLockdownMobEffects(Map<MobEffect, Integer> map);
    void sync();
}
