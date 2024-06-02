package net.merchantpug.bovinesandbuttercups.mixin.fabric;

import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.effect.LockdownEffect;
import net.merchantpug.bovinesandbuttercups.registry.BovinesAttachments;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEffects;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> mobEffect);

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void bovinesandbuttercups$tickLockdown(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!this.level().isClientSide() && entity.hasEffect(BovinesEffects.LOCKDOWN)) {
            HashMap<Holder<MobEffect>, Integer> lockdownEffectsToUpdate = new HashMap<>();
            entity.getAttached(BovinesAttachments.LOCKDOWN).effects().forEach(((effect, integer) -> {
                if (integer > 0) {
                    lockdownEffectsToUpdate.put(effect, --integer);
                } else if (integer == -1) {
                    lockdownEffectsToUpdate.put(effect, -1);
                }
            }));
            entity.getAttached(BovinesAttachments.LOCKDOWN).setLockdownMobEffects(lockdownEffectsToUpdate);
            LockdownAttachment.sync(entity);
        }

        if (entity.hasAttached(BovinesAttachments.COW_TYPE) && entity.getAttached(BovinesAttachments.COW_TYPE).cowType().isBound())
            entity.getAttached(BovinesAttachments.COW_TYPE).cowType().value().configuration().tick(this);
    }

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void bovinesandbuttercups$addRandomLockdown(MobEffectInstance effect, Entity entity, CallbackInfo ci) {
        if (effect.getEffect().isBound() && effect.getEffect().value() instanceof LockdownEffect) {
            LockdownAttachment attachment = this.getAttachedOrCreate(BovinesAttachments.LOCKDOWN);
            if (!level().isClientSide && (attachment.effects().isEmpty() || attachment.effects().values().stream().allMatch(value -> value < effect.getDuration()))) {
                Optional<Holder.Reference<MobEffect>> randomEffect = Util.getRandomSafe(BuiltInRegistries.MOB_EFFECT.holders().filter(holder -> holder.isBound() && holder.value().isEnabled(level().enabledFeatures())).toList(), level().getRandom());
                randomEffect.ifPresent(entry -> {
                    attachment.addLockdownMobEffect(entry, effect.getDuration());
                    LockdownAttachment.sync((LivingEntity)(Object)this);
                });
            }
            if (!level().isClientSide && (LivingEntity) (Object) this instanceof ServerPlayer serverPlayer && !attachment.effects().isEmpty()) {
                attachment.effects().forEach((effect1, duration) -> {
                    if (!this.hasEffect(effect1)) return;
                    LockEffectTrigger.INSTANCE.trigger(serverPlayer, effect1);
                });
            }
        }
    }

    @Inject(method = "onEffectUpdated", at = @At("TAIL"))
    private void bovinesandbuttercups$updateWithRandomLockdown(MobEffectInstance effect, boolean bl, Entity entity, CallbackInfo ci) {
        LockdownAttachment attachment = this.getAttached(BovinesAttachments.LOCKDOWN);
        if (!level().isClientSide && effect.getEffect().isBound() && effect.getEffect().value() instanceof LockdownEffect && attachment != null && (attachment.effects().isEmpty() || attachment.effects().values().stream().allMatch(value -> value < effect.getDuration()))) {
            Optional<Holder.Reference<MobEffect>> randomEffect = Util.getRandomSafe(BuiltInRegistries.MOB_EFFECT.holders().filter(holder -> holder.isBound() && holder.value().isEnabled(level().enabledFeatures())).toList(), level().getRandom());
            randomEffect.ifPresent(entry -> {
                attachment.addLockdownMobEffect(entry, effect.getDuration());
                LockdownAttachment.sync((LivingEntity)(Object)this);
            });
        }
    }

    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void bovinesandbuttercups$removeAllLockdownEffects(MobEffectInstance effect, CallbackInfo ci) {
        LockdownAttachment attachment = this.getAttached(BovinesAttachments.LOCKDOWN);
        if (level().isClientSide || attachment == null || !((Entity)(Object)this instanceof LivingEntity living) || !effect.getEffect().isBound() || !(effect.getEffect().value() instanceof LockdownEffect)) return;
        attachment.effects().clear();
        LockdownAttachment.sync(living);
    }

    @Inject(method = "canBeAffected", at = @At(value = "RETURN"), cancellable = true)
    private void bovinesandbuttercups$cancelStatusEffectIfLocked(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        LockdownAttachment attachment = this.getAttached(BovinesAttachments.LOCKDOWN);
        if (hasEffect(BovinesEffects.LOCKDOWN) && attachment != null && attachment.effects().containsKey(effect.getEffect())) {
            if (!level().isClientSide && (LivingEntity)(Object)this instanceof ServerPlayer serverPlayer)
                PreventEffectTrigger.INSTANCE.trigger(serverPlayer, effect.getEffect());

            cir.setReturnValue(false);
        }
    }
}
