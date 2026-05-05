package net.v972.dinnerware.block.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.config.CommonConfig;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.util.DinnerwareHelper;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

public class PlateBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static VoxelShape SHAPE = Block.box(3, 0, 3, 13, 1.5, 13);

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
                .setValue(WATERLOGGED, Boolean.FALSE)
        );
    }

    public PlateBlock(
            @NotNull Block pMaterial,
            int pCraftingAmount,
            Properties pProperties) {
        this(
            pMaterial,
            Ingredient.of(pMaterial.asItem()),
            pCraftingAmount,
            pProperties
        );
    }

    public PlateBlock(
            @NotNull Block pMaterial,
            Properties pProperties) {
        this(
            pMaterial,
            Ingredient.of(pMaterial.asItem()),
            6,
            pProperties
        );
    }

    @Override
    public @NotNull SoundType getSoundType(@NotNull BlockState pState) {
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
    public @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
        return this.MATERIAL == Blocks.BEDROCK || !pLevel.isEmptyBlock(pPos.below());
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
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
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
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
            if (blockEntity instanceof PlateBlockBlockEntity plateEntity) {
                if(plateEntity.isDoDropContent())
                    plateEntity.dropContents();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void attack(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if(blockentity instanceof PlateBlockBlockEntity pPlateEntity &&
            pPlayer.isShiftKeyDown() &&
            !pPlateEntity.isEmpty() &&
            this.MATERIAL != Blocks.BEDROCK
        ) {
            pickUpPlate(pPlateEntity, pPlayer, pLevel, pPos);
        }

        super.attack(pState, pLevel, pPos, pPlayer);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.isClientSide) {
            pLevel.getBlockEntity(pPos, ModBlockEntities.PLATE_BLOCK_BE.get()).ifPresent((be) -> {
                be.fromItem(pStack);
            });
        } else if (pStack.hasCustomHoverName()) {
            pLevel.getBlockEntity(pPos, ModBlockEntities.PLATE_BLOCK_BE.get()).ifPresent((be) -> {
                be.setCustomName(pStack.getHoverName());
            });
        }
    }

    @Override
    public void entityInside(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (pLevel.isClientSide || !CommonConfig.FRAGILE_PLATES.get()) return;
        if (pEntity.getType().is(ModTags.Entities.FRAGILE_PLATE_IGNORED)) return;

        AABB plateBox = pState.getShape(pLevel, pPos).bounds().move(pPos);
        plateBox = plateBox.inflate(0.05, 0.1, 0.05); // Expand slightly

        // only break if entity is actually touching from above or inside
        boolean steppedOn =
            pEntity.getY() <= pPos.getY() + 0.2 &&
            pEntity.getDeltaMovement().y <= 0;

        if (steppedOn && pEntity.getBoundingBox().intersects(plateBox)) {
            pLevel.destroyBlock(pPos, true);
            pLevel.gameEvent(pEntity, GameEvent.BLOCK_DESTROY, pPos);
        }
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
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //if (!pLevel.isClientSide()) {
            BlockEntity bEntity = pLevel.getBlockEntity(pPos);
            if (bEntity instanceof PlateBlockBlockEntity pPlateEntity) {
                ItemStack itemStackInHand = pPlayer.getItemInHand(pHand);
                if (itemStackInHand.is(ModTags.Items.TRAYS)) {
                    return pLevel.isClientSide()
                        ? pPlayer.getCooldowns().isOnCooldown(itemStackInHand.getItem())
                            ? InteractionResult.sidedSuccess(pLevel.isClientSide())
                            : InteractionResult.PASS
                        : super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);

                // open menu when shift-clicking or clicking empty
                } else if ((pPlayer.isCrouching() ||
                           pPlateEntity.isEmpty())) {
                    if (!pLevel.isClientSide()) {
                        NetworkHooks.openScreen(
                                (ServerPlayer) pPlayer,
                                pPlateEntity,
                                pPos);
                    }
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                } else return attemptEat(pLevel, pPos, pHit, pPlateEntity, pPlayer);
            }

            return InteractionResult.FAIL;
        //}

        //return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private InteractionResult pickUpPlate(PlateBlockBlockEntity pPlateEntity, Player pPlayer, Level pLevel, BlockPos pPos) {
        ItemStack selectedItemStack = pPlayer.getInventory().getSelected();

        boolean isTray = selectedItemStack.is(ModTags.Items.TRAYS);
        boolean didAddToTray = false;

        // with empty hand
        if (selectedItemStack.isEmpty()) {
            // place in hand
            if (!pPlayer.getInventory().add(pPlayer.getInventory().selected, pPlateEntity.getItem()))
                return InteractionResult.FAIL;

        // with not empty hand, but specifically not tray
        } else if (!isTray) {
            // place in first available slot according to vanilla MC logic
            pPlayer.getInventory().placeItemBackInInventory(pPlateEntity.getItem());

        // with tray, try to add
        } else {
            didAddToTray = TrayItem.addPlateToTray(selectedItemStack, pPlateEntity.getItem());
        }

        // and if no luck
        if (isTray && !didAddToTray) {
            // cancel the pickup
            return InteractionResult.FAIL;
        }

        // handle block removal on successful pickup
        pPlateEntity.doNotDropContent();
        pLevel.removeBlock(pPos, false);

        pPlayer.awardStat(Stats.BLOCK_MINED.get(this));
        for (Block plateType : ModBlocks.getKnownBlocks()) {
            pPlayer.getCooldowns().addCooldown(plateType.asItem(), 5);
        }
        if (didAddToTray) {
            for (TrayItem item : ModItems.getTrayItemsArray()) {
                pPlayer.getCooldowns().addCooldown(item, 5);
            }
            TrayItem.checkAndAwardTheOneTrayAdvancement(selectedItemStack, pPlayer);
            TrayItem.playInsertSound(pPlayer, false);
        }
        else {
            pPlayer.playSound(SoundEvents.ITEM_PICKUP,
                    0.8F, 0.8F + pLevel.getRandom().nextFloat() * 0.4F);
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private InteractionResult attemptEat(Level pLevel, BlockPos pPos, BlockHitResult pHit, PlateBlockBlockEntity pEntity, Player pPlayer) {
        int slotToEat = switch (CommonConfig.EATING_MODE.get()) {
            case QUEUE -> getSlotToEatQueue(pEntity);
            case ROUND_ROBIN -> getSlotToEatRoundRobin(pEntity);
            case AIMING -> getSlotToEatAim(pHit, pEntity, pLevel.getBlockState(pPos)
                    //, pPlayer
            );
        };

        ///////////////////////////////////
        // net.minecraft.world.phys.Vec3 loc = pHit.getLocation();
        // double x_fr = fraction3Places(loc.x);
        // double z_fr = fraction3Places(loc.z);
        // pPlayer.sendSystemMessage(Component.literal("slot " + slotToEat + " | x: " + x_fr + ", z: " + z_fr));
        // slotToEat = -1;
        ///////////////////////////////////

        if (slotToEat == -1) return InteractionResult.PASS;

        ItemStack itemStack = pEntity.getStackInSlot(slotToEat);
        if (itemStack.isEmpty() || itemStack == ItemStack.EMPTY) return InteractionResult.PASS;

        boolean isFood = itemStack.getFoodProperties(pPlayer) != null;
        boolean isFoodAndCanAlwaysBeEaten = isFood && Objects.requireNonNull(itemStack.getFoodProperties(pPlayer)).canAlwaysEat();

        if (isFoodAndCanAlwaysBeEaten ||
                (pPlayer.canEat(CommonConfig.ALLOW_OVEREATING.get()) && isFood)
        ) {

            if (CommonConfig.isInFoodBlacklist(itemStack.getItem())) {
                pPlayer.displayClientMessage(
                    Component.translatable("block.dinnerware.plate.food_blacklist_message")
                        .withStyle(ChatFormatting.RED), true);

                return InteractionResult.PASS;
            }

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

            itemStack.finishUsingItem(pLevel, pPlayer);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            pEntity.eatFromSlot(slotToEat);

            return InteractionResult.SUCCESS;
        }

        if (CommonConfig.EATING_MODE.get() == CommonConfig.EATING_MODES.ROUND_ROBIN)
            pEntity.eatingAttemptClick();

        return InteractionResult.PASS;
    }

    private int getSlotToEatQueue(PlateBlockBlockEntity pEntity) {
        OptionalInt firstNonEmptySlotOptional = pEntity.getFirstNonEmptySlot(true);
        if (firstNonEmptySlotOptional.isEmpty())
            return -1;
        return firstNonEmptySlotOptional.getAsInt();
    }

    private int getSlotToEatRoundRobin(PlateBlockBlockEntity pEntity) {
        return pEntity.getRoundRobinCurrentEatingSlot();
    }

    private int getSlotToEatAim(BlockHitResult pHit, PlateBlockBlockEntity pEntity, BlockState pBlockState
            //, Player pPlayer
    ) {
        int slotsPositions = pEntity.getSlotStacksPositions();
        Direction plateDir = pBlockState.getValue(HorizontalDirectionalBlock.FACING);

        if (pHit.getDirection() == Direction.UP) {
            Pair<Double, Double> blockHitLoc = DinnerwareHelper.getBlockHitPos(pHit, plateDir);
            double hor = blockHitLoc.getFirst();
            double vert = blockHitLoc.getSecond();

            ///////////////////////////////////
            //pPlayer.sendSystemMessage(Component.literal("\nhor: " + hor + ", vert: " + vert));
            ///////////////////////////////////

            //
            // 0.26f--˅-------------˅ horizontal gap from plate edge
            //       ├┴────────────┴┤_
            //       │              │  <-- 0.57(1)f-0.78f extra dish height
            //       │              ├-
            //       │              │
            //       │              │_
            //       └─────────┬────┼- <-- 0.24f (+0.08f when no extra dish) vertical gap from plate edge
            //                    ^
            //                    └-- 0.2f side dish length
            //

            switch (slotsPositions) {
                case 1:
                    // center
                    if (!isInRange(hor, 0.32, 0.68) || !isInRange(vert, 0.32, 0.68)) return -1;
                    OptionalInt slot = pEntity.getFirstNonEmptySlot(false);
                    return slot.isPresent() ? slot.getAsInt() : -1;
                case 2:
                    if (CommonConfig.RIGHT_TO_LEFT.get()) {
                        // main dish slot
                        if (isInRange(hor, 0.461, 0.74) && isInRange(vert, 0.32, 0.65)) return 0;
                        // side dish slot
                        if (isInRange(hor, 0.26, 0.46) && isInRange(vert, 0.32, 0.65)) return 1;
                    } else {
                        // main dish slot
                        if (isInRange(hor, 0.26, 0.54) && isInRange(vert, 0.32, 0.65)) return 0;
                        // side dish slot
                        if (isInRange(hor, 0.541, 0.74) && isInRange(vert, 0.32, 0.65)) return 1;
                    }
                    return -1;
                case 3:
                    if (CommonConfig.RIGHT_TO_LEFT.get()) {
                        // main dish slot
                        if (isInRange(hor, 0.461, 0.74) && isInRange(vert, 0.24, 0.57)) return 0;
                        // side dish slot
                        if (isInRange(hor, 0.26, 0.46) && isInRange(vert, 0.24, 0.57)) return 1;
                    } else {
                        // main dish slot
                        if (isInRange(hor, 0.26, 0.54) && isInRange(vert, 0.24, 0.57)) return 0;
                        // side dish slot
                        if (isInRange(hor, 0.541, 0.74) && isInRange(vert, 0.24, 0.57)) return 1;
                    }
                    // extra dish slot
                    if (isInRange(hor, 0.26, 0.74) && isInRange(vert, 0.571, 0.78)) return 2;
                    return -1;
                case 4:
                    // main dish slot
                    if (isInRange(hor, 0.32, 0.68) && isInRange(vert, 0.32, 0.57)) return 0;
                    // extra dish slot
                    if (isInRange(hor, 0.26, 0.74) && isInRange(vert, 0.571, 0.78)) return 2;
                    return -1;
                case 5:
                    // side dish slot
                    if (isInRange(hor, 0.32, 0.68) && isInRange(vert, 0.32, 0.57)) return 1;
                    // extra dish slot
                    if (isInRange(hor, 0.26, 0.74) && isInRange(vert, 0.571, 0.78)) return 2;
                    return -1;
                default: return -1;
            }
        }

        return -1;
    }

    private double fraction3Places(double a) { return (double) Math.round((a % 1) * 1000) / 1000; }

    private boolean isInRange(double x, double min, double max) { return x >= min && x <= max; }
}
