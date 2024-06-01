package net.merchantpug.bovinesandbuttercups.registry;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;

import java.util.Map;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentRegistry.<LockdownAttachment>builder()
            .persistent(LockdownAttachment.CODEC)
            .initializer(() -> new LockdownAttachment(Map.of()))
            .buildAndRegister(LockdownAttachment.ID);
}
