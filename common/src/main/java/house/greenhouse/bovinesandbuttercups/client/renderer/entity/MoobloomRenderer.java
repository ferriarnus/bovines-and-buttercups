package house.greenhouse.bovinesandbuttercups.client.renderer.entity;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.model.MoobloomModel;
import house.greenhouse.bovinesandbuttercups.client.util.BovinesModelLayers;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.layer.MoobloomFlowerLayer;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.layer.CowLayersLayer;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MoobloomRenderer extends MobRenderer<Moobloom, MoobloomModel> {

    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context, new MoobloomModel(context.bakeLayer(BovinesModelLayers.MOOBLOOM_MODEL_LAYER)), 0.7F);
        this.addLayer(new CowLayersLayer<>(this));
        this.addLayer(new MoobloomFlowerLayer(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public ResourceLocation getTextureLocation(Moobloom entity) {
        return BovinesAndButtercups.asResource("textures/entity/bovinesandbuttercups/moobloom/missing_moobloom.png");
    }
}
