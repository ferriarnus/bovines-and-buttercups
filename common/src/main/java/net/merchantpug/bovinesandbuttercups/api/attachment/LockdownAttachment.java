package net.merchantpug.bovinesandbuttercups.api.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncLockdownEffectsClientboundPacket;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public record LockdownAttachment(Map<Holder<MobEffect>, Integer> effects) {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("lockdown");
    public static final Codec<LockdownAttachment> CODEC = Codec.simpleMap(BuiltInRegistries.MOB_EFFECT.holderByNameCodec(), Codec.INT, Keyable.forStrings(() -> Stream.of("effect", "duration")))
            .codec().xmap(LockdownAttachment::new, LockdownAttachment::getLockdownMobEffects);

    public LockdownAttachment(Map<Holder<MobEffect>, Integer> effects) {
        this.effects = new HashMap<>(effects);
    }

    Map<Holder<MobEffect>, Integer> getLockdownMobEffects() {
        return Map.copyOf(effects);
    }

    public void addLockdownMobEffect(Holder<MobEffect> effect, int duration) {
        effects.put(effect, duration);
    }

    public void removeLockdownMobEffect(Holder<MobEffect> effect) {
        effects.remove(effect);
    }

    public void setLockdownMobEffects(Map<Holder<MobEffect>, Integer> map) {
        effects.clear();
        effects.putAll(map);
    }

    public static void sync(LivingEntity entity) {
        if (entity.level().isClientSide())
            return;
        BovinesAndButtercups.getHelper().sendTrackingClientboundPacket(new SyncLockdownEffectsClientboundPacket(entity.getId(), BovinesAndButtercups.getHelper().getLockdownAttachment(entity)), entity);
    }
}
