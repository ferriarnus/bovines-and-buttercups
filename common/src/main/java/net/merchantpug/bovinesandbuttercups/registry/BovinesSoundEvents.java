package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class BovinesSoundEvents {
    public static final SoundEvent MOOBLOOM_EAT = SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.eat"));
    public static final SoundEvent MOOBLOOM_MILK = SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.milk"));
    public static final SoundEvent MOOBLOOM_SHEAR = SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.shear"));
    public static final SoundEvent MOOBLOOM_CONVERT = SoundEvent.createVariableRangeEvent(BovinesAndButtercups.asResource("entity.moobloom.convert"));

    public static void registerAll(RegistrationCallback<SoundEvent> callback) {
        callback.register(BuiltInRegistries.SOUND_EVENT, BovinesAndButtercups.asResource("entity.moobloom.eat"), MOOBLOOM_EAT);
        callback.register(BuiltInRegistries.SOUND_EVENT, BovinesAndButtercups.asResource("entity.moobloom.milk"), MOOBLOOM_MILK);
        callback.register(BuiltInRegistries.SOUND_EVENT, BovinesAndButtercups.asResource("entity.moobloom.shear"), MOOBLOOM_SHEAR);
        callback.register(BuiltInRegistries.SOUND_EVENT, BovinesAndButtercups.asResource("entity.moobloom.convert"), MOOBLOOM_CONVERT);
    }
}
