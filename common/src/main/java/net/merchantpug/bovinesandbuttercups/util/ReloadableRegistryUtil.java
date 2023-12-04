package net.merchantpug.bovinesandbuttercups.util;

import dev.greenhouseteam.rdpr.api.IReloadableRegistryCreationHelper;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;

public class ReloadableRegistryUtil {
    public static void makeDataPackRegistriesReloadable(IReloadableRegistryCreationHelper helper) {
        helper.fromExistingRegistry(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY);
    }
}
