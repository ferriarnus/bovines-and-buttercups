package net.merchantpug.bovinesandbuttercups.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.merchantpug.bovinesandbuttercups.access.SimpleTextureExceptionAccess;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleTexture.class)
public class SimpleTextureMixin implements SimpleTextureExceptionAccess {
    @Unique
    private boolean bovinesandbuttercups$causedException;

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/SimpleTexture$TextureImage;throwIfError()V"))
    private void bovinesandbuttercups$setTextureAsExceptional(ResourceManager resourceManager, CallbackInfo ci, @Local SimpleTexture.TextureImage textureImage) {
        bovinesandbuttercups$causedException = ((SimpleTextureTextureImageAccessor)textureImage).bovinesandbuttercups$getException() != null;
    }

    @Override
    public boolean bovinesandbuttercups$causedException() {
        return bovinesandbuttercups$causedException;
    }
}
