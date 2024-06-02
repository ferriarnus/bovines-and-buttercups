package net.merchantpug.bovinesandbuttercups.client;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.platform.BovinesClientHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.stream.Stream;

public class BovinesAndButtercupsClient {
    public static final HashSet<ResourceLocation> LOADED_COW_TEXTURES = new HashSet<>();
    private static final HashSet<String> COW_TEXTURE_PATHS = new HashSet<>();
    private static BovinesClientHelper clientHelper;

    public static void init(BovinesClientHelper helper) {
        clientHelper = helper;
    }

    public static void registerCowTexturePaths() {
        registerCowTexturePath("textures/entity/cow");
        registerCowTexturePath("textures/entity/bovinesandbuttercups");
    }

    public static BovinesClientHelper getHelper() {
        return clientHelper;
    }

    public static Stream<String> cowTexturePathsAsStream() {
        return COW_TEXTURE_PATHS.stream();
    }

    public static void registerCowTexturePath(String path) {
        if (!COW_TEXTURE_PATHS.contains(path))
            COW_TEXTURE_PATHS.add(path);
        else
            BovinesAndButtercups.LOG.warn("Tried registering cow texture path '{}' more than once. (Skipping).", path);
    }
}
