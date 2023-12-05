package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class BovinesSoundEvents {
    private static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, BovinesAndButtercups.MOD_ID);

    public static final Holder<SoundEvent> MOOBLOOM_EAT = register("entity.moobloom.eat", () -> SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.eat")));
    public static final Holder<SoundEvent> MOOBLOOM_MILK = register("entity.moobloom.milk", () -> SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.milk")));
    public static final Holder<SoundEvent> MOOBLOOM_SHEAR = register("entity.moobloom.shear", () -> SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.shear")));
    public static final Holder<SoundEvent> MOOBLOOM_CONVERT = register("entity.moobloom.convert", () -> SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.convert")));

    public static void register() {

    }

    private static Holder<SoundEvent> register(String name, Supplier<SoundEvent> sound) {
        return SOUND_EVENTS.register(name, sound);
    }
}
