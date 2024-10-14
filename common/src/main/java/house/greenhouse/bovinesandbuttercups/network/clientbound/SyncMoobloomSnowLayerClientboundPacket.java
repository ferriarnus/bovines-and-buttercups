package house.greenhouse.bovinesandbuttercups.network.clientbound;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record SyncMoobloomSnowLayerClientboundPacket(int entityId, boolean value) implements CustomPacketPayload {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("sync_snow_layer");
    public static final CustomPacketPayload.Type<SyncMoobloomSnowLayerClientboundPacket> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMoobloomSnowLayerClientboundPacket> STREAM_CODEC = StreamCodec.of(SyncMoobloomSnowLayerClientboundPacket::write, SyncMoobloomSnowLayerClientboundPacket::new);

    public SyncMoobloomSnowLayerClientboundPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean());
    }

    public static void write(RegistryFriendlyByteBuf buf, SyncMoobloomSnowLayerClientboundPacket packet) {
        buf.writeInt(packet.entityId);
        buf.writeBoolean(packet.value);
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity instanceof Moobloom moobloom)
                moobloom.setSnow(value);
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}