package net.merchantpug.bovinesandbuttercups.componability;

import net.merchantpug.bovinesandbuttercups.api.componability.ILockdownComponability;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.common.capabilities.CapabilityToken;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface ILockdownCapability extends ILockdownComponability, INBTSerializable<CompoundTag> {
    Capability<ILockdownCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
}
