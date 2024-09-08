package house.greenhouse.bovinesandbuttercups.mixin;

import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.util.MooshroomChildTypeUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.MushroomCow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MushroomCow.class)
public class MushroomCowMixin {
    @Inject(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/MushroomCow;", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void bovinesandbuttercups$setDataDrivenMooshroomOffspringType(ServerLevel serverLevel, AgeableMob ageableMob, CallbackInfoReturnable<MushroomCow> cir, MushroomCow baby) {
        CowTypeAttachment.setCowType(baby, MooshroomChildTypeUtil.chooseMooshroomBabyType((MushroomCow)(Object)this, (MushroomCow)ageableMob, baby, ((Animal)(Object)this).getLoveCause()));
    }
}
