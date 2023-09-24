package net.merchantpug.bovinesandbuttercups.mixin;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.merchantpug.bovinesandbuttercups.registry.internal.ReloadableRegistryReloadListener;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Yes, I'm using a mixin on my own class. Cry about it.
 */
@Mixin(ReloadableRegistryReloadListener.class)
public abstract class ReloadableRegistryReloadListenerMixin implements IdentifiableResourceReloadListener {
    @Shadow @Final
    private ResourceLocation baseDirectory;
    @Override
    public ResourceLocation getFabricId() {
        return this.baseDirectory;
    }
}
