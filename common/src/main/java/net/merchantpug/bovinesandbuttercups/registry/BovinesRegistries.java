package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesPlatformHelper;
import net.merchantpug.bovinesandbuttercups.util.ReloadableRegistryUtil;
import net.minecraft.core.Registry;

public class BovinesRegistries {

    public static final Registry<CowType<?>> COW_TYPE_REGISTRY = IBovinesPlatformHelper.INSTANCE.createRegistry(BovinesRegistryKeys.COW_TYPE_KEY);
    public static final Registry<ConfiguredCowType<?, ?>> CONFIGURED_COW_TYPE_REGISTRY = ReloadableRegistryUtil.createReloadableRegistry(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, ConfiguredCowType.CODEC);

    public static void init() {
    }

}
