package house.greenhouse.bovinesandbuttercups.content.block.entity;

import house.greenhouse.bovinesandbuttercups.api.block.CustomMushroomType;
import house.greenhouse.bovinesandbuttercups.content.component.ItemCustomMushroom;
import house.greenhouse.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CustomMushroomPotBlockEntity extends BlockEntity {
    @Nullable
    private ItemCustomMushroom customMushroom;

    public CustomMushroomPotBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BovinesBlockEntityTypes.POTTED_CUSTOM_MUSHROOM, worldPosition, blockState);
    }

    protected void applyImplicitComponents(BlockEntity.DataComponentInput input) {
        customMushroom = input.get(BovinesDataComponents.CUSTOM_MUSHROOM);
    }

    @Nullable public ItemCustomMushroom getMushroomType() {
        return customMushroom;
    }

    public void setMushroomType(@Nullable ItemCustomMushroom value) {
        customMushroom = value;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("custom_mushroom"))
            setMushroomType(new ItemCustomMushroom(CustomMushroomType.CODEC.decode(registries.createSerializationContext(NbtOps.INSTANCE), tag.get("custom_mushroom")).getOrThrow().getFirst()));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (getMushroomType() != null)
            tag.put("custom_mushroom", CustomMushroomType.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), getMushroomType().holder()).getOrThrow());
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
}
