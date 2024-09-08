package house.greenhouse.bovinesandbuttercups.mixin;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {
    @Inject(method = "addOccupant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BeehiveBlockEntity;storeBee(Lnet/minecraft/world/level/block/entity/BeehiveBlockEntity$Occupant;)V"))
    private void bovinesandbuttercups$setPerfected(Entity occupant, CallbackInfo ci) {
        if (BovinesAndButtercups.getHelper().isPerfected(occupant))
            BovinesAndButtercups.getHelper().setPerfected((BeehiveBlockEntity)(Object)this, true);
    }
}
