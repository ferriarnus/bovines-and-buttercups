package net.merchantpug.bovinesandbuttercups.client;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.particle.BloomParticle;
import net.merchantpug.bovinesandbuttercups.client.particle.ModelLocationParticle;
import net.merchantpug.bovinesandbuttercups.client.particle.ShroomParticle;
import net.merchantpug.bovinesandbuttercups.client.platform.BovinesClientHelperNeo;
import net.merchantpug.bovinesandbuttercups.client.registry.BovinesModelLayers;
import net.merchantpug.bovinesandbuttercups.client.renderer.block.CustomFlowerPotBlockRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.block.CustomFlowerRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.block.CustomHugeMushroomBlockRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.block.CustomMushroomPotBlockRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.block.CustomMushroomRenderer;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.MoobloomRenderer;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.layer.CowLayersLayer;
import net.merchantpug.bovinesandbuttercups.client.renderer.entity.model.FlowerCrownModel;
import net.merchantpug.bovinesandbuttercups.client.renderer.item.FlowerCrownItemRenderer;
import net.merchantpug.bovinesandbuttercups.client.util.BovineStateModelUtil;
import net.merchantpug.bovinesandbuttercups.client.util.ClearTextureCacheReloadListener;
import net.merchantpug.bovinesandbuttercups.content.item.FlowerCrownItem;
import net.merchantpug.bovinesandbuttercups.mixin.client.ModelBakeryAccessor;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEffects;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.List;

@Mod(value = BovinesAndButtercups.MOD_ID, dist = Dist.CLIENT)
public class BovinesAndButtercupsNeoForgeClient {
    public BovinesAndButtercupsNeoForgeClient(IEventBus eventBus) {
        BovinesAndButtercupsClient.init(new BovinesClientHelperNeo());
    }

    @EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BovineBlockstateTypes.init();
        }

        public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(new ClearTextureCacheReloadListener());
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerMobEffect(LockdownClientEffectExtensions.INSTANCE, BovinesEffects.LOCKDOWN);

            event.registerItem(BovinesBEWLR.ITEM_EXTENSIONS, BovinesItems.CUSTOM_FLOWER);
            event.registerItem(BovinesBEWLR.ITEM_EXTENSIONS, BovinesItems.CUSTOM_MUSHROOM);
            event.registerItem(BovinesBEWLR.ITEM_EXTENSIONS, BovinesItems.CUSTOM_MUSHROOM_BLOCK);
            event.registerItem(BovinesBEWLR.ITEM_EXTENSIONS, BovinesItems.FLOWER_CROWN);
            event.registerItem(BovinesBEWLR.ITEM_EXTENSIONS, BovinesItems.NECTAR_BOWL);
        }

        @SubscribeEvent
        public static void bakeModels(ModelEvent.ModifyBakingResult event) {
            List<ResourceLocation> models = BovineStateModelUtil.getModels(Minecraft.getInstance().getResourceManager(), Runnable::run).join();
            for (ResourceLocation entry : models) {
                UnbakedModel unbaked = BovineStateModelUtil.getUnbakedModel(entry, ((ModelBakeryAccessor)event.getModelBakery())::bovinesandbuttercups$getModel);
                if (unbaked != null) {
                    unbaked.resolveParents(location -> ((ModelBakeryAccessor)event.getModelBakery()).bovinesandbuttercups$getModel(location));
                    ModelResourceLocation modelResource = ModelResourceLocation.standalone(entry);
                    BakedModel model = unbaked.bake(event.getModelBakery().new ModelBakerImpl((rl, material) -> material.sprite(), modelResource), event.getTextureGetter(), BlockModelRotation.X0_Y0);
                    event.getModels().put(modelResource, model);
                }
            }

            List<ResourceLocation> flowerCrownModels = List.of(FlowerCrownItemRenderer.BASE);
            for (ResourceLocation entry : flowerCrownModels) {
                UnbakedModel unbaked = ((ModelBakeryAccessor)event.getModelBakery()).bovinesandbuttercups$getModel(entry);
                unbaked.resolveParents(location -> ((ModelBakeryAccessor)event.getModelBakery()).bovinesandbuttercups$getModel(location));
                ModelResourceLocation modelResource = ModelResourceLocation.standalone(entry);
                BakedModel model = unbaked.bake(event.getModelBakery().new ModelBakerImpl((rl, material) -> material.sprite(), modelResource), event.getTextureGetter(), BlockModelRotation.X0_Y0);
                event.getModels().put(modelResource, model);
            }
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BovinesModelLayers.MOOBLOOM_MODEL_LAYER, CowModel::createBodyLayer);
            event.registerLayerDefinition(BovinesModelLayers.FLOWER_CROWN_MODEL_LAYER, FlowerCrownModel::createLayer);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(BovinesEntityTypes.MOOBLOOM, MoobloomRenderer::new);
            event.registerBlockEntityRenderer(BovinesBlockEntityTypes.CUSTOM_FLOWER, CustomFlowerRenderer::new);
            event.registerBlockEntityRenderer(BovinesBlockEntityTypes.CUSTOM_MUSHROOM, CustomMushroomRenderer::new);
            event.registerBlockEntityRenderer(BovinesBlockEntityTypes.POTTED_CUSTOM_FLOWER, CustomFlowerPotBlockRenderer::new);
            event.registerBlockEntityRenderer(BovinesBlockEntityTypes.POTTED_CUSTOM_MUSHROOM, CustomMushroomPotBlockRenderer::new);
            event.registerBlockEntityRenderer(BovinesBlockEntityTypes.CUSTOM_MUSHROOM_BLOCK, CustomHugeMushroomBlockRenderer::new);
        }

        @SubscribeEvent
        public static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
            MushroomCowRenderer mushroomCowRenderer = event.getRenderer(EntityType.MOOSHROOM);
            // mushroomCowRenderer.addLayer(new MushroomCowDatapackMushroomLayer<>(mushroomCowRenderer, Minecraft.getInstance().getBlockRenderer()));
            mushroomCowRenderer.addLayer(new CowLayersLayer<>(mushroomCowRenderer));
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpecial(BovinesParticleTypes.MODEL_LOCATION, new ModelLocationParticle.Provider());
            event.registerSpriteSet(BovinesParticleTypes.BLOOM, BloomParticle.Provider::new);
            event.registerSpriteSet(BovinesParticleTypes.SHROOM, ShroomParticle.Provider::new);
        }
    }
}
