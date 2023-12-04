package net.merchantpug.bovinesandbuttercups.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Lifecycle;
import dev.greenhouseteam.rdpr.api.IReloadableRegistryCreationHelper;
import dev.greenhouseteam.rdpr.api.loader.CustomDataLoader;
import dev.greenhouseteam.rdpr.api.platform.IRDPRApiPlatformHelper;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.PrioritizedRecord;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReloadableRegistryUtil {
    public static void makeDataPackRegistriesReloadable(IReloadableRegistryCreationHelper helper) {
        helper.fromExistingRegistry(BovinesResourceKeys.CONFIGURED_COW_TYPE);
        helper.setCustomDataLoader(BovinesResourceKeys.CONFIGURED_COW_TYPE, prioritisedWithBootstrap(BovinesCowTypes.bootstrap()));
        helper.fromExistingRegistry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE);
        helper.setCustomDataLoader(BovinesResourceKeys.CUSTOM_FLOWER_TYPE, prioritisedWithBootstrap(CustomFlowerType.bootstrap()));
        helper.fromExistingRegistry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE);
        helper.setCustomDataLoader(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE, prioritisedWithBootstrap(CustomMushroomType.bootstrap()));
    }

    @SuppressWarnings("UnstableApiUsage")
    private static <T> CustomDataLoader<T> prioritisedWithBootstrap(RegistrySetBuilder.RegistryBootstrap<T> registryBootstrap) {
        return (lookup, resourceManager, registry, codec, exceptionMap) -> {
            Map<ResourceLocation, Integer> priorityList = new HashMap<>();
            FileToIdConverter fileToIdConverter = FileToIdConverter.json(registry.key().location().getNamespace() + "/" + registry.key().location().getPath());
            RegistryOps<JsonElement> ops = IRDPRApiPlatformHelper.INSTANCE.getRegistryOps(lookup);
            Decoder<Optional<PrioritizedRecord<T>>> decoder = IRDPRApiPlatformHelper.INSTANCE.getDecoder(PrioritizedRecord.createCodec(codec));

            registryBootstrap.run(new BootstapContext<>() {
                @Override
                public @NotNull Holder.Reference<T> register(@NotNull ResourceKey<T> resourceKey, @NotNull T configuredCowType, @NotNull Lifecycle lifecycle) {
                    // As the bootstrap should only ever be running the missing values, we don't want them replaced.
                    priorityList.put(resourceKey.location(), Integer.MAX_VALUE);
                    return registry.register(resourceKey, configuredCowType, lifecycle);
                }

                @Override
                public <S> @NotNull HolderGetter<S> lookup(@NotNull ResourceKey<? extends Registry<? extends S>> resourceKey) {
                    return lookup.lookup(resourceKey).orElseThrow().getter();
                }
            });

            for (Map.Entry<ResourceLocation, Resource> entry : fileToIdConverter.listMatchingResources(resourceManager).entrySet()) {
                Resource resource = entry.getValue();
                ResourceKey<T> resourceKey = ResourceKey.create(registry.key(), fileToIdConverter.fileToId(entry.getKey()));
                try (Reader reader = resource.openAsReader()) {
                    JsonElement jsonelement = JsonParser.parseReader(reader);
                    DataResult<Optional<PrioritizedRecord<T>>> dataResult = decoder.parse(ops, jsonelement);
                    Optional<PrioritizedRecord<T>> t = dataResult.getOrThrow(false, s -> {});
                    t.ifPresentOrElse(e -> {
                        if (e.priority() > priorityList.getOrDefault(resourceKey.location(), Integer.MIN_VALUE)) {
                            registry.register(resourceKey, e.record(), Lifecycle.stable());
                            int newPriority = e.priority();
                            priorityList.put(resourceKey.location(), newPriority);
                        }
                    }, () -> LogUtils.getLogger().debug("Skipping loading registry entry {} as it's conditions were not met", resource));
                } catch (Exception ex) {
                    exceptionMap.put(resourceKey, ex);
                }
            }
        };
    }

}
