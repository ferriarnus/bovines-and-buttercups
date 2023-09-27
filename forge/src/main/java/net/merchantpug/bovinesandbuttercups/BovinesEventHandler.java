package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableRegistryReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID)
public class BovinesEventHandler {
    @SubscribeEvent
    private static void addReloadListeners(AddReloadListenerEvent event) {

    }
}
