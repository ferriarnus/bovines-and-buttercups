package net.merchantpug.bovinesandbuttercups.registry;

import com.williambl.dfunc.api.DTypes;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.function.VFunctionSignature;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.Optional;

public class BovinesVFunctions {
    public static class EntityVFunctions {
        public static final VFunctionDefinition BLOCK_IN_RADIUS = new VFunctionDefinition(BovinesAndButtercups.asResource("block_in_radius").toString(),
                new VFunctionSignature(
                        Map.of("entity", DTypes.ENTITY,
                                "block_predicate", VType.createParameterised(StandardVTypes.BOOLEAN, DTypes.BLOCK_IN_WORLD),
                                "offset", StandardVTypes.OPTIONAL.with(0, DTypes.VEC3),
                                "radius", DTypes.VEC3),
                        StandardVTypes.NUMBER),
                (ctx, sig, args) -> {
                    Vec3 radius = args.get("radius").get(DTypes.VEC3);
                    Entity entity = args.get("entity").get(DTypes.ENTITY);
                    AABB box = new AABB(entity.blockPosition()).inflate(radius.x, radius.y, radius.z);
                    Optional<Vec3> offset = args.get("offset").getUnchecked();
                    offset.ifPresent(box::move);

                    int i = 0;
                    for (BlockPos pos : BlockPos.betweenClosed((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ)) {;
                        if (sig.inputTypes().get("block_predicate").accepts(new BlockInWorld(entity.level(), pos, false))) {
                            ++i;
                        }
                    }

                    return new VValue(sig.outputType(), i);
                });
    }
}
