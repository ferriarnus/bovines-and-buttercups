package house.greenhouse.bovinesandbuttercups.network.clientbound;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.attachment.MooshroomExtrasAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.MushroomCow;

public record SyncMooshroomExtrasClientboundPacket(int entityId, MooshroomExtrasAttachment value) implements CustomPacketPayload {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("sync_mooshroom_extras");
    public static final Type<SyncMooshroomExtrasClientboundPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMooshroomExtrasClientboundPacket> STREAM_CODEC = StreamCodec.of(SyncMooshroomExtrasClientboundPacket::write, SyncMooshroomExtrasClientboundPacket::new);

    public SyncMooshroomExtrasClientboundPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), ByteBufCodecs.fromCodec(MooshroomExtrasAttachment.CODEC).decode(buf));
    }

    public static void write(RegistryFriendlyByteBuf buf, SyncMooshroomExtrasClientboundPacket packet) {
        buf.writeInt(packet.entityId);
        ByteBufCodecs.fromCodec(MooshroomExtrasAttachment.CODEC).encode(buf, packet.value);
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity.getType() == EntityType.MOOSHROOM && entity instanceof MushroomCow mooshroom)
                BovinesAndButtercups.getHelper().setMooshroomExtrasAttachment(mooshroom, value);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}