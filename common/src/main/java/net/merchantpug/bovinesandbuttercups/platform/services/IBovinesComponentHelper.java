package net.merchantpug.bovinesandbuttercups.platform.services;

import net.merchantpug.bovinesandbuttercups.api.componability.ILockdownComponability;
import net.merchantpug.bovinesandbuttercups.platform.ServiceUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IBovinesComponentHelper {
    IBovinesComponentHelper INSTANCE = ServiceUtil.load(IBovinesComponentHelper.class);

    ILockdownComponability getLockdown(LivingEntity entity);

    RegistryAccess getAttachedRegistryAccess(ItemStack stack);

    void setAttachedRegistryAccess(ItemStack stack, RegistryAccess access);
}
