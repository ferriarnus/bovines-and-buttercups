package net.merchantpug.bovinesandbuttercups.mixin;

import com.mojang.serialization.Decoder;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {
    @Inject(method = "loadContentsFromManager", at = @At("TAIL"))
    private static <E> void bovinesandbuttercups$loadMissingTypes(ResourceManager manager, RegistryOps.RegistryInfoLookup lookup, WritableRegistry<E> registry, Decoder<E> decoder, Map<ResourceKey<?>, Exception> exceptionMap, CallbackInfo ci) {
        if (registry.key() != (ResourceKey) BovinesRegistryKeys.COW_TYPE)
            return;
        for (Map.Entry<ResourceKey<CowTypeType<?>>, CowTypeType<?>> entry : BovinesRegistries.COW_TYPE_TYPE.entrySet())
            registry.register((ResourceKey<E>) ResourceKey.create(BovinesRegistryKeys.COW_TYPE, entry.getKey().location().withPath(string -> "missing_" + string)), (E) new CowType(entry.getValue(), entry.getValue().defaultConfig()), RegistrationInfo.BUILT_IN);
    }
}
