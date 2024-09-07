package net.merchantpug.bovinesandbuttercups.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class NectarBowlItemRenderer {
    public static void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        ResourceLocation resourceLocation = BovinesAndButtercups.asResource("bovinesandbuttercups/item/colored_nectar_bowl/inventory");
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (stack.has(BovinesDataComponents.MOOBLOOM_TYPE)) {
            Optional<ResourceLocation> modelLocationWithoutVariant = Optional.ofNullable(stack.get(BovinesDataComponents.MOOBLOOM_TYPE)).filter(itemMoobloomType -> itemMoobloomType.cowType().isBound() && itemMoobloomType.cowType().value().configuration() instanceof MoobloomConfiguration).flatMap(itemMoobloomType -> ((MoobloomConfiguration)itemMoobloomType.cowType().value().configuration()).nectarPalette());
            if (modelLocationWithoutVariant.isPresent())
                resourceLocation = BovineStatesAssociationRegistry.getItem(modelLocationWithoutVariant.get()).orElse(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl/inventory"));
        } else
            resourceLocation = BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl/inventory");

        BakedModel nectarBowlModel = BovinesAndButtercupsClient.getHelper().getModel(resourceLocation);

        boolean left = context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        poseStack.translate(0.5F, 0.5F, 0.5F);

        boolean bl = context == ItemDisplayContext.GUI && !nectarBowlModel.usesBlockLight();
        MultiBufferSource.BufferSource source = null;

        if (bl) {
            Lighting.setupForFlatItems();
            source = Minecraft.getInstance().renderBuffers().bufferSource();
        }

        Minecraft.getInstance().getItemRenderer().render(stack, context, left, poseStack, source == null ? bufferSource : source, light, overlay, nectarBowlModel);

        if (bl) {
            source.endBatch();
            Lighting.setupFor3DItems();
        }
    }
}
