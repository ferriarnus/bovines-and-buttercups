package net.merchantpug.bovinesandbuttercups.mixin;

import com.mojang.serialization.Lifecycle;
import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableMappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(RegistryDataLoader.RegistryData.class)
public abstract class RegistryDataLoaderRegistryDataMixin<T> {
    @Shadow public abstract ResourceKey<? extends Registry<T>> key();

    @ModifyVariable(method = "create", at = @At(value = "STORE", ordinal = 0))
    private WritableRegistry bovinesandbuttercups$changeToReloadableMappedRegistry(WritableRegistry original, Lifecycle lifecycle, Map<ResourceKey<?>, Exception> map) {
        if (ReloadableMappedRegistry.RELOADABLE_KEYS.contains(this.key())) {
            ReloadableMappedRegistry.RELOADABLE_KEYS.remove(this.key());
            return new ReloadableMappedRegistry<>(this.key(), lifecycle);
        }
        return original;
    }
}
