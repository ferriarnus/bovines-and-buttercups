package house.greenhouse.bovinesandbuttercups.content.block;

import com.mojang.serialization.MapCodec;
import house.greenhouse.bovinesandbuttercups.content.block.entity.CustomMushroomPotBlockEntity;
import house.greenhouse.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CustomMushroomPotBlock extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    private static final MapCodec<CustomMushroomPotBlock> CODEC = simpleCodec(CustomMushroomPotBlock::new);

    public CustomMushroomPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack handStack = player.getItemInHand(hand);
        boolean isAir = blockState.is(Blocks.AIR);
        if (isAir) {
            ItemStack flowerStack = this.getContent(level, blockPos);
            if (handStack.isEmpty())
                player.setItemInHand(hand, flowerStack);
            else if (!player.addItem(flowerStack))
                player.drop(flowerStack, false);

            level.setBlock(blockPos, Blocks.FLOWER_POT.defaultBlockState(), 3);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, blockState, level, blockPos, player, hand, hitResult);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return this.getContent(level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    public ItemStack getContent(BlockGetter level, BlockPos pos) {
        ItemStack stack = new ItemStack(BovinesItems.CUSTOM_MUSHROOM);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof CustomMushroomPotBlockEntity cmpbe)
            stack.set(BovinesDataComponents.CUSTOM_MUSHROOM, cmpbe.getMushroomType());
        return stack;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BovinesBlockEntityTypes.POTTED_CUSTOM_MUSHROOM.create(pos, state);
    }
}
