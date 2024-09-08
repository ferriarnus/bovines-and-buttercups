package house.greenhouse.bovinesandbuttercups.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Animal.class)
public interface AnimalAccessor {
    @Invoker("isBrightEnoughToSpawn")
    static boolean bovinesandbuttercups$invokeIsBrightEnoughToSpawn(BlockAndTintGetter level, BlockPos pos) {
        throw new RuntimeException("");
    }
}
