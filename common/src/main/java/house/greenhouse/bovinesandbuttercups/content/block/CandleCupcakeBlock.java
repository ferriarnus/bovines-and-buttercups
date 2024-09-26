package house.greenhouse.bovinesandbuttercups.content.block;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.BovinesTags;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CandleCupcakeBlock extends AbstractCandleBlock {
    public static final MapCodec<CandleCupcakeBlock> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(CandleCupcakeBlock::base),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(CandleCupcakeBlock::candle),
            propertiesCodec()
    ).apply(inst, CandleCupcakeBlock::new));

    public static final IntegerProperty COUNT = CupcakeBlock.COUNT;
    public static final IntegerProperty CANDLES = CandleBlock.CANDLES;
    private static final Map<Pair<Integer, Integer>, List<Vec3>> PARTICLE_OFFSETS = Util.make(() -> {
        Object2ObjectOpenHashMap<Pair<Integer, Integer>, List<Vec3>> map = new Object2ObjectOpenHashMap<>();
        map.defaultReturnValue(ImmutableList.of());

        var one  = ImmutableList.of(new Vec3(0.5, 0.6875, 0.5));

        map.put(Pair.of(1, 1), one);
        map.put(Pair.of(1, 2), one);
        map.put(Pair.of(1, 3), one);
        map.put(Pair.of(1, 4), one);

        var twoOneCandle  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.5));
        var twoTwoCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.5), new Vec3(0.25, 0.625, 0.5));

        map.put(Pair.of(2, 1), twoOneCandle);
        map.put(Pair.of(2, 2), twoTwoCandles);
        map.put(Pair.of(2, 3), twoTwoCandles);
        map.put(Pair.of(2, 4), twoTwoCandles);

        var threeOneCandle  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25));
        var threeTwoCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25), new Vec3(0.25, 0.625, 0.25));
        var threeThreeCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25), new Vec3(0.25, 0.625, 0.25), new Vec3(0.5, 0.75, 0.75));

        map.put(Pair.of(3, 1), threeOneCandle);
        map.put(Pair.of(3, 2), threeTwoCandles);
        map.put(Pair.of(3, 3), threeThreeCandles);
        map.put(Pair.of(3, 4), threeThreeCandles);

        var fourOneCandle  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25));
        var fourTwoCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25), new Vec3(0.25, 0.625, 0.25));
        var fourThreeCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25), new Vec3(0.25, 0.625, 0.25), new Vec3(0.75, 0.75, 0.75));
        var fourFourCandles  = ImmutableList.of(new Vec3(0.75, 0.6875, 0.25), new Vec3(0.25, 0.625, 0.25), new Vec3(0.75, 0.75, 0.75), new Vec3(0.25, 0.6875, 0.75));

        map.put(Pair.of(4, 1), fourOneCandle);
        map.put(Pair.of(4, 2), fourTwoCandles);
        map.put(Pair.of(4, 3), fourThreeCandles);
        map.put(Pair.of(4, 4), fourFourCandles);

        return Map.copyOf(map);
    });
    protected static final Map<Pair<Integer, Integer>, VoxelShape> SHAPE_BY_COUNT = Util.make(() -> {
        Object2ObjectOpenHashMap<Pair<Integer, Integer>, VoxelShape> map = new Object2ObjectOpenHashMap<>();
        map.defaultReturnValue(Shapes.empty());

        var one = Shapes.or(Block.box(5.0, 0.0, 5.0, 11.0, 5.0, 11.0), Block.box(7.0, 5.0, 7.0, 9.0, 9.0, 9.0));

        map.put(Pair.of(1, 1), one);
        map.put(Pair.of(1, 2), one);
        map.put(Pair.of(1, 3), one);
        map.put(Pair.of(1, 4), one);

        var twoOneCandle = Shapes.or(Block.box(1.0, 0.0, 5.0, 15.0, 5.0, 11.0), Block.box(11.0, 5.0, 7.0, 13.0, 9.0, 9.0));
        var twoTwoCandles = Shapes.or(twoOneCandle, Block.box(3.0, 5.0, 7.0, 5.0, 8.0, 9.0));

        map.put(Pair.of(2, 1), twoOneCandle);
        map.put(Pair.of(2, 2), twoTwoCandles);
        map.put(Pair.of(2, 3), twoTwoCandles);
        map.put(Pair.of(2, 4), twoTwoCandles);

        var threeOneCandle = Shapes.or(Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0), Block.box(11.0, 5.0, 3.0, 13.0, 9.0, 5.0));
        var threeTwoCandles = Shapes.or(threeOneCandle, Block.box(3.0, 5.0, 3.0, 5.0, 8.0, 5.0));
        var threeThreeCandles = Shapes.or(threeTwoCandles, Block.box(7.0, 5.0, 11.0, 9.0, 10.0, 13.0));

        map.put(Pair.of(3, 1), threeOneCandle);
        map.put(Pair.of(3, 2), threeTwoCandles);
        map.put(Pair.of(3, 3), threeThreeCandles);
        map.put(Pair.of(3, 4), threeThreeCandles);

        var fourOneCandle = Shapes.or(Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0), Block.box(11.0, 5.0, 3.0, 13.0, 9.0, 5.0));
        var fourTwoCandles = Shapes.or(fourOneCandle, Block.box(3.0, 5.0, 3.0, 5.0, 8.0, 5.0));
        var fourThreeCandles = Shapes.or(fourTwoCandles, Block.box(11.0, 5.0, 11.0, 13.0, 10.0, 13.0));
        var fourFourCandles = Shapes.or(fourThreeCandles, Block.box(11.0, 5.0, 3.0, 13.0, 9.0, 5.0));

        map.put(Pair.of(4, 1), fourOneCandle);
        map.put(Pair.of(4, 2), fourTwoCandles);
        map.put(Pair.of(4, 3), fourThreeCandles);
        map.put(Pair.of(4, 4), fourFourCandles);

        return Map.copyOf(map);
    });
    private static final Map<CupcakeBlock, Map<CandleBlock, CandleCupcakeBlock>> BY_CANDLE = new Reference2ObjectOpenHashMap<>();
    private final CupcakeBlock base;
    private final CandleBlock candle;

    @Override
    protected MapCodec<CandleCupcakeBlock> codec() {
        return CODEC;
    }

    public CandleCupcakeBlock(Block base, Block candle, Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(COUNT, 1).setValue(CANDLES, 1).setValue(LIT, false));
        if (!(base instanceof CupcakeBlock cupcakeBlock))
            throw new IllegalArgumentException("Expected base block to be of " + CupcakeBlock.class + " was " + base.getClass());
        if (!(candle instanceof CandleBlock candleBlock))
            throw new IllegalArgumentException("Expected candle block to be of " + CandleBlock.class + " was " + candle.getClass());
        this.base = cupcakeBlock;
        this.candle = candleBlock;
        BY_CANDLE.compute(cupcakeBlock, (cb, map) -> {
            if (map == null)
                map = new Reference2ObjectOpenHashMap<>();
            map.put(candleBlock, this);
            return map;
        });
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_COUNT.get(Pair.of(state.getValue(COUNT), state.getValue(CANDLES)));
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS.get(Pair.of(state.getValue(COUNT), state.getValue(CANDLES)));
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (stack.is(base.asItem()) && state.getValue(COUNT) < 4) {
            stack.consume(1, player);
            level.setBlock(pos, state.setValue(COUNT, state.getValue(COUNT) + 1), 3);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.awardStat(Stats.ITEM_USED.get(candle.asItem()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (stack.is(candle.asItem())) {
            if (state.getValue(CANDLES) >= state.getValue(COUNT))
                return ItemInteractionResult.CONSUME;
            stack.consume(1, player);
            level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, state.setValue(CANDLES, state.getValue(CANDLES) + 1), 3);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.awardStat(Stats.ITEM_USED.get(candle.asItem()));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE))
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;

        if (candleHit(hitResult) && stack.isEmpty() && state.getValue(LIT)) {
            extinguish(player, state, level, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private static boolean candleHit(BlockHitResult hit) {
        return hit.getLocation().y - (double)hit.getBlockPos().getY() > 0.3125;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        InteractionResult interactionresult = CupcakeBlock.eat(level, pos, state, player);
        if (!level.isClientSide() && interactionresult.consumesAction() && (level.getBlockState(pos).isAir() || level.getBlockState(pos).is(BovinesTags.BlockTags.CANDLE_CUPCAKES, base -> base.getValue(COUNT) < base.getValue(CANDLES)))) {
            if (level.getBlockState(pos).is(BovinesTags.BlockTags.CANDLE_CUPCAKES))
                level.setBlock(pos, level.getBlockState(pos).setValue(CANDLES, state.getValue(CANDLES) - 1), 3);
            popResource(level, pos, new ItemStack(candle));
        }

        return interactionresult;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COUNT);
        builder.add(CANDLES);
        builder.add(LIT);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(base);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return Math.min(CupcakeBlock.MAX_CUPCAKES - blockState.getValue(COUNT) * 4, 15);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType computationType) {
        return false;
    }

    public CupcakeBlock base() {
        return base;
    }

    public CandleBlock candle() {
        return candle;
    }

    public static BlockState from(CupcakeBlock base, CandleBlock candle, int count) {
        if (!BY_CANDLE.getOrDefault(base, Map.of()).containsKey(candle)) {
            candle = (CandleBlock) Blocks.CANDLE;
            BovinesAndButtercups.LOG.error("Could not get candle cupcake from candle block '{}'.", candle.builtInRegistryHolder().getRegisteredName());
        }
        return BY_CANDLE.getOrDefault(base, Map.of()).get(candle).defaultBlockState().setValue(COUNT, count);
    }

    public static void forEachCandleVariant(CupcakeBlock base, Consumer<CandleCupcakeBlock> consumer) {
        BY_CANDLE.getOrDefault(base, Map.of()).values().forEach(consumer);
    }

    public static boolean canLight(BlockState state) {
        return state.is(BovinesTags.BlockTags.CANDLE_CUPCAKES, base -> base.hasProperty(LIT) && !state.getValue(LIT));
    }
}
