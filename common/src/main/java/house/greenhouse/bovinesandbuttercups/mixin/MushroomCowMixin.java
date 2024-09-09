package house.greenhouse.bovinesandbuttercups.mixin;

import house.greenhouse.bovinesandbuttercups.access.MooshroomInitializedTypeAccess;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.util.MooshroomChildTypeUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.MushroomCow;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin implements MooshroomInitializedTypeAccess {
    @Shadow public abstract MushroomCow.MushroomType getVariant();

    @Nullable
    @Unique
    private MushroomCow.MushroomType bovineandbuttercups$initializedType;

    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;contains(Ljava/lang/String;I)Z"))
    private void bovinesandbuttercups$setInitializedType(CompoundTag compound, CallbackInfo ci) {
         if (compound.contains("Type"))
             bovineandbuttercups$initializedType = getVariant();
    }

    @Inject(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/MushroomCow;", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void bovinesandbuttercups$setDataDrivenMooshroomOffspringType(ServerLevel serverLevel, AgeableMob ageableMob, CallbackInfoReturnable<MushroomCow> cir, MushroomCow baby) {
        CowTypeAttachment.setCowType(baby, MooshroomChildTypeUtil.chooseMooshroomBabyType((MushroomCow)(Object)this, (MushroomCow)ageableMob, baby, ((Animal)(Object)this).getLoveCause()));
    }

    @Override
    public MushroomCow.MushroomType bovinesandbuttercups$initialType() {
        return bovineandbuttercups$initializedType;
    }

    @Override
    public void bovinesandbuttercups$clearInitialType() {
        bovineandbuttercups$initializedType = null;
    }
}
