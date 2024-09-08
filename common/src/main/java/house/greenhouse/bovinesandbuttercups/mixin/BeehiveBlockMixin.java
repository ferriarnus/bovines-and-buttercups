package house.greenhouse.bovinesandbuttercups.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin {
    @ModifyArg(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"))
    private ItemLike bovinesandbuttercups$setToPerfectedHoney(ItemLike original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof BeehiveBlockEntity beehive) {
            if (BovinesAndButtercups.getHelper().isPerfected(beehive)) {
                BovinesAndButtercups.getHelper().setPerfected(beehive, false);
                return BovinesItems.PERFECTED_HONEY_BOTTLE;
            }
        }
        return original;
    }

    @ModifyArg(method = "dropHoneycomb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;I)V"), index = 1)
    private static int bovinesandbuttercups$dropMoreHoneycombWhenPerfected(int original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof BeehiveBlockEntity beehive) {
            if (BovinesAndButtercups.getHelper().isPerfected(beehive)) {
                BovinesAndButtercups.getHelper().setPerfected(beehive, false);
                return original * 2;
            }
        }
        return original;
    }
}
