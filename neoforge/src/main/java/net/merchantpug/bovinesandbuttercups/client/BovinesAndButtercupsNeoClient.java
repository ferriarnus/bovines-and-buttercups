package net.merchantpug.bovinesandbuttercups.client;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.platform.BovinesClientHelperNeo;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsNeoClient {
    public BovinesAndButtercupsNeoClient(IEventBus eventBus) {
        BovinesAndButtercupsClient.init(new BovinesClientHelperNeo());
    }
}
