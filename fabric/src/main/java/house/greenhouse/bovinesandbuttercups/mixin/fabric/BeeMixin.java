package house.greenhouse.bovinesandbuttercups.mixin.fabric;

import house.greenhouse.bovinesandbuttercups.access.BeeGoalAccess;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal {
    protected BeeMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void bovinesandbuttercupsmethod$handleAiStep(CallbackInfo ci) {
        if (this.level().isClientSide() || ((BeeGoalAccess) this).bovinesandbuttercups$getPollinateMoobloomGoal() == null) return;
        ((BeeGoalAccess) this).bovinesandbuttercups$getPollinateMoobloomGoal().tickCooldown();
    }
}
