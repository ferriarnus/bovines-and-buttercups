package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;

import java.util.ServiceLoader;

public class ServiceUtil {
    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BovinesAndButtercups.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}