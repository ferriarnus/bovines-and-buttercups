package house.greenhouse.bovinesandbuttercups.registry;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.worldgen.RanchStructure;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class BovinesStructureTypes {
    public static final StructureType<RanchStructure> RANCH = () -> RanchStructure.CODEC;

    public static void registerAll(RegistrationCallback<StructureType<?>> callback) {
        callback.register(BuiltInRegistries.STRUCTURE_TYPE, BovinesAndButtercups.asResource("ranch"), RANCH);
    }
}
