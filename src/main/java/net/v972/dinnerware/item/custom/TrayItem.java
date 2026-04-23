package net.v972.dinnerware.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
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

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack trayStack = pPlayer.getItemInHand(pUsedHand);
        if (!trayStack.is(this)) return super.use(pLevel, pPlayer, pUsedHand);

        if (pPlayer.isSecondaryUseActive()) {
//            if (data.cycle()) {
//                this.playInsertSound(pPlayer);
//            }
            return InteractionResultHolder.sidedSuccess(trayStack, pLevel.isClientSide);
        } else {
            //same as startUsingItem but client only so it does not slow
            if (pLevel.isClientSide) {
                //SelectableContainerItemHud.getInstance().setUsingItem(SlotReference.hand(hand), player);
            }
            this.playRemoveOneSound(pPlayer);
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(trayStack);
        }
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLevel.isClientSide) {
            //SelectableContainerItemHud.getInstance().setUsingItem(SlotReference.EMPTY, pLivingEntity);
        }
        playInsertSound(pLivingEntity);
        pLivingEntity.swing(pLivingEntity.getUsedItemHand());
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level contextLevel = pContext.getLevel();
        if (!contextLevel.isClientSide()) {
            BlockPos pos = pContext.getClickedPos();
            Player contextPlayer = pContext.getPlayer();

            if (contextPlayer == null) {
                return InteractionResult.SUCCESS;
            }
            ItemStack trayStack = contextPlayer.getItemInHand(pContext.getHand());

            Block clickedBlock = contextLevel.getBlockState(pos).getBlock();
            if (Arrays.asList(ModBlocks.getKnownBlocksArray()).contains(clickedBlock) && (
                        ((PlateBlock)clickedBlock).MATERIAL != Blocks.BEDROCK ||
                        contextPlayer.isCreative())
                    ) {
                PlateBlockBlockEntity plateBE = (PlateBlockBlockEntity)contextLevel.getBlockEntity(pos);
                ItemStack plateStack = plateBE != null ? plateBE.getItem() : ItemStack.EMPTY;
                if (plateBE != null && add(trayStack, plateStack) > 0) {
                    plateBE.doNotDropContent();
                    contextLevel.removeBlock(pos, false);
                    playInsertSound(contextPlayer);
                    contextPlayer.awardStat(Stats.ITEM_USED.get(this));
                    contextPlayer.getCooldowns().addCooldown(this, 5);
                    checkAndAwardTheOneTrayAdvancement(trayStack, contextPlayer);
                }
            } else {
                peekTop(trayStack, false).ifPresent((plateItemStack) -> {

                    PlateBlockBlockItem plateBlockBlockItem = (PlateBlockBlockItem)plateItemStack.getItem();
                    PlateBlock plateBlock = (PlateBlock)plateBlockBlockItem.getBlock();

                    boolean canSurvive = plateBlock.canSurvive(
                            plateBlock.defaultBlockState(), contextLevel, pos.relative(pContext.getClickedFace())
                    );
                    BlockPlaceContext plateContext =
                        new BlockPlaceContext(
                            new UseOnContext(
                                contextLevel,
                                contextPlayer,
                                pContext.getHand(),
                                plateItemStack,
                                new BlockHitResult(
                                    pContext.getClickLocation(),
                                    pContext.getClickedFace(),
                                    pContext.getClickedPos(),
                                    pContext.isInside()
                                )
                            )
                        );
                    if (canSurvive && plateContext.canPlace()) {
                        playRemoveOneSound(contextPlayer);
                        removeOne(trayStack, false);
                        plateBlockBlockItem.place(plateContext);
                        contextPlayer.awardStat(Stats.ITEM_USED.get(this));
                    }
                });
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    private static boolean canLoadItem(ItemStack pStack) {
        //
        // TODO: Add Farmer's Delight Compat for Feasts
        //
        return Arrays.stream(ModItems.getPlateItemsArray()).anyMatch(pStack::is);
    }

    public static boolean addPlateToTray(ItemStack pTrayStack, ItemStack pPlateStack) {
        if (ModItems.getTrayItemsSet().contains(pTrayStack.getItem()) &&
            Arrays.stream(ModItems.getPlateItemsArray()).anyMatch(pPlateStack::is)
        ) {
            return add(pTrayStack, pPlateStack) > 0;
        }
        return false;
    }

    // ===== Bundle Copy-paste =====

    public boolean overrideStackedOnOther(ItemStack pStack, @NotNull Slot pSlot, @NotNull ClickAction pAction, @NotNull Player pPlayer) {
        // when clicking with tray on plate/slot

        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack itemstack = pSlot.getItem();
        if (itemstack.isEmpty()) {
            this.playRemoveOneSound(pPlayer);
            removeOne(pStack, true).ifPresent((itemStack) ->
                add(pStack, pSlot.safeInsert(itemStack)));
        } else if (
            itemstack.getItem().canFitInsideContainerItems() &&
            canLoadItem(itemstack)
        ) {
            int i = (64 - getContentWeight(pStack)) / getWeight(itemstack);
            int j = add(pStack, pSlot.safeTake(itemstack.getCount(), i, pPlayer));
            if (j > 0) {
                this.playInsertSound(pPlayer);
            }
        }

        // check for and award One Tray advancement
        checkAndAwardTheOneTrayAdvancement(pStack, pPlayer);

        return true;
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, @NotNull ItemStack pOther, @NotNull Slot pSlot, @NotNull ClickAction pAction, @NotNull Player pPlayer, @NotNull SlotAccess pAccess) {
        // when clicking with plate on tray

        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                removeOne(pStack, true).ifPresent((itemStack) -> {
                    this.playRemoveOneSound(pPlayer);
                    pAccess.set(itemStack);
                });
            } else {
                int leftOver = add(pStack, pOther);
                if (leftOver > 0) {
                    this.playInsertSound(pPlayer);
                    pOther.shrink(leftOver);
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

    public void onDestroyed(@NotNull ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, getContents(pItemEntity.getItem()));
    }

    public void appendHoverText(@NotNull ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        String maxFullness = "64";
        pTooltipComponents.add(
            Component.translatable("container.dinnerware.tray.fullness",
                getContentWeight(pStack), maxFullness)
                .withStyle(ChatFormatting.GRAY));

        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains(TAG_ITEMS)) {
            ListTag listTag = compoundtag.getList(TAG_ITEMS, 10);
            int parsedItems;
            int itemsSize = listTag.size();
            int maxLines = 5;

            for(parsedItems = 0; parsedItems < Math.min(itemsSize, maxLines); parsedItems++) {
                CompoundTag itemTag = listTag.getCompound(parsedItems);
                ItemStack itemstack = ItemStack.of(itemTag);
                if (itemstack.isEmpty()) continue;
                MutableComponent mutablecomponent = itemstack.getHoverName().copy();

                boolean withFood = false;
                CompoundTag blockEntityData = BlockItem.getBlockEntityData(itemstack);
                if (blockEntityData != null && blockEntityData.contains(PlateBlockBlockEntity.ITEMS_TAG)) {
                    blockEntityData = blockEntityData.getCompound(PlateBlockBlockEntity.ITEMS_TAG);
                    if (blockEntityData.contains("Items", Tag.TAG_LIST)) {
                        ListTag plateListTag = blockEntityData.getList("Items", 10);
                        withFood = !plateListTag.isEmpty();
                    }
                }

                if (withFood) mutablecomponent = Component.translatable("container.dinnerware.tray.with_food", mutablecomponent);
                if (itemstack.getCount() > 1) mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                pTooltipComponents.add(mutablecomponent);
            }

            if (itemsSize - parsedItems > 0) {
                pTooltipComponents.add(Component.translatable("container.dinnerware.tray.more", itemsSize - parsedItems).withStyle(ChatFormatting.ITALIC));
            }
        }
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, //ModSounds.TRAY_METAL_UNLOAD.get(),
                0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, //ModSounds.TRAY_METAL_LOAD.get(),
                0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    // -----------------------------

    private static int add(ItemStack pTrayStack, ItemStack pInsertedStack) {
        if (!pInsertedStack.isEmpty() &&
            pInsertedStack.getItem().canFitInsideContainerItems() &&
            canLoadItem(pInsertedStack)
        ) {
            CompoundTag compoundtag = pTrayStack.getOrCreateTag();
            if (!compoundtag.contains(TAG_ITEMS)) {
                compoundtag.put(TAG_ITEMS, new ListTag());
            }

            int i = getContentWeight(pTrayStack);
            int j = getWeight(pInsertedStack);
            int k = Math.min(pInsertedStack.getCount(), (64 - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
                Optional<CompoundTag> matchingItemOptional = getMatchingItem(pInsertedStack, listtag);
                if (matchingItemOptional.isPresent()) {
                    CompoundTag matchingItemTag = matchingItemOptional.get();
                    ItemStack itemstack = ItemStack.of(matchingItemTag);
                    itemstack.grow(k);
                    itemstack.save(matchingItemTag);
                    listtag.remove(matchingItemTag);
                    listtag.add(0, matchingItemTag);
                } else {
                    ItemStack itemstack1 = pInsertedStack.copyWithCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, compoundtag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<ItemStack> peekTop(ItemStack pStack, boolean wholeStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listTag = compoundtag.getList(TAG_ITEMS, 10);
            if (listTag.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag itemTag = listTag.getCompound(0);
                ItemStack itemStackToReturn = ItemStack.of(itemTag);

                return Optional.of(wholeStack
                    ? itemStackToReturn
                    : itemStackToReturn.copyWithCount(1));
            }
        }
    }

    private static Optional<ItemStack> removeOne(ItemStack pTrayStack, boolean wholeStack) {
        CompoundTag trayTag = pTrayStack.getOrCreateTag();
        if (!trayTag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listTag = trayTag.getList(TAG_ITEMS, 10);
            if (listTag.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag itemTag = listTag.getCompound(0);
                ItemStack itemStackToReturn = ItemStack.of(itemTag);

                if (wholeStack) {
                    listTag.remove(0);
                } else {
                    ItemStack itemStackRemaining = ItemStack.of(itemTag);
                    itemStackToReturn = ItemStack.of(itemTag).copyWithCount(1);
                    itemStackRemaining.shrink(1);

                    if (itemStackRemaining.isEmpty() || itemStackRemaining.is(Items.AIR)) {
                        listTag.remove(0);
                    } else {
                        listTag.set(0, itemStackRemaining.serializeNBT());
                    }
                }

                if (listTag.isEmpty()) {
                    pTrayStack.removeTagKey(TAG_ITEMS);
                }

                return Optional.of(itemStackToReturn);
            }
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack pStack, ListTag pList) {
        return ModItems.getTrayItemsSet().contains(pStack.getItem())
            ? Optional.empty()
            : pList
                .stream()
                .filter(CompoundTag.class::isInstance)
                .map(CompoundTag.class::cast)
                .filter((tag) ->
                    ItemStack.isSameItemSameTags(ItemStack.of(tag), pStack)
                ).findFirst();
    }

    public static boolean hasAllItems(ItemStack pStack) {
        if (!ModItems.getTrayItemsSet().contains(pStack.getItem())) return false;

        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag == null || !compoundtag.contains(TAG_ITEMS)) return false;

        ListTag listTag = compoundtag.getList(TAG_ITEMS, 10);
        Set<Item> trayItemsSet =
            listTag.stream()
                .map(tag -> ItemStack.of((CompoundTag)tag).getItem())
                .collect(Collectors.toSet());
        return trayItemsSet.containsAll(List.of(ModItems.getSurvivalPlateItemsArray()));
    }

    public static void checkAndAwardTheOneTrayAdvancement(ItemStack pStack, Player pPlayer) {
        if ((pPlayer instanceof ServerPlayer pServerPlayer) && TrayItem.hasAllItems(pStack)) {
            ModCriterionTriggers.MANUAL_TRIGGER.trigger(pServerPlayer,
                ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "one_tray_to_hold_them_all"));
        }
    }

    private static Stream<ItemStack> getContents(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    private static int getContentWeight(ItemStack pStack) {
        return getContents(pStack).mapToInt((itemStack) ->
                getWeight(itemStack) * itemStack.getCount()).sum();
    }

    private static int getWeight(ItemStack pStack) {
        return 64 / pStack.getMaxStackSize();
    }

    // =============================

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose TRAY_ARMS_POSE_IDLE = HumanoidModel.ArmPose.create("TRAY_ARMS_POSE_IDLE", true,
                (model, entity, arm) -> {
                    model.rightArm.xRot = -((float)Math.PI / 8F);
                    AnimationUtils.bobModelPart(model.rightArm, entity.tickCount, -1.0F); // * -1 from regular

                    model.leftArm.xRot  = -((float)Math.PI / 8F);
                    AnimationUtils.bobModelPart(model.leftArm, entity.tickCount, 1.0F); // * -1 from regular
                }
            );



            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) return TRAY_ARMS_POSE_IDLE;

                return HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return DinnerwareBEWLR.INSTANCE;
            }
        });
    }


    // =============================
}
