package house.greenhouse.bovinesandbuttercups.mixin.client;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.layer.FlowerCrownLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow protected abstract boolean addLayer(RenderLayer<T, M> layer);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void bovinesandbuttercups$allowEquippingFlowerCrown(EntityRendererProvider.Context context, M model, float shadowRadius, CallbackInfo ci) {
        if (model instanceof HumanoidModel<?> || model instanceof IllagerModel<?> || model instanceof VillagerModel<?>)
            addLayer(new FlowerCrownLayer<>((LivingEntityRenderer)(Object)this, context::bakeLayer, context.getModelManager()));
    }

    @ModifyVariable(method = "getRenderType", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation bovinesandbuttercups$modifyTextureLocation(ResourceLocation value, T living) {
        CowTypeAttachment attachment = BovinesAndButtercups.getHelper().getCowTypeAttachment(living);
        if (attachment != null)
            return BovinesAndButtercupsClient.getCachedTextures(attachment.cowType(), value);
        return value;
    }
}
