package net.frosted.aquarious.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class Starfish extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<Starfish> CODEC = createCodec(Starfish::new);
    public static final EnumProperty<BlockFace> FACE = Properties.BLOCK_FACE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 2, 13);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(3, 14, 3, 13, 16, 13);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 3, 3, 2, 13, 13);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(14, 3, 3, 16, 13, 13);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(3, 3, 0, 13, 13, 2);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3, 3, 14, 13, 13, 16);

    public Starfish(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends Starfish> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACE)) {
            case CEILING -> DOWN_SHAPE;
            case FLOOR -> UP_SHAPE;
            case WALL -> switch (state.get(FACING)){
                case DOWN -> UP_SHAPE;
                case UP -> DOWN_SHAPE;
                case NORTH -> NORTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case WEST -> WEST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
            };
        };
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, getDirection(state).getOpposite());
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;

        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.getDefaultState()
                        .with(FACE, direction == Direction.UP ? BlockFace.CEILING : BlockFace.FLOOR)
                        .with(FACING, ctx.getHorizontalPlayerFacing());
            } else {
                blockState = this.getDefaultState().with(FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState.with(WATERLOGGED, bl);
            }
        }

        return null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView,
            BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random
    ) {
        return getDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected static Direction getDirection(BlockState state) {
        switch ((BlockFace)state.get(FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return state.get(FACING);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, WATERLOGGED);
    }


}
