package net.v972.dinnerware.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.ModItems;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TrayItem extends Item {
    private static final String TAG_ITEMS = "Items";
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public TrayItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {

            // open GUI
            // (pass platePile ??? )
        }

        return InteractionResultHolder.fail(itemstack);
        //return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = pContext.getClickedPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            Player player = pContext.getPlayer();

            if (player == null) {
                return InteractionResult.SUCCESS;
            }
            ItemStack itemstack = player.getItemInHand(pContext.getHand());

            if (block == ModBlocks.PLATE_BLOCK.get()) {
                if (canLoadPlate(itemstack)) {
                    player.sendSystemMessage(Component.literal("Clicked plate; Loading"));
                    // serialize food info
                    // add to inventory (NBT)
                    // level.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            } else if (canUnloadPlate(itemstack)) {
                player.sendSystemMessage(Component.literal("Clicked block; Unloading"));
                // deserialize food data
                // place block
                // remove from inventory

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }

        return InteractionResult.SUCCESS;
    }

    private boolean canLoadPlate(ItemStack pStack) {
        // check if any plates in the inventory
        // if top one has no food
        // if INFINITE || platePile.size is < Config.MaxPlatePileSize

        return true;
    }

    private boolean canUnloadPlate(ItemStack pStack) {
        // check if any plates in the inventory (nbt)
        // check if block face has enough collision to place the plate

        return true;
    }

    private static boolean canLoadItem(ItemStack pStack) {
        //
        // TODO: Add Farmer's Delight Compat for Feasts
        //
        return pStack.is(ModBlocks.PLATE_BLOCK.get().asItem());
    }

    // ===== Bundle Copy-paste =====

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        // when clicking with tray on plate/slot

        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack itemstack = pSlot.getItem();
        if (itemstack.isEmpty()) {
            this.playRemoveOneSound(pPlayer);
            removeOne(pStack).ifPresent((itemStack) -> {
                add(pStack, pSlot.safeInsert(itemStack));
            });
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

        return true;
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        // when clicking with plate on tray

        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                removeOne(pStack).ifPresent((itemStack) -> {
                    this.playRemoveOneSound(pPlayer);
                    pAccess.set(itemStack);
                });
            } else {
                int i = add(pStack, pOther);
                if (i > 0) {
                    this.playInsertSound(pPlayer);
                    pOther.shrink(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    // -----------------------------

    public static float getFullnessDisplay(ItemStack pStack) {
        if (Config.trayPlatePileMaxSize == 0) return 0F;
        return (float)getContentWeight(pStack) / (float)Config.trayPlatePileMaxSize;
    }

    public boolean isBarVisible(ItemStack pStack) {
        return Config.trayPlatePileMaxSize > 0 && getContentWeight(pStack) > 0;
    }

    public int getBarWidth(ItemStack pStack) {
        return Math.min(1 + 12 * getContentWeight(pStack) / (Config.trayPlatePileMaxSize == 0 ? 1 : Config.trayPlatePileMaxSize), 13);
    }

    public int getBarColor(ItemStack pStack) {
        return BAR_COLOR;
    }

    public void onDestroyed(ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, getContents(pItemEntity.getItem()));
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String maxFullness = Config.trayPlatePileMaxSize == 0 ? "INFINITY" : String.valueOf(Config.trayPlatePileMaxSize);
        pTooltipComponents.add(
                Component.translatable("item.dinnerware.tray.fullness",
                        getContentWeight(pStack), maxFullness)
                        .withStyle(ChatFormatting.GRAY));
        if (pIsAdvanced.isAdvanced()) {
            CompoundTag compoundtag = pStack.getOrCreateTag();
            if (!compoundtag.contains(TAG_ITEMS)) {
                compoundtag.put(TAG_ITEMS, new ListTag());
            }
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                pTooltipComponents.add(
                    Component.literal("[").append(itemstack.getDisplayName()).append("]")
                );
            }

        }
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE,
                0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT,
                0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    // -----------------------------

    private static int add(ItemStack pTrayStack, ItemStack pInsertedStack) {
        if (
                !pInsertedStack.isEmpty() &&
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
                Optional<CompoundTag> optional = getMatchingItem(pInsertedStack, listtag);
                if (optional.isPresent()) {
                    CompoundTag compoundtag1 = optional.get();
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    itemstack.grow(k);
                    itemstack.save(compoundtag1);
                    listtag.remove(compoundtag1);
                    listtag.add(0, (Tag)compoundtag1);
                } else {
                    ItemStack itemstack1 = pInsertedStack.copyWithCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, (Tag)compoundtag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<ItemStack> removeOne(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    pStack.removeTagKey(TAG_ITEMS);
                }

                return Optional.of(itemstack);
            }
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack pStack, ListTag pList) {
        return pStack.is(ModItems.TRAY.get())
                ? Optional.empty()
                : pList
                    .stream()
                    .filter(CompoundTag.class::isInstance)
                    .map(CompoundTag.class::cast)
                    .filter((tag) -> {
                        return ItemStack.isSameItemSameTags(ItemStack.of(tag), pStack);
                    }).findFirst();
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
        return getContents(pStack).mapToInt((itemStack) -> {
            return getWeight(itemStack) * itemStack.getCount();
        }).sum();
    }

    private static int getWeight(ItemStack pStack) {
        if (pStack.is(ModItems.TRAY.get())) {
            return 4 + getContentWeight(pStack);
        } else {
            if ((pStack.is(Items.BEEHIVE) || pStack.is(Items.BEE_NEST)) && pStack.hasTag()) {
                CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
                if (compoundtag != null && !compoundtag.getList("Bees", 10).isEmpty()) {
                    return 64;
                }
            }

            return 64 / pStack.getMaxStackSize();
        }
    }

    // =============================
}
