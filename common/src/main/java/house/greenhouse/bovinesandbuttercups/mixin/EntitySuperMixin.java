package house.greenhouse.bovinesandbuttercups.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public abstract class EntitySuperMixin {
    @Shadow
    public abstract void thunderHit(ServerLevel $$0, LightningBolt $$1);

    @Shadow public abstract Level level();

    @Unique
    public void bovinesandbuttercups$thunderHit(ServerLevel level, LightningBolt bolt) {
        this.thunderHit(level, bolt);
    }
}
