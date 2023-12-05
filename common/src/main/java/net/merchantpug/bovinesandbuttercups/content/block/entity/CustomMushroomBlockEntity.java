package net.merchantpug.bovinesandbuttercups.content.block.entity;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomMushroomBlockEntity extends BlockEntity {
    @Nullable private CustomMushroomType cachedMushroomType;
    @Nullable private String mushroomTypeName;

    public CustomMushroomBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BovinesBlockEntityTypes.CUSTOM_MUSHROOM.value(), worldPosition, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.setMushroomTypeName(tag.getString("Type"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Type", Objects.requireNonNullElse(this.mushroomTypeName, "bovinesandbuttercups:missing_mushroom"));
    }

    @Nullable public String getMushroomTypeName() {
        return mushroomTypeName;
    }

    public void setMushroomTypeName(@Nullable String value) {
        mushroomTypeName = value;
        this.customMushroomType();
    }

    public CustomMushroomType customMushroomType() {
        try {
            if (this.getLevel() != null) {
                if (mushroomTypeName == null) {
                    return CustomMushroomType.MISSING;
                } else if (cachedMushroomType != this.level.registryAccess().registry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE).orElseThrow().get(ResourceLocation.tryParse(mushroomTypeName))) {
                    cachedMushroomType = this.level.registryAccess().registry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE).orElseThrow().get(ResourceLocation.tryParse(mushroomTypeName));
                    return cachedMushroomType;
                } else if (cachedMushroomType != null) {
                    return cachedMushroomType;
                }
            }
        } catch (Exception e) {
            this.cachedMushroomType = CustomMushroomType.MISSING;
            BovinesAndButtercups.LOG.warn("Could not load MushroomType at BlockPos '" + this.getBlockPos().toString() + "': ", e.getMessage());
        }
        return CustomMushroomType.MISSING;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
