package net.merchantpug.bovinesandbuttercups.registry.internal;

import com.mojang.serialization.Lifecycle;
import net.merchantpug.bovinesandbuttercups.mixin.MappedRegistryAccessor;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ReloadableMappedRegistry<T> extends MappedRegistry<T> {
    private Lifecycle defaultLifecycle;

    public ReloadableMappedRegistry(ResourceKey<? extends Registry<T>> resourceKey, Lifecycle lifecycle) {
        super(resourceKey, lifecycle);
        this.defaultLifecycle = lifecycle;
    }

    /**
     * Unfreezes and empties the registry.
     */
    public void unfreezeAndEmpty() {
        ((MappedRegistryAccessor)this).bovinesandbuttercups$setFrozen(false);
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$setNextId(0);

        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getByKey().clear();
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getByLocation().clear();
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getByValue().clear();
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getById().size(0);
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getToId().clear();

        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$getLifecycles().clear();
        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$setRegistryLifecycle(defaultLifecycle);

        ((MappedRegistryAccessor<T>)this).bovinesandbuttercups$SetHoldersInOrder(null);
    }

}
