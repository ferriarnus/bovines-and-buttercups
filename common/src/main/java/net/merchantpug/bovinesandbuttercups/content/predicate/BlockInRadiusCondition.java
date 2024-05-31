package net.merchantpug.bovinesandbuttercups.content.predicate;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.api.codec.BovinesCodecs;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.merchantpug.bovinesandbuttercups.registry.BovinesLootContextParamSets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record BlockInRadiusCondition(Either<Holder<Block>, BlockState> blockOrState, AABB radius) implements LootItemCondition {
    public static final MapCodec<BlockInRadiusCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.either(BuiltInRegistries.BLOCK.holderByNameCodec(), BlockState.CODEC).fieldOf("block").forGetter(BlockInRadiusCondition::blockOrState),
            BovinesCodecs.AABB.fieldOf("radius").forGetter(BlockInRadiusCondition::radius)
    ).apply(inst, BlockInRadiusCondition::new));
    public static final LootItemConditionType TYPE = new LootItemConditionType(CODEC);

    @Override
    public boolean test(LootContext context) {
        Vec3 origin = context.getParam(LootContextParams.ORIGIN);
        AABB aabb = radius.move(origin);
        return BlockPos.betweenClosedStream(aabb).anyMatch(pos -> {
            BlockState state = context.getLevel().getBlockState(pos);
            boolean bl = blockOrState.map(state::is, blockState -> blockState.getProperties().equals(state.getProperties()));
            if (bl && context.hasParam(BovinesLootContextParamSets.CHILD)) {
                Entity child = context.getParam(BovinesLootContextParamSets.CHILD);
                if (child instanceof Moobloom moobloom)
                    moobloom.addParticlePosition(state.getCollisionShape(context.getLevel(), pos).bounds().getCenter());
            }
            return bl;
        });
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    public static class Builder implements LootItemCondition.Builder {
        private Either<Holder<Block>, BlockState> block;
        private AABB radius = AABB.ofSize(Vec3.ZERO, 0, 0, 0);
        private Optional<Vec3> offset = Optional.empty();

        public Builder(Either<Holder<Block>, BlockState> block) {
            this.block = block;
        }

        public Builder(Holder<Block> block) {
            this.block = Either.left(block);
        }

        public Builder(BlockState blockState) {
            this.block = Either.right(blockState);
        }

        public Builder withRadius(double xz, double y) {
            radius = AABB.ofSize(Vec3.ZERO, xz, y, xz);
            return this;
        }

        public Builder withOffset(double x, double y, double z) {
            radius = radius.move(x, y, z);
            return this;
        }

        @Override
        public LootItemCondition build() {
            return new BlockInRadiusCondition(block, radius);
        }
    }
}
