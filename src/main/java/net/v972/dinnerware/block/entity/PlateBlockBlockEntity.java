package net.v972.dinnerware.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.v972.dinnerware.screen.PlateMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlateBlockBlockEntity extends BaseContainerBlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PlateBlockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLATE_BLOCK_BE.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @NotNull Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && !this.remove) {
            if (lazyItemHandler == null)
                lazyItemHandler = LazyOptional.of(this::createHandler);
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private net.minecraftforge.items.IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.lazyItemHandler != null) {
            this.lazyItemHandler.invalidate();
            this.lazyItemHandler = null;
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.dinnerware.plate_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PlateMenu(pContainerId, pPlayerInventory, this, null);
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new PlateMenu(pContainerId, pInventory, this, null);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY) return false;
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return itemHandler.getStackInSlot(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return itemHandler.extractItem(pIndex, pCount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        ItemStack itemInSlot = itemHandler.getStackInSlot(pIndex);
        if (itemInSlot == ItemStack.EMPTY) return ItemStack.EMPTY;
        return itemHandler.extractItem(pIndex, itemInSlot.getCount(), true);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        itemHandler.setStackInSlot(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
