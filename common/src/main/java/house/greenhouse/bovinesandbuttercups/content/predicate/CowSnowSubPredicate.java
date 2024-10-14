package house.greenhouse.bovinesandbuttercups.content.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record CowSnowSubPredicate(boolean hasSnow) implements EntitySubPredicate {
    public static final MapCodec<CowSnowSubPredicate> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.BOOL.fieldOf("has_snow").forGetter(CowSnowSubPredicate::hasSnow)
    ).apply(inst, CowSnowSubPredicate::new));

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return CODEC;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof Moobloom moobloom)
            return moobloom.hasSnow() == hasSnow;
        else if (entity instanceof MushroomCow mooshroom)
            return BovinesAndButtercups.getHelper().getMooshroomExtrasAttachment(mooshroom).hasSnow() == hasSnow;
        return false;
    }
}
