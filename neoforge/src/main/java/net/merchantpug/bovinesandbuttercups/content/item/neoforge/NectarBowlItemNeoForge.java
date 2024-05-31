package net.merchantpug.bovinesandbuttercups.content.item.neoforge;

import net.merchantpug.bovinesandbuttercups.client.BovinesBEWLR;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class NectarBowlItemNeoForge extends NectarBowlItem {
    public NectarBowlItemNeoForge(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return BovinesBEWLR.BLOCK_ENTITY_WITHOUT_LEVEL_RENDERER;
            }
        });
    }
}