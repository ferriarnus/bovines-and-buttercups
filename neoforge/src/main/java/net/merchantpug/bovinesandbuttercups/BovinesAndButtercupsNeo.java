package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.api.componability.ILockdownComponability;
import net.merchantpug.bovinesandbuttercups.componability.LockdownCapability;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsNeo {
    
    public BovinesAndButtercupsNeo(IEventBus eventBus) {
        BovinesAndButtercups.init();
    }

    @Mod.EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class NeoEventBusListeners {
        @SubscribeEvent
        public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity living) {
                event.addCapability(ILockdownComponability.ID, new LockdownCapability(living));
            }
        }
    }

}