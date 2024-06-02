package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.platform.BovinesPlatformHelper;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BovinesAndButtercups {
    public static final String MOD_ID = "bovinesandbuttercups";
    public static final String MOD_NAME = "Bovines and Buttercups";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    private static BovinesPlatformHelper helper;

    public static void init(BovinesPlatformHelper helper) {
        BovinesAndButtercups.helper = helper;
    }

    public static BovinesPlatformHelper getHelper() {
        return helper;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}