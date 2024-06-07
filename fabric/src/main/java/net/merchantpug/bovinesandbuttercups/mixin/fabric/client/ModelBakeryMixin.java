package net.merchantpug.bovinesandbuttercups.mixin.fabric.client;

import net.merchantpug.bovinesandbuttercups.client.util.BovineStateModelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

// TODO: Remove this as soon as Fabric Model Loading API updates.
@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Shadow abstract UnbakedModel getModel(ResourceLocation $$0);

    @Shadow protected abstract void registerModelAndLoadDependencies(ModelResourceLocation $$0x, UnbakedModel $$1x);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void bovinesandbuttercups$initCustomModels(BlockColors blockColors, ProfilerFiller profilerFiller, Map map, Map map2, CallbackInfo ci) {
        BovineStateModelUtil.getModels(Minecraft.getInstance().getResourceManager()).forEach(rl -> {
            UnbakedModel model = getModel(rl.id());
            registerModelAndLoadDependencies(rl, model);
        });
    }
}
