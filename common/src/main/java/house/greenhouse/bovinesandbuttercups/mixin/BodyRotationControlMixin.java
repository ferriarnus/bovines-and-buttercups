package house.greenhouse.bovinesandbuttercups.mixin;

import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BodyRotationControl.class)
public abstract class BodyRotationControlMixin {
    @Shadow @Final private Mob mob;

    @Shadow protected abstract void rotateHeadIfNecessary();

    @Inject(method = "clientTick", at = @At("HEAD"), cancellable = true)
    private void bovinesandbuttercups$cancelBodyRotation(CallbackInfo ci) {
        if (this.mob instanceof Moobloom moobloom && moobloom.getStandingStillForBeeTicks() > 0) {
            this.rotateHeadIfNecessary();
            ci.cancel();
        }
    }
}
