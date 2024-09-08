package house.greenhouse.bovinesandbuttercups.registry;

import com.mojang.serialization.Codec;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.api.attachment.LockdownAttachment;
import house.greenhouse.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentType
            .builder(() -> new LockdownAttachment(Map.of()))
            .serialize(LockdownAttachment.CODEC)
            .build();

    public static final AttachmentType<CowTypeAttachment> COW_TYPE = AttachmentType
            .builder(() -> new CowTypeAttachment(Holder.direct(null), Optional.empty()))
            .serialize(CowTypeAttachment.CODEC)
            .build();

    public static final AttachmentType<Boolean> PERFECTED = AttachmentType
            .builder(() -> false)
            .serialize(Codec.BOOL)
            .build();
    public static final AttachmentType<UUID> POLLINATING_MOOBLOOM = AttachmentType
            .builder(() -> (UUID)null)
            .serialize(UUIDUtil.CODEC)
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, CowTypeAttachment.ID, COW_TYPE);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, LockdownAttachment.ID, LOCKDOWN);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, BovinesAndButtercups.asResource("perfected"), PERFECTED);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, BovinesAndButtercups.asResource("pollinating_moobloom"), POLLINATING_MOOBLOOM);
    }
}
