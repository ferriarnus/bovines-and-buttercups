package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BovinesAndButtercups {
    public static final String MOD_ID = "bovinesandbuttercups";
    public static final String MOD_NAME = "Bovines and Buttercups";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    private static MinecraftServer server;
    private static boolean hasServerStarted = false;

    public static void init() {
        BovinesEntityTypes.registerAll();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static RegistryAccess.Frozen registryAccess() {
        if (server == null) {
            return null;
        }
        return server.registryAccess();
    }

    public static void setServer(MinecraftServer server) {
        if (BovinesAndButtercups.server != null) {
            BovinesAndButtercups.LOG.error("Could not set server as it was already set.");
            return;
        }
        BovinesAndButtercups.server = server;
    }

    public static boolean hasServerStarted() {
        return BovinesAndButtercups.hasServerStarted;
    }

    public static void setServerStarted(boolean value) {
        BovinesAndButtercups.hasServerStarted = value;
    }
}