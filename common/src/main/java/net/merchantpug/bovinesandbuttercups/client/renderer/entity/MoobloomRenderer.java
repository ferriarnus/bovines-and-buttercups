package net.merchantpug.bovinesandbuttercups.client.renderer.entity;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.merchantpug.bovinesandbuttercups.client.model.MoobloomModel;
import net.merchantpug.bovinesandbuttercups.client.registry.BovinesModelLayers;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer.MoobloomFlowerLayer;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer.MoobloomGrassLayer;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class MoobloomRenderer extends MobRenderer<Moobloom, MoobloomModel> {

    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context, new MoobloomModel(context.bakeLayer(BovinesModelLayers.MOOBLOOM_MODEL_LAYER)), 0.7f);
        this.addLayer(new MoobloomGrassLayer<>(this));
        this.addLayer(new MoobloomFlowerLayer<>(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public ResourceLocation getTextureLocation(Moobloom entity) {
        ResourceLocation originalLocation = entity.getMoobloomType().unwrapKey().get().location();
        ResourceLocation remappedLocation = entity.getMoobloomType().value().configuration().settings().cowTexture().map(texture -> new ResourceLocation(texture.getNamespace(), "textures/entity" + texture.getPath() + ".png")).orElseGet(() -> new ResourceLocation(originalLocation.getNamespace(), "textures/entity/bovinesandbuttercups/moobloom/" + originalLocation.getPath().toLowerCase(Locale.ROOT) + "_moobloom.png"));
        if (BovinesAndButtercupsClient.LOADED_COW_TEXTURES.contains(remappedLocation))
            return remappedLocation;

        return BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/missing_moobloom.png");
    }
}
