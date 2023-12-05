package net.merchantpug.bovinesandbuttercups.platform.services;

import net.merchantpug.bovinesandbuttercups.platform.ServiceUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public interface IBovinesPlatformHelper {
    IBovinesPlatformHelper INSTANCE = ServiceUtil.load(IBovinesPlatformHelper.class);

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Creates a new registry.
     *
     * @return The created registry.
     */
    <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey);

    /**
     * Gets the potted block map.
     *
     * @return The potted block map.
     */
    Map<Block, Block> getPottedBlockMap();

}