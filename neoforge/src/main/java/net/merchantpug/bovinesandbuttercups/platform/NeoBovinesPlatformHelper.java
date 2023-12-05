package net.merchantpug.bovinesandbuttercups.platform;

import com.google.auto.service.AutoService;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesPlatformHelper;
import net.merchantpug.bovinesandbuttercups.util.PottedBlockMapUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Map;

@AutoService(IBovinesPlatformHelper.class)
public class NeoBovinesPlatformHelper implements IBovinesPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey).create();
    }

    @Override
    public Map<Block, Block> getPottedBlockMap() {
        return PottedBlockMapUtil.getPottedContentMap();
    }

}