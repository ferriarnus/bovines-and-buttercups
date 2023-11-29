package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod.EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BovinesRegistryEvents {
    @SubscribeEvent
    public static void instantiateNewRegistries(NewRegistryEvent event) {
        event.register(BovinesRegistries.COW_TYPE_REGISTRY);
        event.register(BovinesRegistries.CONFIGURED_COW_TYPE_REGISTRY);
    }
}
