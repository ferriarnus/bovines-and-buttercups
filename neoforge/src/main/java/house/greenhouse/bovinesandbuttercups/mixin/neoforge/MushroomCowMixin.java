package house.greenhouse.bovinesandbuttercups.mixin.neoforge;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.component.ItemCustomMushroom;
import house.greenhouse.bovinesandbuttercups.content.data.configuration.MooshroomConfiguration;
import house.greenhouse.bovinesandbuttercups.registry.BovinesAttachments;
import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin {

    @WrapWithCondition(method = "thunderHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/MushroomCow;setVariant(Lnet/minecraft/world/entity/animal/MushroomCow$MushroomType;)V"))
    private boolean bovinesandbuttercups$cancelThunderConversion(MushroomCow instance, MushroomCow.MushroomType variant) {
        boolean bl = BovinesAndButtercups.convertedByBovines;
        if (bl) {
            BovinesAndButtercups.convertedByBovines = false;
            return false;
        }
        return true;
    }

    @Inject(method = "shear", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void bovinesandbuttercups$cancelItemDroppingIfUnnecessary(SoundSource soundSource, CallbackInfo ci) {
        MushroomCow cow = (MushroomCow)(Object)this;
        if (cow.hasData(BovinesAttachments.COW_TYPE) && cow.getData(BovinesAttachments.COW_TYPE).cowType().value().configuration() instanceof MooshroomConfiguration mc && mc.mushroom().blockState().isEmpty() && mc.mushroom().customType().isEmpty()) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "shear", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/MushroomCow;spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private ItemStack bovinesandbuttercups$modifyShearItem(ItemStack stack) {
        MushroomCow cow = (MushroomCow)(Object)this;
        if (cow.hasData(BovinesAttachments.COW_TYPE) && cow.getData(BovinesAttachments.COW_TYPE).cowType().value().configuration() instanceof MooshroomConfiguration mc) {
            if (mc.mushroom().blockState().isPresent()) {
                return new ItemStack(mc.mushroom().blockState().get().getBlock());
            } else if (mc.mushroom().customType().isPresent()) {
                ItemStack itemStack = new ItemStack(BovinesItems.CUSTOM_MUSHROOM);
                itemStack.set(BovinesDataComponents.CUSTOM_MUSHROOM, new ItemCustomMushroom(mc.mushroom().customType().get()));
                return itemStack;
            }
        }
        return stack;
    }
}