package net.v972.dinnerware.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.v972.dinnerware.config.CommonConfig;
import net.v972.dinnerware.inventory.PlateInventory;
import net.v972.dinnerware.screen.PlateMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

public class PlateBlockBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    public static final String ITEMS_TAG = "Inventory";
    public static final int SLOT_COUNT = 3;

    @Nullable
    private Component name;

    private int eatingAttemptClicks;
    private int roundRobinCurrentSlot;

    private boolean doDropContent = true;

    private final PlateInventory inventory = new PlateInventory(SLOT_COUNT, this::inventoryChanged);

    protected final ContainerData containerData;

    public PlateBlockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLATE_BLOCK_BE.get(), pPos, pBlockState);
        eatingAttemptClicks = 0;
        roundRobinCurrentSlot = 0;
        this.containerData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) return roundRobinCurrentSlot;
                return -1;
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) roundRobinCurrentSlot = pValue;
            }

            @Override
            public int getCount() {  return 1; }
        };
    }

    // ========================================

    public PlateInventory getInventory() {
        return this.inventory;
    }

    public boolean stillValidForPlayer(@NotNull Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }

        return player.distanceToSqr(
            this.worldPosition.getX() + 0.5D,
            this.worldPosition.getY() + 0.5D,
            this.worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }

    private void inventoryChanged() {
        setChanged();

        if (level == null) {
            System.out.println("Level is null in inventoryChanged()");
            return;
        }

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    // ========================================

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new PlateMenu(pContainerId, pPlayerInventory, this, this.containerData);
    }

    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new PlateMenu(pContainerId, pInventory, this, this.containerData);
    }

    // ========================================

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        loadClientData(pTag);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveClientData(pTag);
    }

    // ========================================

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag pTag = super.getUpdateTag();
        saveClientData(pTag);
        return pTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag pTag) {
        if (pTag != null) {
            loadClientData(pTag);
        }
    }

    // ========================================

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadClientData(tag);
        }
    }

    // ========================================

    private void saveClientData(CompoundTag pTag) {
        if (pTag == null)
            pTag = new CompoundTag();

        CompoundTag inventoryTag = new CompoundTag();
        this.inventory.save(inventoryTag);
        pTag.put(ITEMS_TAG, inventoryTag);

        if (this.name != null) {
            pTag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }

    private void loadClientData(CompoundTag pTag) {
        if (pTag != null) {
            if (pTag.contains("CustomName", 8)) {
                this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
            }
            if (pTag.contains(ITEMS_TAG)) {
                this.inventory.load(pTag.getCompound(ITEMS_TAG));
            }
        }
    }

    // ========================================

    @Override
    public @NotNull Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return this.name;
    }

    public void setCustomName(@Nullable Component pName) {
        this.name = pName;
    }

    protected Component getDefaultName() {
        return Component.translatable("block.dinnerware.plate_block");
    }

    // ========================================

    public void fromItem(ItemStack pItem) {
        CompoundTag pTag = pItem.getTag();
        loadClientData(pTag);
    }

    public ItemStack getItem() {
        return this.getItem(true);
    }

    public ItemStack getItem(boolean pWithContent) {
        ItemStack itemstack = new ItemStack(getBlock());
        if (!this.isEmpty() && pWithContent) {
            CompoundTag compoundtag = new CompoundTag();

            CompoundTag inventoryTag = new CompoundTag();
            this.inventory.save(inventoryTag);
            compoundtag.put(ITEMS_TAG, inventoryTag);

            BlockItem.setBlockEntityData(itemstack, this.getType(), compoundtag);
        }

        if (this.name != null) {
            itemstack.setHoverName(this.name);
        }

        return itemstack;
    }

    // ========================================

    public int getInventorySize() {
        return this.inventory.getSlotCount();
    }

    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    public int getNonEmptySlotsCount() {
        return this.inventory.getNonEmptySlotsCount();
    }

    public OptionalInt getFirstNonEmptySlot(boolean validateBlacklist) {
        return this.inventory.getFirstNonEmptySlot(validateBlacklist);
    }

    public boolean hasPlateInside() {
        return this.inventory.hasPlateInside();
    }

    ///  Returns **a copy** of the item stacks in all slots
    public NonNullList<ItemStack> getInventoryStacks() {
        return this.inventory.getStackCopies();
    }

    ///  Returns **a copy** of the item stack in the slot
    public ItemStack getStackInSlot(int pSlot) {
        return this.inventory.getStackCopyInSlot(pSlot);
    }

    public ItemStack extractItem(int pIndex) {
        return extractItem(pIndex, 1);
    }

    public ItemStack extractItem(int pIndex, int pCount) {
        return this.inventory.extractItem(pIndex, pCount, false);
    }

    public void clearContent() {
        this.inventory.clearContent();
    }

    public void dropContents() {
        if (this.level == null) return;

        Containers.dropContents(this.level, getBlockPos(), getInventoryStacks());
        this.clearContent();
    }

    // ========================================

    private int advanceRoundRobinEatingSlot() {
        int newSlot = (roundRobinCurrentSlot + 1) % SLOT_COUNT;

        if (getStackInSlot(newSlot).isEmpty() ||
                CommonConfig.isInFoodBlacklist(getStackInSlot(newSlot))
        ) {
            newSlot = (newSlot + 1) % SLOT_COUNT;
        } else return newSlot;

        if (getStackInSlot(newSlot).isEmpty() ||
                CommonConfig.isInFoodBlacklist(getStackInSlot(newSlot))
        ) {
            newSlot = (newSlot + 1) % SLOT_COUNT;
        } else return newSlot;

        if (getStackInSlot(newSlot).isEmpty() ||
                CommonConfig.isInFoodBlacklist(getStackInSlot(newSlot))
        ) {
            return 0;
        }

        return newSlot;
    }

    public int getRoundRobinCurrentEatingSlot() {
        return roundRobinCurrentSlot;
    }

    public int getEatingAttemptClicks() { return eatingAttemptClicks; }

    public int eatingAttemptClick() {
        eatingAttemptClicks++;
        this.roundRobinCurrentSlot = this.advanceRoundRobinEatingSlot();
        return eatingAttemptClicks;
    }

    /// 1 - one item in any slot;<br>
    /// 2 - main and side dish slots;<br>
    /// 3 - all slots;<br>
    /// 4 - main dish slot and extra dish slot;<br>
    /// 5 - side dish slot and extra dish slot;
    public int getSlotStacksPositions() {
        int result = this.getNonEmptySlotsCount();

        if (result == 2 && !getStackInSlot(2).isEmpty()) {
            result *= 2; // 4

            if (!getStackInSlot(1).isEmpty())
                result += 1; // 5
        }

        return result;
    }

    public void eatFromSlot(int pSlot) {
        this.extractItem(pSlot);
        if (CommonConfig.EATING_MODE.get() == CommonConfig.EATING_MODES.ROUND_ROBIN)
            eatingAttemptClick();
    }

    // ========================================

    public Block getBlock(Level pLevel) {
        return pLevel.getBlockState(getBlockPos()).getBlock();
    }

    public Block getBlock() {
        if (level == null) return Blocks.AIR;
        return this.getBlock(level);
    }

    // ========================================

    public void doDropContent() { doDropContent = true; }

    public void doNotDropContent() { doDropContent = false; }

    public boolean isDoDropContent() { return doDropContent; }
}
