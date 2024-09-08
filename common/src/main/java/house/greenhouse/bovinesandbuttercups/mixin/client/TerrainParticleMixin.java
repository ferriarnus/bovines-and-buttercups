package house.greenhouse.bovinesandbuttercups.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import house.greenhouse.bovinesandbuttercups.client.BovinesAndButtercupsClient;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineBlockstateTypes;
import house.greenhouse.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import house.greenhouse.bovinesandbuttercups.client.util.BovineStateModelUtil;
import house.greenhouse.bovinesandbuttercups.content.block.CustomMushroomPotBlock;
import house.greenhouse.bovinesandbuttercups.content.block.entity.CustomFlowerBlockEntity;
import house.greenhouse.bovinesandbuttercups.content.block.entity.CustomHugeMushroomBlockEntity;
import house.greenhouse.bovinesandbuttercups.content.block.entity.CustomMushroomBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(TerrainParticle.class)
public class TerrainParticleMixin {
    @ModifyArg(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V",  at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/TerrainParticle;setSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private TextureAtlasSprite bovinesandbuttercups$useCustomBlocksForParticle(TextureAtlasSprite original, @Local(argsOnly = true) ClientLevel level, @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof CustomFlowerBlockEntity customFlower && customFlower.getFlowerType() != null) {
            Optional<ResourceLocation> modelLocationWithoutVariant = BovineStatesAssociationRegistry.getBlock(customFlower.getFlowerType().holder().unwrapKey().get().location(), BovineBlockstateTypes.FLOWER);
            if (modelLocationWithoutVariant.isPresent())
                return BovinesAndButtercupsClient.getHelper().getModel(modelLocationWithoutVariant.get().withPath(s -> s + "/" + BovineStateModelUtil.acceptedStateProperties(BovineStateModelUtil.acceptedStateProperties(BlockModelShaper.statePropertiesToString(customFlower.getBlockState().getValues()))))).getParticleIcon();
        } else if (level.getBlockEntity(pos) instanceof CustomMushroomBlockEntity customMushroom && customMushroom.getMushroomType() != null) {
            Optional<ResourceLocation> modelLocationWithoutVariant = BovineStatesAssociationRegistry.getBlock(customMushroom.getMushroomType().holder().unwrapKey().get().location(), BovineBlockstateTypes.MUSHROOM);
            if (modelLocationWithoutVariant.isPresent())
                return BovinesAndButtercupsClient.getHelper().getModel(modelLocationWithoutVariant.get().withPath(s -> s + "/" + BovineStateModelUtil.acceptedStateProperties(BovineStateModelUtil.acceptedStateProperties(BlockModelShaper.statePropertiesToString(customMushroom.getBlockState().getValues()))))).getParticleIcon();
        } else if (level.getBlockEntity(pos) instanceof CustomHugeMushroomBlockEntity customMushroomBlock && customMushroomBlock.getMushroomType() != null) {
            Optional<ResourceLocation> modelLocationWithoutVariant = BovineStatesAssociationRegistry.getBlock(customMushroomBlock.getMushroomType().holder().unwrapKey().get().location(), BovineBlockstateTypes.MUSHROOM_BLOCK);
            if (modelLocationWithoutVariant.isPresent())
                return BovinesAndButtercupsClient.getHelper().getModel(modelLocationWithoutVariant.get().withPath(s -> s + "/" + BovineStateModelUtil.acceptedStateProperties(BovineStateModelUtil.acceptedStateProperties(BlockModelShaper.statePropertiesToString(customMushroomBlock.getBlockState().getValues()))))).getParticleIcon();
        }
        return original;
    }
}
