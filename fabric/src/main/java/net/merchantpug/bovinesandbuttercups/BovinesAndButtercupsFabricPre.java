package net.merchantpug.bovinesandbuttercups;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.merchantpug.bovinesandbuttercups.platform.BovinesPlatformHelperFabric;

public class BovinesAndButtercupsFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        BovinesAndButtercups.init(new BovinesPlatformHelperFabric());
    }
}
