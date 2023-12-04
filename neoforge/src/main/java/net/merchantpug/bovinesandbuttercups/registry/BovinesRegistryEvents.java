package net.merchantpug.bovinesandbuttercups.registry;

import dev.greenhouseteam.rdpr.api.ReloadableRegistryEvent;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.util.ReloadableRegistryUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod.EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BovinesRegistryEvents {
    @SubscribeEvent
    public static void createNewRegistries(NewRegistryEvent event) {
        event.register(BovinesRegistries.COW_TYPE_REGISTRY);
    }

    @SubscribeEvent
    public static void createNewDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BovinesRegistryKeys.CONFIGURED_COW_TYPE_KEY, ConfiguredCowType.CODEC, ConfiguredCowType.CODEC);
    }

    @SubscribeEvent
    public static void makeDataPackRegistriesReloadable(ReloadableRegistryEvent event) {
        ReloadableRegistryUtil.makeDataPackRegistriesReloadable(event);
    }
}
