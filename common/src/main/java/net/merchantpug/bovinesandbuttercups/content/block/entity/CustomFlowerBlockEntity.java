package net.merchantpug.bovinesandbuttercups.content.block.entity;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
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

public class CustomFlowerBlockEntity extends BlockEntity {
    @Nullable private CustomFlowerType cachedFlowerType;
    @Nullable private String flowerTypeName;

    public CustomFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(BovinesBlockEntityTypes.CUSTOM_FLOWER.value(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setFlowerTypeName(tag.getString("Type"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Type", Objects.requireNonNullElse(this.flowerTypeName, "bovinesandbuttercups:missing_flower"));
    }

    @Nullable public String getFlowerTypeName() {
        return flowerTypeName;
    }

    public void setFlowerTypeName(@Nullable String value) {
        flowerTypeName = value;
        this.customFlowerType();
    }

    public CustomFlowerType customFlowerType() {
        try {
            if (this.getLevel() != null) {
                if (flowerTypeName == null) {
                    return CustomFlowerType.MISSING;
                } else if (cachedFlowerType != this.level.registryAccess().registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().get(new ResourceLocation(flowerTypeName))) {
                    cachedFlowerType = this.level.registryAccess().registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().get(new ResourceLocation(flowerTypeName));
                    return cachedFlowerType;
                } else if (cachedFlowerType != null) {
                    return cachedFlowerType;
                }
            }
        } catch (Exception e) {
            this.cachedFlowerType = CustomFlowerType.MISSING;
            BovinesAndButtercups.LOG.warn("Could not load FlowerType at BlockPos '" + this.getBlockPos() + "': " + e.getMessage());
        }
        return CustomFlowerType.MISSING;
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
