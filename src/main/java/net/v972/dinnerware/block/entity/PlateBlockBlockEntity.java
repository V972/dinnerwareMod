package net.v972.dinnerware.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
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

public class PlateBlockBlockEntity extends BlockEntity implements MenuProvider, Nameable {

    @Nullable
    private Component name;

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return Config.onlyFoodOnPlate
                ? canPlaceItemOnPlate(stack)
                : super.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PlateBlockBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLATE_BLOCK_BE.get(), pPos, pBlockState);
    }

    // ========================================

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @NotNull Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && !this.remove) {
            if (lazyItemHandler == null || !lazyItemHandler.isPresent())
                lazyItemHandler = LazyOptional.of(() -> itemHandler);
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.lazyItemHandler != null) {
            this.lazyItemHandler.invalidate();
            this.lazyItemHandler = null;
        }
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
        if (pTag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
        }
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        if (this.name != null) {
            pTag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }

    // ========================================

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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

    public void setCustomName(Component pName) {
        this.name = pName;
    }

    protected Component getDefaultName() {
        return Component.translatable("block.dinnerware.plate_block");
    }

    // ========================================

    public int getInventorySize() {
        return this.itemHandler.getSlots();
    }

    public boolean isEmpty() {
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY) return false;
        }

        return true;
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> result = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            result.set(i, itemHandler.getStackInSlot(i));
        }

        return result;
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        return itemHandler.extractItem(pIndex, pCount, false);
    }

    public ItemStack removeItem(int pIndex) {
        return removeItem(pIndex, 1);
    }

    public void clearContent() {
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void dropContents() {
        Containers.dropContents(this.getLevel(), getBlockPos(), getItems());
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
}
