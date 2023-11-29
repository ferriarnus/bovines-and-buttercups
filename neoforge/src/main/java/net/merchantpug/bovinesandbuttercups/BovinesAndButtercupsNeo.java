package net.merchantpug.bovinesandbuttercups;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsNeo {
    
    public BovinesAndButtercupsNeo(IEventBus eventBus) {
        BovinesAndButtercups.init();
    }

}