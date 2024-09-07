package house.greenhouse.bovinesandbuttercups;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.platform.BovinesPlatformHelperFabric;

public class BovinesAndButtercupsFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        BovinesAndButtercups.init(new BovinesPlatformHelperFabric());
    }
}
