package net.merchantpug.bovinesandbuttercups.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.merchantpug.bovinesandbuttercups.api.componability.ILockdownComponability;
import net.merchantpug.bovinesandbuttercups.componability.ILockdownComponent;
import net.merchantpug.bovinesandbuttercups.componability.LockdownComponent;
import net.minecraft.world.entity.LivingEntity;

public class BovinesEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<ILockdownComponent> LOCKDOWN_COMPONENT = ComponentRegistry.getOrCreate(ILockdownComponability.ID, ILockdownComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, LOCKDOWN_COMPONENT, LockdownComponent::new);
    }
}
