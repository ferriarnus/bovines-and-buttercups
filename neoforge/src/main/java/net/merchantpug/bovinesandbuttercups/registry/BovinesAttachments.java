package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.Optional;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentType
            .builder(() -> new LockdownAttachment(Map.of()))
            .serialize(LockdownAttachment.CODEC)
            .build();

    public static final AttachmentType<CowTypeAttachment> COW_TYPE = AttachmentType
            .builder(() -> new CowTypeAttachment(Holder.direct(null), Optional.empty()))
            .serialize(CowTypeAttachment.CODEC)
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, CowTypeAttachment.ID, COW_TYPE);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, LockdownAttachment.ID, LOCKDOWN);
    }
}
