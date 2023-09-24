package net.merchantpug.bovinesandbuttercups.mixin;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(MappedRegistry.class)
public interface MappedRegistryAccessor<T> {
    @Accessor("nextId")
    void bovinesandbuttercups$setNextId(int value);

    @Accessor("byId")
    ObjectList<Holder.Reference<T>> bovinesandbuttercups$getById();

    @Accessor("toId")
    Object2IntMap<T> bovinesandbuttercups$getToId();

    @Accessor("byLocation")
    Map<ResourceLocation, Holder.Reference<T>> bovinesandbuttercups$getByLocation();

    @Accessor("byKey")
    Map<ResourceKey<T>, Holder.Reference<T>> bovinesandbuttercups$getByKey();

    @Accessor("byValue")
    Map<T, Holder.Reference<T>> bovinesandbuttercups$getByValue();

    @Accessor("lifecycles")
    Map<T, Lifecycle> bovinesandbuttercups$getLifecycles();

    @Accessor("registryLifecycle")
    void bovinesandbuttercups$setRegistryLifecycle(Lifecycle value);

    @Accessor("holdersInOrder")
    void bovinesandbuttercups$SetHoldersInOrder(List<Holder.Reference<T>> value);

    @Accessor("frozen")
    void bovinesandbuttercups$setFrozen(boolean value);
}
