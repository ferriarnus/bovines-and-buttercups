package house.greenhouse.bovinesandbuttercups.client.util;

import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.renderer.item.FlowerCrownItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

public class ClearTextureCacheReloadListener extends SimplePreparableReloadListener<Unit> {
    @Override
    protected Unit prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return Unit.INSTANCE;
    }

    @Override
    protected void apply(Unit object, ResourceManager resourceManager, ProfilerFiller profiler) {
        BovinesAndButtercupsClient.clearCowTextureCache();
        FlowerCrownItemRenderer.clearModelMap();
    }
}
