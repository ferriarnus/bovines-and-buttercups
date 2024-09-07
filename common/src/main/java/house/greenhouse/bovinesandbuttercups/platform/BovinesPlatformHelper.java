package house.greenhouse.bovinesandbuttercups.platform;

import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public interface BovinesPlatformHelper {

    BovinesPlatform getPlatform();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey);

    Map<Block, Block> getPottedBlockMap();

    LockdownAttachment getLockdownAttachment(LivingEntity entity);

    CowTypeAttachment getCowTypeAttachment(LivingEntity entity);

    void setCowTypeAttachment(LivingEntity entity, CowTypeAttachment attachment);

    void sendTrackingClientboundPacket(CustomPacketPayload payload, Entity entity);
}