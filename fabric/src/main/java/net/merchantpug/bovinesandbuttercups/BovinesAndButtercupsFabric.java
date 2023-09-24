package net.merchantpug.bovinesandbuttercups;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableRegistryReloadListener;
import net.minecraft.server.packs.PackType;

public class BovinesAndButtercupsFabric implements ModInitializer {


    @Override
    public void onInitialize() {
        BovinesRegistries.init();
        BovinesAndButtercups.init();

        ServerLifecycleEvents.SERVER_STARTING.register(BovinesAndButtercups::setServer);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> BovinesAndButtercups.setServerStarted(true));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> BovinesAndButtercups.setServerStarted(false));

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener((IdentifiableResourceReloadListener) ReloadableRegistryReloadListener.createCowTypeReloadListener(BovinesAndButtercups.registryAccess()));
    }

}
