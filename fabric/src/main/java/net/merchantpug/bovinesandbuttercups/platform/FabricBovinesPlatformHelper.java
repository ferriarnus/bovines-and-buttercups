package net.merchantpug.bovinesandbuttercups.platform;

import com.google.auto.service.AutoService;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.Map;

@AutoService(IBovinesPlatformHelper.class)
public class FabricBovinesPlatformHelper implements IBovinesPlatformHelper {

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
    public <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        return FabricRegistryBuilder.createSimple(registryKey).buildAndRegister();
    }

    @Override
    public Map<Block, Block> getPottedBlockMap() {
        return FlowerPotBlock.POTTED_BY_CONTENT;
    }

}
