package net.merchantpug.bovinesandbuttercups.mixin.client;

import net.minecraft.client.renderer.texture.SimpleTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.IOException;

@Mixin(SimpleTexture.TextureImage.class)
public interface SimpleTextureTextureImageAccessor {
    @Accessor("exception")
    IOException bovinesandbuttercups$getException();
}
