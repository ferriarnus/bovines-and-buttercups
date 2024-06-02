package net.merchantpug.bovinesandbuttercups.network.clientbound;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.content.data.modifier.ConditionedModifierFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public record SyncConditionedTextureModifier(int entityId, ResourceLocation conditionId, boolean value) implements CustomPacketPayload {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("sync_conditioned_texture_modifier");
    public static final Type<SyncConditionedTextureModifier> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncConditionedTextureModifier> STREAM_CODEC = StreamCodec.of(SyncConditionedTextureModifier::write, SyncConditionedTextureModifier::new);

    public SyncConditionedTextureModifier(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readResourceLocation(), buf.readBoolean());
    }

    public static void write(RegistryFriendlyByteBuf buf, SyncConditionedTextureModifier packet) {
        buf.writeInt(packet.entityId);
        buf.writeResourceLocation(packet.conditionId);
        buf.writeBoolean(packet.value);
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (!(entity instanceof LivingEntity living))
                return;
            Holder<CowType<?>> cowType = BovinesAndButtercups.getHelper().getCowTypeAttachment(living).cowType();
            if (!cowType.isBound())
                return;
            cowType.value().configuration().getTextureModifierFactories().stream().filter(textureModifierFactory -> {
                if (textureModifierFactory instanceof ConditionedModifierFactory conditioned)
                    return conditioned.getConditionId().equals(conditionId);
                return false;
            }).findFirst().ifPresent(condition -> ((ConditionedModifierFactory)condition).setConditionValue(entity, value));
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
