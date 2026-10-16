package net.v972.dinnerware.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
//import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.block.DinnerwareBlocks;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.item.DinnerwareItems;
import net.v972.dinnerware.item.context.DinnerwareUseOnContext;
import net.v972.dinnerware.platform.PlatformHooks;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrayItem extends Item {
    private static final String TAG_ITEMS = "Items";
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public final Block MATERIAL;

    public TrayItem(
            @NotNull Block pMaterial,
            Properties pProperties) {
        super(pProperties);
        this.MATERIAL = pMaterial;
    }

//    @Override
//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
//        ItemStack trayStack = pPlayer.getItemInHand(pUsedHand);
//        if (!trayStack.is(this)) return super.use(pLevel, pPlayer, pUsedHand);
//
//        if (pPlayer.isSecondaryUseActive()) {
//              if (data.cycle()) {
//                  playInsertSound(pPlayer);
//              }
//            return InteractionResultHolder.sidedSuccess(trayStack, pLevel.isClientSide);
//        } else {
//            //same as startUsingItem but client only so it does not slow
//            if (pLevel.isClientSide) {
//                ForgeTrayItemHud.getInstance().setUsingItem(SlotReference.hand(pUsedHand), pPlayer);
//            }
//            playRemoveOneSound(pPlayer);
//            pPlayer.startUsingItem(pUsedHand);
//            return InteractionResultHolder.consume(trayStack);
//        }
//    }
//
//    @Override
//    public int getUseDuration(ItemStack stack) {
//        return 72000;
//    }
//
//    @Override
//    public void releaseUsing(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
//        if (pLevel.isClientSide) {
//            //ForgeTrayItemHud.getInstance().setUsingItem(SlotReference.EMPTY, pLivingEntity);
//        }
//        playInsertSound(pLivingEntity);
//        pLivingEntity.swing(pLivingEntity.getUsedItemHand());
//        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
//    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level contextLevel = pContext.getLevel();
        //if (!contextLevel.isClientSide()) {
            BlockPos pos = pContext.getClickedPos();
            Player contextPlayer = pContext.getPlayer();

            if (contextPlayer == null) {
                return InteractionResult.FAIL;
            }
            ItemStack trayStack = contextPlayer.getItemInHand(pContext.getHand());

            Block clickedBlock = contextLevel.getBlockState(pos).getBlock();
            if (Arrays.asList(DinnerwareBlocks.getKnownPlateBlocksArray()).contains(clickedBlock)) {

                if (((PlateBlock)clickedBlock).MATERIAL == Blocks.BEDROCK && !contextPlayer.isCreative()) {
                    return InteractionResult.FAIL;
                }

                PlateBlockBlockEntity plateBE = (PlateBlockBlockEntity)contextLevel.getBlockEntity(pos);
                ItemStack plateStack = plateBE != null ? plateBE.getItem() : ItemStack.EMPTY;
                if (plateBE != null && add(trayStack, plateStack) > 0) {
                    plateBE.doNotDropContent();
                    contextLevel.removeBlock(pos, false);
                    playInsertSound(contextPlayer, false);
                    contextPlayer.awardStat(Stats.ITEM_USED.get(this));
                    contextPlayer.getCooldowns().addCooldown(this, 5); // 1/4th of a sec
                    checkAndAwardTheOneTrayAdvancement(trayStack, contextPlayer);

                    return InteractionResult.sidedSuccess(contextLevel.isClientSide());
                }
            } else {
                InteractionResult result = InteractionResult.PASS;
                Optional<ItemStack> peekedResult = peekTop(trayStack, false);

                if (peekedResult.isPresent()) {
                    PlateBlockBlockItem plateBlockBlockItem = (PlateBlockBlockItem)peekedResult.get().getItem();
                    PlateBlock plateBlock = (PlateBlock)plateBlockBlockItem.getBlock();

                    boolean canSurvive = plateBlock.canSurvive(
                        plateBlock.defaultBlockState(), contextLevel, pos.relative(pContext.getClickedFace())
                    );
                    BlockPlaceContext plateContext =
                        new BlockPlaceContext(
                            new DinnerwareUseOnContext(
                                contextLevel,
                                contextPlayer,
                                pContext.getHand(),
                                peekedResult.get(),
                                new BlockHitResult(
                                    pContext.getClickLocation(),
                                    pContext.getClickedFace(),
                                    pContext.getClickedPos(),
                                    pContext.isInside()
                                )
                            )
                        );
                    if (canSurvive && plateContext.canPlace() &&
                        !isEntityInBlock(contextLevel, pos.relative(pContext.getClickedFace()))) {

                        playRemoveOneSound(contextPlayer, false);
                        removeOne(trayStack, false);
                        plateBlockBlockItem.place(plateContext);
                        contextPlayer.awardStat(Stats.ITEM_USED.get(this));

                        result = InteractionResult.sidedSuccess(contextLevel.isClientSide());
                    }
                }

                return result;
            }
        //}

        return InteractionResult.FAIL;
    }

    private boolean isEntityInBlock(Level level, BlockPos pos) {
        AABB box = PlateBlock.SHAPE.bounds().move(pos).inflate(0, 0.15, 0);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, box);

        // Return true if the list is not empty
        // or any of the entities cannot coexist with block
        if (entities.isEmpty()) return false;
        else return !entities.stream().allMatch(entity ->
                entity instanceof HangingEntity);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    private static boolean canLoadItem(ItemStack pStack) {
        //
        // TODO: Add Farmer's Delight Compat for Feasts
        //
        return pStack.is(ModTags.Items.PLATES);
    }

    public static boolean addPlateToTray(ItemStack pTrayStack, ItemStack pPlateStack) {
        if (pTrayStack.is(ModTags.Items.TRAYS) && pPlateStack.is(ModTags.Items.PLATES)) {
            return add(pTrayStack, pPlateStack) > 0;
        }
        return false;
    }

    // ===== Bundle-"Inspired" =====

    public boolean overrideStackedOnOther(ItemStack pStack, @NotNull Slot pSlot, @NotNull ClickAction pAction, @NotNull Player pPlayer) {
        // when clicking with tray on plate/slot

        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack itemstack = pSlot.getItem();
        if (itemstack.isEmpty()) {
            playRemoveOneSound(pPlayer, true);
            removeOne(pStack, true).ifPresent((itemStack) ->
                add(pStack, pSlot.safeInsert(itemStack)));
        } else if (
            itemstack.getItem().canFitInsideContainerItems() &&
            canLoadItem(itemstack)
        ) {
            int i = (64 - getContentWeight(pStack)) / getWeight(itemstack);
            int j = add(pStack, pSlot.safeTake(itemstack.getCount(), i, pPlayer));
            if (j > 0) {
                playInsertSound(pPlayer, true);
                // check for and award One Tray advancement
                PlatformHooks.requestTrayAdvancementCheck(
                    pPlayer, TrayAdvancementCheckSource.CURSOR, pStack
                );
            }
        }

        return true;
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, @NotNull ItemStack pOther, @NotNull Slot pSlot, @NotNull ClickAction pAction, @NotNull Player pPlayer, @NotNull SlotAccess pAccess) {
        // when clicking with plate on tray

        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                removeOne(pStack, true).ifPresent((itemStack) -> {
                    playRemoveOneSound(pPlayer, true);
                    pAccess.set(itemStack);
                });
            } else {
                int leftOver = add(pStack, pOther);
                if (leftOver > 0) {
                    playInsertSound(pPlayer, true);
                    pOther.shrink(leftOver);
                    // check for and award One Tray advancement
                    PlatformHooks.requestTrayAdvancementCheck(
                        pPlayer, TrayAdvancementCheckSource.MENU_SLOT, ItemStack.EMPTY);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    // -----------------------------

    public static float getFullnessDisplay(ItemStack pStack) {
        return (float)getContentWeight(pStack) / 64f;
    }

    public boolean isBarVisible(@NotNull ItemStack pStack) {
        return getContentWeight(pStack) > 0;
    }

    public int getBarWidth(@NotNull ItemStack pStack) {
        return Math.min(1 + 12 * getContentWeight(pStack) / 64, 13);
    }

    public int getBarColor(@NotNull ItemStack pStack) {
        return BAR_COLOR;
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity pItemEntity) {
        ItemContainerContents contents = pItemEntity.getItem()
                .getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        ItemUtils.onContainerDestroyed(pItemEntity, contents.nonEmptyItems());
    }

    public void appendHoverText(@NotNull ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        String maxFullness = "64";
        pTooltipComponents.add(
            Component.translatable("container.dinnerware.tray.fullness",
                getContentWeight(pStack), maxFullness)
                .withStyle(ChatFormatting.GRAY));

        List<ItemStack> contents = getContentList(pStack);

        int parsedItems;
        int itemsSize = contents.size();
        int maxLines = DinnerwareConfig.maxTrayTooltipLines();

        for (parsedItems = 0; parsedItems < Math.min(itemsSize, maxLines); parsedItems++) {
            ItemStack itemstack = contents.get(parsedItems);
            MutableComponent mutablecomponent = itemstack.getHoverName().copy();

            boolean withFood = false;

            CustomData blockEntityData = itemstack.get(DataComponents.BLOCK_ENTITY_DATA);
            if (blockEntityData != null) {
                CompoundTag tag = blockEntityData.copyTag();

                if (tag.contains(PlateBlockBlockEntity.ITEMS_TAG, Tag.TAG_COMPOUND)) {
                    CompoundTag inventoryTag = tag.getCompound(PlateBlockBlockEntity.ITEMS_TAG);

                    if (inventoryTag.contains("Items", Tag.TAG_LIST)) {
                        withFood = !inventoryTag
                            .getList("Items", Tag.TAG_COMPOUND)
                            .isEmpty();
                    }
                }
            }

            if (withFood) {
                mutablecomponent = Component.translatable(
                    "container.dinnerware.tray.with_food",
                    mutablecomponent
                );
            }

            if (itemstack.getCount() > 1) {
                mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
            }

            pTooltipComponents.add(mutablecomponent);
        }

        if (itemsSize - parsedItems > 0) {
            pTooltipComponents.add(
                    Component.translatable(
                            "container.dinnerware.tray.more",
                            itemsSize - parsedItems
                    ).withStyle(ChatFormatting.ITALIC)
            );
        }
    }

    public static void playRemoveOneSound(Entity pEntity, boolean pInInventory) {
        if (pInInventory) {
            pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, //trayMetalUnload(),
                0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
        }
    }

    public static void playInsertSound(Entity pEntity, boolean pInInventory) {
        pEntity.playSound(pInInventory
                ? SoundEvents.BUNDLE_INSERT //trayMetalLoad()
                : SoundEvents.ITEM_PICKUP,
            0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    // -----------------------------

    private static int add(ItemStack pTrayStack, ItemStack pInsertedStack) {
        if (pInsertedStack.isEmpty() ||
                !pInsertedStack.getItem().canFitInsideContainerItems() ||
                !canLoadItem(pInsertedStack)) {
            return 0;
        }

        int trayContentWeight = getContentWeight(pTrayStack);
        int insertedStackWeight = getWeight(pInsertedStack);

        int insertedCount = Math.min(pInsertedStack.getCount(), (64 - trayContentWeight) / insertedStackWeight);
        if (insertedCount == 0) {
            return 0;
        }

        List<ItemStack> contents = getContentList(pTrayStack);

        int matchingIndex = -1;

        if (!pInsertedStack.is(ModTags.Items.TRAYS)) {
            for (int i = 0; i < contents.size(); i++) {
                if (ItemStack.isSameItemSameComponents(contents.get(i), pInsertedStack)) {
                    matchingIndex = i;
                    break;
                }
            }
        }

        if (matchingIndex >= 0 && (DinnerwareConfig.trayMergeMatchingItem() || matchingIndex == 0)) {
            ItemStack matching = contents.remove(matchingIndex);
            matching.grow(insertedCount);
            contents.addFirst(matching);
        } else {
            contents.addFirst(pInsertedStack.copyWithCount(insertedCount));
        }

        setContentList(pTrayStack, contents);

        return insertedCount;
    }

    private static Optional<ItemStack> peekTop(ItemStack pStack, boolean wholeStack) {
        List<ItemStack> contents = getContentList(pStack);

        if (contents.isEmpty()) {
            return Optional.empty();
        }

        ItemStack top = contents.getFirst();
        return Optional.of(wholeStack
            ? top.copy()
            : top.copyWithCount(1)
        );
    }

    private static Optional<ItemStack> removeOne(ItemStack pTrayStack, boolean wholeStack) {
        List<ItemStack> contents = getContentList(pTrayStack);

        if (contents.isEmpty()) {
            return Optional.empty();
        }

        ItemStack top = contents.getFirst();
        ItemStack result;

        if (wholeStack) {
            result = top.copy();
            contents.removeFirst();
        } else {
            result = top.copyWithCount(1);
            top.shrink(1);

            if (top.isEmpty()) {
                contents.removeFirst();
            }
        }

        setContentList(pTrayStack, contents);
        return Optional.of(result);
    }

    private static Stream<ItemStack> getContents(ItemStack pStack) {
        return pStack
            .getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
            .nonEmptyStream();
    }

    private static int getContentWeight(ItemStack pStack) {
        return getContents(pStack).mapToInt((itemStack) ->
            getWeight(itemStack) * itemStack.getCount()).sum();
    }

    private static int getWeight(ItemStack pStack) {
        return 64 / pStack.getMaxStackSize();
    }

    // =============================

    public static boolean cycle(ItemStack pStack) {
        return cycle(pStack, 1);
    }

    public static boolean cycle(ItemStack pStack, boolean forward) {
        return cycle(pStack, forward ? 1 : -1);
    }

    public static boolean cycle(ItemStack pStack, int slotsMoved) {
        if (!pStack.is(ModTags.Items.TRAYS)) return false;
        if (slotsMoved == 0) return false;

        List<ItemStack> contents = getContentList(pStack);

        int maxSlots = contents.size();
        if (maxSlots == 0) return false;
        if (maxSlots == 1 && Math.abs(slotsMoved) == 1) return false;

        slotsMoved %= maxSlots;

        int newFirstSlot = (maxSlots + slotsMoved) % maxSlots;
        if (newFirstSlot == 0) return true;

        Collections.rotate(contents, slotsMoved);
        setContentList(pStack, contents);

        return true;
    }

    // =============================

    public static boolean hasAllItems(ItemStack pStack) {
        if (!pStack.is(ModTags.Items.TRAYS)) return false;

        Set<Item> trayItemsSet = getContents(pStack)
            .map(ItemStack::getItem)
            .collect(Collectors.toSet());

        return trayItemsSet.containsAll(
            List.of(DinnerwareItems.getSurvivalPlateItemsArray())
        );
    }

    public static void checkAndAwardTheOneTrayAdvancement(ItemStack pStack, Player pPlayer) {
        if ((pPlayer instanceof ServerPlayer pServerPlayer) && TrayItem.hasAllItems(pStack)) {
            ModCriterionTriggers.manualTrigger().trigger(pServerPlayer,
                DinnerwareConstants.id("one_tray_to_hold_them_all"));
        }
    }

    // =============================

    private static List<ItemStack> getContentList(ItemStack trayStack) {
        return trayStack
            .getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
            .nonEmptyStream()
            .map(ItemStack::copy)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private static void setContentList(ItemStack trayStack, List<ItemStack> contents) {
        if (contents.isEmpty()) {
            trayStack.remove(DataComponents.CONTAINER);
        } else {
            trayStack.set(
                DataComponents.CONTAINER,
                ItemContainerContents.fromItems(contents)
            );
        }
    }

    // =============================
}