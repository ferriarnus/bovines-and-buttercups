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
import net.merchantpug.bovinesandbuttercups.client.util.BovineStateModelUtil;
import net.merchantpug.bovinesandbuttercups.client.util.CowTextureReloadListener;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesParticleTypes;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
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
import net.neoforged.neoforge.client.model.SimpleModelState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(value = BovinesAndButtercups.MOD_ID, dist = Dist.CLIENT)
public class BovinesAndButtercupsNeoClient {
    public BovinesAndButtercupsNeoClient(IEventBus eventBus) {
        BovinesAndButtercupsClient.init(new BovinesClientHelperNeo());
    }

    @EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BovineBlockstateTypes.init();
            BovinesAndButtercupsClient.registerCowTexturePaths();
        }

        @SubscribeEvent
        public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
            BovinesAndButtercupsClient.registerCowTexturePaths();
            event.registerReloadListener(new CowTextureReloadListener());
        }

        @SubscribeEvent
        public static void registerModels(ModelEvent.RegisterAdditional event) {
            BovineStateModelUtil.getModels(Minecraft.getInstance().getResourceManager(), Util.backgroundExecutor())
                    .thenAccept(list -> list.forEach(rl -> event.register(ModelResourceLocation.standalone(rl))));
        }

        @SubscribeEvent
        public static void bakeModels(ModelEvent.ModifyBakingResult event) {
            for (Map.Entry<ModelResourceLocation, BakedModel> entry : event.getModels().entrySet()) {
                UnbakedModel unbaked = BovineStateModelUtil.getUnbakedModel(entry.getKey().id(), rl -> event.getModelBakery().getModel(rl));
                if (unbaked != null)
                    event.getModels().put(entry.getKey(), unbaked.bake(event.getModelBakery().new ModelBakerImpl(event.getTextureGetter(), ModelResourceLocation.standalone(entry.getKey().id())), event.getTextureGetter(), BlockModelRotation.X0_Y0));
            }
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BovinesModelLayers.MOOBLOOM_MODEL_LAYER, CowModel::createBodyLayer);
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
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpecial(BovinesParticleTypes.MODEL_LOCATION, new ModelLocationParticle.Provider());
            event.registerSpriteSet(BovinesParticleTypes.BLOOM, BloomParticle.Provider::new);
            event.registerSpriteSet(BovinesParticleTypes.SHROOM, ShroomParticle.Provider::new);
        }
    }
}
