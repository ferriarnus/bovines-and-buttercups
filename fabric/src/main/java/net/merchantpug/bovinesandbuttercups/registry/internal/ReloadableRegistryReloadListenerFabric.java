package net.merchantpug.bovinesandbuttercups.registry.internal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class ReloadableRegistryReloadListenerFabric<T> extends ReloadableRegistryReloadListener<T> implements IdentifiableResourceReloadListener {

    public ReloadableRegistryReloadListenerFabric(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation baseDirectory, Codec<T> codec, RegistryAccess access) {
        super(registryKey, baseDirectory, codec, access);
    }

    @Override
    protected void apply(Map<ResourceLocation, T> map, ResourceManager manager, ProfilerFiller applyFiller) {
        if (map.isEmpty()) return;
        if (access.registry(registryKey).isEmpty()) {
            throw new RuntimeException("Could not find registry that matches key: '" + registryKey.location() + "'");
        }
        if (!(access.registry(registryKey).get() instanceof ReloadableMappedRegistry<T> reloadable)) {
            throw new RuntimeException("Registry '" + registryKey.location() + "' is not a ReloadableMappedRegistry.");
        }
        reloadable.unfreezeAndEmpty();
        map.forEach((resourceLocation, t) -> reloadable.register(ResourceKey.create(registryKey, resourceLocation), t, Lifecycle.stable()));
        reloadable.freeze();
    }

    @Override
    public ResourceLocation getFabricId() {
        return baseDirectory;
    }
}
