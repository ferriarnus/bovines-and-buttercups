package net.merchantpug.bovinesandbuttercups.client;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.platform.IBovinesClientHelper;
import net.merchantpug.bovinesandbuttercups.client.resources.BovineBlockstateTypes;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesPlatformHelper;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.stream.Stream;

public class BovinesAndButtercupsClient {
    public static final HashSet<ResourceLocation> LOADED_COW_TEXTURES = new HashSet<>();
    private static final HashSet<String> COW_TEXTURE_PATHS = new HashSet<>();

    public static void init() {
        BovineBlockstateTypes.init();

        if (!IBovinesPlatformHelper.INSTANCE.getPlatformName().equals("NeoForge")) {
            registerCowTexturePaths();
        }

        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.BUTTERCUP.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_BUTTERCUP.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.PINK_DAISY.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_PINK_DAISY.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.LIMELIGHT.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_LIMELIGHT.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.BIRD_OF_PARADISE.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_BIRD_OF_PARADISE.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.CHARGELILY.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_CHARGELILY.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.HYACINTH.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_HYACINTH.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.SNOWDROP.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_SNOWDROP.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.TROPICAL_BLUE.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_TROPICAL_BLUE.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.FREESIA.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_FREESIA.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.CUSTOM_FLOWER.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_CUSTOM_FLOWER.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.CUSTOM_MUSHROOM.value(), RenderType.cutout());
        IBovinesClientHelper.INSTANCE.setRenderLayer(BovinesBlocks.POTTED_CUSTOM_MUSHROOM.value(), RenderType.cutout());
    }

    public static Stream<String> cowTexturePathsAsStream() {
        return COW_TEXTURE_PATHS.stream();
    }

    public static void registerCowTexturePath(String path) {
        if (!COW_TEXTURE_PATHS.contains(path))
            COW_TEXTURE_PATHS.add(path);
        else
            BovinesAndButtercups.LOG.warn("Tried registering cow texture path '{}' more than once. (Skipping).", path);
    }

    public static void registerCowTexturePaths() {
        registerCowTexturePath("textures/entity/cow");
        registerCowTexturePath("textures/entity/bovinesandbuttercups");
    }

}
