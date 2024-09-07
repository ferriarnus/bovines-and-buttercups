package net.merchantpug.bovinesandbuttercups.mixin.client;

import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ModelManager.class)
public class ModelManagerMixin {
    @Inject(method = "loadModels", at = @At("HEAD"))
    private void barricade$captureModelBakery(ProfilerFiller profilerFiller, Map<ResourceLocation, AtlasSet.StitchResult> atlasPreparations, ModelBakery modelBakery, CallbackInfoReturnable<ModelManager.ReloadState> cir) {
        BovinesAndButtercupsClient.setModelBakery(modelBakery);
    }
}