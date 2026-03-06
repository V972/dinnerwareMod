package net.v972.dinnerware.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public class PlateBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static VoxelShape SHAPE = Block.box(3, 0, 3, 13, 2, 13);

    public final Block MATERIAL;
    public final Ingredient CRAFTING_MATERIAL;
    public final int CRAFTING_AMOUNT;

    public PlateBlock(
            @NotNull Block pMaterial,
            @NotNull Ingredient pCraftMaterial,
            int pCraftingAmount,
            Properties pProperties) {
        super(pProperties);
        this.MATERIAL = pMaterial;
        this.CRAFTING_MATERIAL = pCraftMaterial;
        this.CRAFTING_AMOUNT = pCraftingAmount;
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
    }

    @Override
    public SoundType getSoundType(BlockState pState) {
        return MATERIAL.getSoundType(pState);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return MATERIAL.getSoundType(state, level, pos, entity);
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        return MATERIAL.getMapColor(state, level, pos, defaultColor);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return  this.MATERIAL == Blocks.BEDROCK ||
                !pLevel.isEmptyBlock(pPos.below());
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
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.isClientSide) {
            pLevel.getBlockEntity(pPos, ModBlockEntities.PLATE_BLOCK_BE.get()).ifPresent((be) -> {
                be.fromItem(pStack);
            });
        } else if (pStack.hasCustomHoverName()) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof PlateBlockBlockEntity plateBlockBlockEntity) {
                plateBlockBlockEntity.setCustomName(pStack.getHoverName());
            }
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof PlateBlockBlockEntity pPlateBlockEntity &&
            !pLevel.isClientSide()) {
            pPlateBlockEntity.saveBlockItemToDrop();
        }

        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity instanceof PlateBlockBlockEntity plateBE
                ? plateBE.getItem(!plateBE.isEmpty())
                : super.getCloneItemStack(pLevel, pPos, pState);
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
            OptionalInt firstNonEmptySlotOptional = pEntity.getFirstNonEmptySlot();
            if (firstNonEmptySlotOptional.isPresent()) {
                int firstNonEmptySlot = firstNonEmptySlotOptional.getAsInt();
                ItemStack itemStack = pEntity.getStackInSlot(firstNonEmptySlot);

                if (itemStack.getFoodProperties(pPlayer) != null) {
                    if (!pPlayer.isCreative()) {
                        if ((itemStack.getItem() instanceof BowlFoodItem) ||
                            (itemStack.getItem() instanceof SuspiciousStewItem)) {

                            Containers.dropItemStack(pLevel,
                                    pPos.getX(),
                                    pPos.getY(),
                                    pPos.getZ(),
                                    new ItemStack(Items.BOWL));
                        } else {
                            if (itemStack.hasCraftingRemainingItem()) {
                                Containers.dropItemStack(pLevel,
                                        pPos.getX(),
                                        pPos.getY(),
                                        pPos.getZ(),
                                        itemStack.getCraftingRemainingItem());
                            }
                        }
                    }
                    itemStack.finishUsingItem(pLevel, pPlayer);
                    pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
                    pEntity.extractItem(firstNonEmptySlot);
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide && Config.fragilePlates) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof PlateBlockBlockEntity pPlateBlockEntity &&
                this.processBlockDrop(pLevel, pPos, pPlateBlockEntity, pEntity, true)) {
                pLevel.destroyBlock(pPos, true);
            }

            pLevel.gameEvent(pEntity, GameEvent.BLOCK_DESTROY, pPos);
        } else super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
        if (compoundtag != null && compoundtag.contains(PlateBlockBlockEntity.ITEMS_TAG)) {
            compoundtag = compoundtag.getCompound(PlateBlockBlockEntity.ITEMS_TAG);
            if (compoundtag.contains("Items", Tag.TAG_LIST)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(3, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundtag, nonnulllist);
                for(ItemStack itemstack : nonnulllist) {
                    if (itemstack.isEmpty()) continue;
                    MutableComponent mutablecomponent = itemstack.getHoverName().copy();
                    mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                    pTooltip.add(mutablecomponent);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof PlateBlockBlockEntity pPlateBlockEntity) {
            if (pPlateBlockEntity.getRemovedByPlayer()) {
                super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
                return;
            }
            if (!pLevel.isClientSide) {
                processBlockDrop(pLevel, pPos, pPlateBlockEntity, null, true, true);
                super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof PlateBlockBlockEntity pPlateBlockEntity && !level.isClientSide) {
            processBlockDrop(level, pos, pPlateBlockEntity, player, false);
            pPlateBlockEntity.setRemovedByPlayer();
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    private boolean processBlockDrop(Level pLevel, BlockPos pPos,
                                     PlateBlockBlockEntity pPlateBlockEntity,
                                     Entity pEntity, boolean pNeverKeep) {
        return processBlockDrop(pLevel, pPos, pPlateBlockEntity, pEntity, pNeverKeep, false);
    }

    private boolean processBlockDrop(Level pLevel, BlockPos pPos,
                                     PlateBlockBlockEntity pPlateBlockEntity,
                                     Entity pEntity, boolean pNeverKeep,
                                     boolean pIgnoreEntity) {
        if (!pIgnoreEntity && !(pEntity instanceof Player)) return false;
        Player pPlayer = pIgnoreEntity ? null : (Player)pEntity;

        boolean plateEmpty = pPlateBlockEntity.isEmpty();
        boolean dropBlock =
                pIgnoreEntity ||
                !pPlayer.isCreative() ||
                (!plateEmpty && pPlayer.isCrouching());
        boolean keepItems =
                !pNeverKeep && !pIgnoreEntity &&
                dropBlock && pPlayer.isCrouching();
        boolean dropItems =
                (!keepItems && !plateEmpty);

        if (dropBlock) {
            ItemStack itemstack = pIgnoreEntity
                ? pPlateBlockEntity.getBlockItemToDropOnCustomRemove()
                : pPlateBlockEntity.getItem(keepItems);
            if (pPlateBlockEntity.hasCustomName()) {
                itemstack.setHoverName(pPlateBlockEntity.getCustomName());
            }

            ItemEntity itementity = new ItemEntity(pLevel,
                    (double)pPos.getX() + 0.5D,
                    (double)pPos.getY() + 0.25D,
                    (double)pPos.getZ() + 0.5D,
                    itemstack);
            itementity.setDefaultPickUpDelay();
            pLevel.addFreshEntity(itementity);
        }
        if (dropItems) {
            pPlateBlockEntity.dropContents();
        }

        return true;
    }
}
