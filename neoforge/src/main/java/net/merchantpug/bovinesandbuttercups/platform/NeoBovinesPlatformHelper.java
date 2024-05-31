package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.merchantpug.bovinesandbuttercups.registry.BovinesAttachments;
import net.merchantpug.bovinesandbuttercups.util.PottedBlockMapUtil;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.NetworkChannel;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Map;

public class NeoBovinesPlatformHelper implements BovinesPlatformHelper {

    private static final NeoBovinesRegistryHelper REGISTRY = new NeoBovinesRegistryHelper();

    @Override
    public BovinesPlatform getPlatform() {
        return BovinesPlatform.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey).create();
    }

    @Override
    public Map<Block, Block> getPottedBlockMap() {
        return PottedBlockMapUtil.getPottedContentMap();
    }

    public BovinesRegistryHelper getRegistryHelper() {
        return REGISTRY;
    }

    @Override
    public LockdownAttachment getLockdownAttachment(LivingEntity entity) {
        return entity.getData(BovinesAttachments.LOCKDOWN);
    }

    @Override
    public void sendTrackingClientboundPacket(CustomPacketPayload payload, LivingEntity entity) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
    }

}