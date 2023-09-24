package net.merchantpug.bovinesandbuttercups.registry.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
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
import java.util.function.BiFunction;

/**
 * The base class for PreparableReloadListeners for {@link ReloadableMappedRegistry}.
 *
 * @param <T> The type of the registry.
 */
public class ReloadableRegistryReloadListener<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>> {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final ResourceKey<? extends Registry<T>> registryKey;
    private final ResourceLocation baseDirectory;
    private final BiFunction<ResourceLocation, JsonElement, T> jsonToValue;
    private final RegistryAccess access;

    public ReloadableRegistryReloadListener(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation baseDirectory, Codec<T> codec, RegistryAccess access) {
        this.registryKey = registryKey;
        this.baseDirectory = baseDirectory;
        this.jsonToValue = (resourceLocation, jsonElement) -> codec.parse(JsonOps.INSTANCE, jsonElement).resultOrPartial(BovinesAndButtercups.LOG::error).orElseThrow(RuntimeException::new);
        this.access = access;
    }

    public ReloadableRegistryReloadListener(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation baseDirectory, BiFunction<ResourceLocation, JsonElement, T> jsonToValue, RegistryAccess access) {
        this.registryKey = registryKey;
        this.baseDirectory = baseDirectory;
        this.jsonToValue = jsonToValue;
        this.access = access;
    }

    public static ReloadableRegistryReloadListener<ConfiguredCowType<?, ?>> createCowTypeReloadListener(RegistryAccess access) {
        return new ReloadableRegistryReloadListener<>(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, BovinesAndButtercups.asResource("configured_cow_type"), (resourceLocation, jsonElement) -> {
            StringBuilder stringBuilder = new StringBuilder(resourceLocation.getPath());
            String[] split = resourceLocation.getPath().split("/");
            stringBuilder.delete(resourceLocation.getPath().length() - split[split.length - 1].length(), resourceLocation.getPath().length());
            stringBuilder.delete(0, "bovinesandbuttercups/configured_cow_type".length());
            String potentialName = stringBuilder.toString();

            if (CowType.getFromName(potentialName).isPresent()) {
                return CowType.getFromName(potentialName).get().parse(JsonOps.INSTANCE, jsonElement).resultOrPartial(BovinesAndButtercups.LOG::error).orElseThrow(RuntimeException::new);
            }

            return ConfiguredCowType.CODEC.parse(JsonOps.INSTANCE, jsonElement).resultOrPartial(BovinesAndButtercups.LOG::error).orElseThrow(RuntimeException::new);
        }, access);
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
                    T apply = jsonToValue.apply(fileToId, jsonElement);
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

    @Override
    protected void apply(Map<ResourceLocation, T> map, ResourceManager manager, ProfilerFiller applyFiller) {
        if (map.isEmpty()) return;
        if (access.registry(registryKey).isEmpty()){
            throw new RuntimeException("Could not find registry that matches key: '" + registryKey.location() + "'");
        }
        if (!(access.registry(registryKey).get() instanceof ReloadableMappedRegistry<T> reloadable)) {
            throw new RuntimeException("Registry '" + registryKey.location() + "' is not a ReloadableMappedRegistry.");
        }
        reloadable.unfreezeAndEmpty();
        map.forEach((resourceLocation, t) -> reloadable.register(ResourceKey.create(registryKey, resourceLocation), t, Lifecycle.stable()));
        reloadable.freeze();
    }
}
