package house.greenhouse.bovinesandbuttercups.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.access.BeeGoalAccess;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeePollinateGoal")
public abstract class BeePollinateGoalMixin {
    @Final
    @Shadow(aliases = { "this$0", "field_20377" })
    Bee bee;

    @Inject(method = "canBeeUse", at = @At(value = "HEAD"), cancellable = true)
    private void bovinesandbuttercups$cancelIfBeeHasMoobloom(CallbackInfoReturnable<Boolean> cir) {
        if (BovinesAndButtercups.getHelper().getPollinatingMoobloom(bee).isEmpty())
            return;
        cir.setReturnValue(false);
    }

    @ModifyReturnValue(method = "isPollinating", at = @At(value = "RETURN"))
    private boolean bovinesandbuttercups$isPollinatingMoobloomOrFlower(boolean value) {
        return value || ((BeeGoalAccess)bee).bovinesandbuttercups$getPollinateFlowerCowGoal().isPollinating();
    }

    @Inject(method = "stopPollinating", at = @At(value = "TAIL"))
    private void bovinesandbuttercups$stopMoobloomPollination(CallbackInfo ci) {
        ((BeeGoalAccess)bee).bovinesandbuttercups$getPollinateFlowerCowGoal().stopPollinating();
    }
}
