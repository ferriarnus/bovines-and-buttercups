package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsForge {
    
    public BovinesAndButtercupsForge(IEventBus eventBus) {
        BovinesRegistries.init(eventBus);
        BovinesAndButtercups.init();
    }

}