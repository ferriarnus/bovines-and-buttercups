package net.merchantpug.bovinesandbuttercups.mixin.client;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @ModifyVariable(method = "getRenderType", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation bovinesandbuttercups$modifyTextureLocation(ResourceLocation value, T living) {
        CowTypeAttachment attachment = BovinesAndButtercups.getHelper().getCowTypeAttachment(living);
        if (attachment != null)
            return BovinesAndButtercupsClient.getCachedTextures(attachment.cowType(), value);
        return value;
    }
}
