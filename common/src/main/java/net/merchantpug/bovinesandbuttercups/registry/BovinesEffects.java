package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesRegistryHelper;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class BovinesEffects {
    private static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, BovinesAndButtercups.MOD_ID);

    public static final Holder<MobEffect> LOCKDOWN = register("lockdown", IBovinesRegistryHelper.INSTANCE::createLockdownEffect);

    public static void register() {

    }

    private static Holder<MobEffect> register(String name, Supplier<MobEffect> item) {
        return MOB_EFFECTS.register(name, item);
    }
}
