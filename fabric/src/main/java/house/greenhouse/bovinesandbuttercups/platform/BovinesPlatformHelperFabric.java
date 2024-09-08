package house.greenhouse.bovinesandbuttercups.platform;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.api.attachment.LockdownAttachment;
import house.greenhouse.bovinesandbuttercups.registry.BovinesAttachments;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BovinesPlatformHelperFabric implements BovinesPlatformHelper {

    @Override
    public BovinesPlatform getPlatform() {
        return BovinesPlatform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        return FabricRegistryBuilder.createSimple(registryKey).buildAndRegister();
    }

    @Override
    public Map<Block, Block> getPottedBlockMap() {
        return FlowerPotBlock.POTTED_BY_CONTENT;
    }

    @Override
    public LockdownAttachment getLockdownAttachment(LivingEntity entity) {
        return entity.getAttachedOrCreate(BovinesAttachments.LOCKDOWN);
    }

    @Override
    public CowTypeAttachment getCowTypeAttachment(LivingEntity entity) {
        return entity.getAttached(BovinesAttachments.COW_TYPE);
    }

    @Override
    public void setCowTypeAttachment(LivingEntity entity, CowTypeAttachment attachment) {
        entity.setAttached(BovinesAttachments.COW_TYPE, attachment);
    }

    @Override
    public void sendTrackingClientboundPacket(CustomPacketPayload payload, Entity entity) {
        for (ServerPlayer other : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(other, payload);
        }
        if (entity instanceof ServerPlayer player)
            ServerPlayNetworking.send(player, payload);
    }

    @Override
    public boolean isPerfected(BeehiveBlockEntity blockEntity) {
        return blockEntity.getAttachedOrElse(BovinesAttachments.PERFECTED, false);
    }

    @Override
    public boolean isPerfected(Entity bee) {
        return bee.getAttachedOrElse(BovinesAttachments.PERFECTED, false);
    }

    @Override
    public void setPerfected(BeehiveBlockEntity blockEntity, boolean value) {
        if (!value) {
            blockEntity.removeAttached(BovinesAttachments.PERFECTED);
            return;
        }
        blockEntity.setAttached(BovinesAttachments.PERFECTED, true);
    }

    @Override
    public void setPerfected(Entity bee, boolean value) {
        if (!value) {
            bee.removeAttached(BovinesAttachments.PERFECTED);
            return;
        }
        bee.setAttached(BovinesAttachments.PERFECTED, true);
    }

    @Override
    public Optional<UUID> getPollinatingMoobloom(Bee bee) {
        return Optional.ofNullable(bee.getAttachedOrElse(BovinesAttachments.POLLINATING_MOOBLOOM, null));
    }

    @Override
    public void setPollinatingMoobloom(Bee bee, @Nullable UUID uuid) {
        if (uuid == null) {
            bee.removeAttached(BovinesAttachments.POLLINATING_MOOBLOOM);
            return;
        }
        bee.setAttached(BovinesAttachments.POLLINATING_MOOBLOOM, uuid);
    }

}
