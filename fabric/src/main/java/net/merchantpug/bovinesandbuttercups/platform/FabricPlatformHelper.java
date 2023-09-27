package net.merchantpug.bovinesandbuttercups.platform;

import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.minecraft.resources.ResourceKey;

import java.util.Map;
import java.util.Set;

@AutoService(IPlatformHelper.class)
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Codec<CowType<?>> getCowTypeCodec() {
        return BovinesRegistries.COW_TYPE_REGISTRY.byNameCodec();
    }

    @Override
    public Set<Map.Entry<ResourceKey<CowType<?>>, CowType<?>>> ctEntrySet() {
        return BovinesRegistries.COW_TYPE_REGISTRY.entrySet();
    }
}
