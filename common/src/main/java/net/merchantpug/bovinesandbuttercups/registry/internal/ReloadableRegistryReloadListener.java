package net.merchantpug.bovinesandbuttercups.registry.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The base class for PreparableReloadListeners for each loader's
 * respective reloadable registry.
 *
 * @param <T> The type of the registry.
 */
public abstract class ReloadableRegistryReloadListener<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>> {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    protected final ResourceKey<? extends Registry<T>> registryKey;
    protected final ResourceLocation baseDirectory;
    protected final Codec<T> codec;
    protected final RegistryAccess access;

    public ReloadableRegistryReloadListener(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation baseDirectory, Codec<T> codec, RegistryAccess access) {
        this.registryKey = registryKey;
        this.baseDirectory = baseDirectory;
        this.codec = codec;
        this.access = access;
    }

    @Override
    protected Map<ResourceLocation, T> prepare(ResourceManager manager, ProfilerFiller prepareFiller) {
        if (!BovinesAndButtercups.hasServerStarted()) {
            return Map.of();
        }

        FileToIdConverter fileToIdConverter = FileToIdConverter.json(baseDirectory.getNamespace() + "/" + baseDirectory.getPath());
        Iterator<Map.Entry<ResourceLocation, Resource>> iterator = fileToIdConverter.listMatchingResources(manager).entrySet().iterator();

        Map<ResourceLocation, T> contentMap = new HashMap<>();

        while (iterator.hasNext()) {
            var value = iterator.next();
            ResourceLocation key = value.getKey();
            ResourceLocation fileToId = fileToIdConverter.fileToId(key);

            try {
                Reader reader = value.getValue().openAsReader();

                try {
                    JsonElement jsonElement = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                    T apply = codec.parse(JsonOps.INSTANCE, jsonElement).resultOrPartial(BovinesAndButtercups.LOG::error).orElseThrow(RuntimeException::new);
                    contentMap.put(fileToId, apply);
                } catch (Throwable throwable) {
                    try {
                        reader.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                }
                reader.close();
            } catch (IllegalArgumentException | IOException | JsonParseException ex) {
                BovinesAndButtercups.LOG.error("Couldn't parse data file {} from {}", fileToId, key, ex);
            }
        }

        return contentMap;
    }

}
