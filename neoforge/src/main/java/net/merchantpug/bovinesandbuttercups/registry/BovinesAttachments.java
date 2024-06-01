package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Map;
import java.util.Optional;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentType
            .builder(() -> new LockdownAttachment(Map.of()))
            .serialize(LockdownAttachment.CODEC)
            .build();

    public static final AttachmentType<CowTypeAttachment> COW_TYPE = AttachmentType
            .builder(() -> new CowTypeAttachment(null, Optional.empty()))
            .serialize(CowTypeAttachment.CODEC)
            .build();
}
