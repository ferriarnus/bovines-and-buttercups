package net.merchantpug.bovinesandbuttercups.content.block.entity;

import net.merchantpug.bovinesandbuttercups.content.block.CustomHugeMushroomBlock;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomMushroom;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomHugeMushroomBlockEntity extends BlockEntity {
    @Nullable
    private ItemCustomMushroom customMushroom;

    public CustomHugeMushroomBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BovinesBlockEntityTypes.CUSTOM_MUSHROOM_BLOCK, worldPosition, blockState);
    }

    @Nullable
    public ItemCustomMushroom getMushroomType() {
        return customMushroom;
    }

    public void setMushroomType(@Nullable ItemCustomMushroom value) {
        customMushroom = value;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    public void updateState() {
        Level level = this.getLevel();
        BlockPos pos = this.getBlockPos();
        BlockState newState = this.getBlockState();

        if (level.getBlockState(pos.above()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.above()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.UP, Boolean.FALSE);

        if (level.getBlockState(pos.below()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.below()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.DOWN, Boolean.FALSE);

        if (level.getBlockState(pos.west()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.west()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.WEST, Boolean.FALSE);

        if (level.getBlockState(pos.north()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.north()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.NORTH, Boolean.FALSE);

        if (level.getBlockState(pos.east()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.east()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.EAST, Boolean.FALSE);

        if (level.getBlockState(pos.south()).is(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK) && level.getBlockEntity(pos.south()) instanceof CustomHugeMushroomBlockEntity hugeMushroomBlock && Objects.equals(hugeMushroomBlock.getMushroomType().holder(), this.getMushroomType().holder()))
            newState = newState.setValue(CustomHugeMushroomBlock.SOUTH, Boolean.FALSE);

        level.setBlock(pos, newState, 3);
    }
}
