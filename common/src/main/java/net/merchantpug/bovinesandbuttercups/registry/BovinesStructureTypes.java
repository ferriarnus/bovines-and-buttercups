package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.structure.RanchStructure;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class BovinesStructureTypes {
    public static final StructureType<RanchStructure> RANCH = () -> RanchStructure.CODEC;

    public static void registerAll(RegistrationCallback<StructureType<?>> callback) {
        callback.register(BuiltInRegistries.STRUCTURE_TYPE, BovinesAndButtercups.asResource("ranch"), RANCH);
    }
}
