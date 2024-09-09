package house.greenhouse.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.MutationTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.worldgen.OffsetPoolElement;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class BovinesStructurePoolElements {

    public static void registerAll(RegistrationCallback<StructurePoolElementType<?>> callback) {
        callback.register(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, OffsetPoolElement.ID, createStructurePoolElementType(OffsetPoolElement.CODEC));
    }

    private static <T extends StructurePoolElement> StructurePoolElementType<T> createStructurePoolElementType(MapCodec<T> mapCodec) {
        return () -> mapCodec;
    }
}
