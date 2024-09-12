package house.greenhouse.bovinesandbuttercups.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.HoneyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HoneyBlock.class)
public interface HoneyBlockAccessor {
    @Invoker("doesEntityDoHoneyBlockSlideEffects")
    static boolean doesEntityDoHoneyBlockSlideEffects(Entity entity) {
        throw new RuntimeException("");
    }
}
