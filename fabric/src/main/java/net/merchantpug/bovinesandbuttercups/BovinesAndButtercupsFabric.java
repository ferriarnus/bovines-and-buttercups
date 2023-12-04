package net.merchantpug.bovinesandbuttercups;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;

public class BovinesAndButtercupsFabric implements ModInitializer {


    @Override
    public void onInitialize() {
        BovinesRegistries.init();
        BovinesAndButtercups.init();
    }

}
