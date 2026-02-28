package net.v972.dinnerware.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.screen.PlateMenu;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlateBlockBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    public static final String ITEMS_TAG = "Inventory";
    public static int SLOT_COUNT = 3;

    @Nullable
    private Component name;

    private final ItemStackHandler items = createItemHandler();
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> items);

    public PlateBlockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLATE_BLOCK_BE.get(), pPos, pBlockState);
    }

    // ========================================

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Nonnull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return Config.onlyFoodOnPlate
                        ? canPlaceItemOnPlate(stack)
                        : super.isItemValid(slot, stack);
            }
        };
    }

    // ========================================

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PlateMenu(pContainerId, pPlayerInventory, this, null);
    }

    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new PlateMenu(pContainerId, pInventory, this, null);
    }

    // ========================================

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadClientData(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
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
        pTag.put(ITEMS_TAG, items.serializeNBT());
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
                items.deserializeNBT(pTag.getCompound(ITEMS_TAG));
            }
        }
    }

    // ========================================

    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    @Override
    public Component getDisplayName() {
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
        ItemStack itemstack = new ItemStack(getBlock());
        if (!this.isEmpty()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.put(ITEMS_TAG, items.serializeNBT());
            BlockItem.setBlockEntityData(itemstack, this.getType(), compoundtag);
        }

        if (this.name != null) {
            itemstack.setHoverName(this.name);
        }

        return itemstack;
    }

    // ========================================

    public int getInventorySize() {
        return this.items.getSlots();
    }

    public boolean isEmpty() {
        for(int i = 0; i < items.getSlots(); i++) {
            ItemStack itemStack = items.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY) return false;
        }

        return true;
    }

    public NonNullList<ItemStack> getInventoryItems() {
        NonNullList<ItemStack> result = NonNullList.withSize(items.getSlots(), ItemStack.EMPTY);

        for(int i = 0; i < items.getSlots(); i++) {
            result.set(i, items.getStackInSlot(i));
        }

        return result;
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        return items.extractItem(pIndex, pCount, false);
    }

    public ItemStack removeItem(int pIndex) {
        return removeItem(pIndex, 1);
    }

    public void clearContent() {
        for(int i = 0; i < items.getSlots(); i++) {
            items.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void dropContents() {
        Containers.dropContents(this.getLevel(), getBlockPos(), getInventoryItems());
        this.clearContent();
    }

    private boolean canPlaceItemOnPlate(ItemStack pStack) {
        return
                !Config.onlyFoodOnPlate ||
                (
                    pStack.isEdible() &&
                    !(pStack.getItem() instanceof BowlFoodItem) &&
                    !(pStack.getItem() instanceof SuspiciousStewItem)
                ) ||
                pStack.is(ModTags.Items.ADDITIONAL_FOOD);
    }

    // ========================================

    public Block getBlock(Level pLevel) {
        return pLevel.getBlockState(getBlockPos()).getBlock();
    }

    public Block getBlock() {
        return level.getBlockState(getBlockPos()).getBlock();
    }
}
