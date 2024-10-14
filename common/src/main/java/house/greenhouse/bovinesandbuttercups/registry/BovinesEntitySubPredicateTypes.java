package house.greenhouse.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.predicate.CowSnowSubPredicate;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.BuiltInRegistries;

public class BovinesEntitySubPredicateTypes {
    public static void registerAll(RegistrationCallback<MapCodec<? extends EntitySubPredicate>> callback) {
        callback.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, BovinesAndButtercups.asResource("cow"), CowSnowSubPredicate.CODEC);
    }
}