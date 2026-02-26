package net.v972.dinnerware.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class PlateBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static VoxelShape SHAPE = Block.box(3, 0, 3, 13, 2, 13);

    public PlateBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return !pLevel.isEmptyBlock(pPos.below());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        return defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlateBlockBlockEntity(pPos, pState);
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PlateBlockBlockEntity) {
                Containers.dropContents(pLevel, pPos, (Container)blockEntity);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity bEntity = pLevel.getBlockEntity(pPos);
            if (bEntity instanceof PlateBlockBlockEntity pPlateEntity) {
                if (pPlayer.isCrouching() ||
                    pPlateEntity.isEmpty()
                ) {
                    NetworkHooks.openScreen(
                            (ServerPlayer)pPlayer,
                            pPlateEntity,
                            pPos);

                } else return attemptEat(pLevel, pPos, pPlateEntity, pPlayer);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private InteractionResult attemptEat(Level pLevel, BlockPos pPos, PlateBlockBlockEntity pEntity, Player pPlayer) {
        if (!pPlayer.canEat(Config.allowOverEating)) {
            return InteractionResult.PASS;
        } else {
            NonNullList<ItemStack> items = pEntity.getRenderStacks();
            OptionalInt firstNonEmptySlotOptional = IntStream.range(0, items.size())
                    .filter(i -> !items.get(i).isEmpty())
                    .findFirst();

            if (firstNonEmptySlotOptional.isPresent()) {
                int firstNonEmptySlot = firstNonEmptySlotOptional.getAsInt();
                ItemStack itemStack = items.get(firstNonEmptySlot);

                if (itemStack.getFoodProperties(pPlayer) != null) {
                    itemStack.finishUsingItem(pLevel, pPlayer);
                    pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
                    pEntity.removeItem(firstNonEmptySlot);
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (Config.fragilePlates && !pLevel.isClientSide()) {
            if (pEntity instanceof Player player) {
                player.sendSystemMessage(Component.literal("stepped on plate"));
            }
            // deserialize food ???
            // drop food if any ???
            // destroy
        }
    }
}
