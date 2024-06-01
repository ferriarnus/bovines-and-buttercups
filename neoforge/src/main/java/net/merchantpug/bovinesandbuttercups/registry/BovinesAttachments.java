package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BovinesAttachments {
    public static final AttachmentType<LockdownAttachment> LOCKDOWN = AttachmentType
            .builder(() -> new LockdownAttachment(Map.of()))
            .serialize(LockdownAttachment.CODEC)
            .build();

    public static final AttachmentType<CowTypeAttachment> COW_TYPE = AttachmentType
            .builder(() -> new CowTypeAttachment(Holder.direct(null), Optional.empty()))
            .serialize(new IAttachmentSerializer<>() {
                @Override
                public CowTypeAttachment read(IAttachmentHolder holder, Tag tag, HolderLookup.Provider provider) {
                    return CowTypeAttachment.CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, provider), tag).getOrThrow();
                }

                @Override
                public @Nullable Tag write(CowTypeAttachment attachment, HolderLookup.Provider provider) {
                    return CowTypeAttachment.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, provider), attachment).getOrThrow();
                }
            })
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, CowTypeAttachment.ID, COW_TYPE);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, LockdownAttachment.ID, LOCKDOWN);
    }
}
