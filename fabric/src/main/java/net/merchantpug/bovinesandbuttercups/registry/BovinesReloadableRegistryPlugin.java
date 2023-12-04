package net.merchantpug.bovinesandbuttercups.registry;

import dev.greenhouseteam.rdpr.api.IReloadableRegistryCreationHelper;
import dev.greenhouseteam.rdpr.api.entrypoint.ReloadableRegistryPlugin;
import net.merchantpug.bovinesandbuttercups.util.ReloadableRegistryUtil;

public class BovinesReloadableRegistryPlugin implements ReloadableRegistryPlugin {
    @Override
    public void createContents(IReloadableRegistryCreationHelper helper) {
        ReloadableRegistryUtil.makeDataPackRegistriesReloadable(helper);
    }
}
