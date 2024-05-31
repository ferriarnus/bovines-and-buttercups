package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Map;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentType
            .builder(() -> new LockdownAttachment(Map.of()))
            .serialize(LockdownAttachment.CODEC)
            .build();
}
