package net.v972.dinnerware.forge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.forge.inventory.ForgePlateInventoryItemHandler;
import org.jetbrains.annotations.NotNull;

public class ForgePlateBlockBlockEntity extends PlateBlockBlockEntity {
    private final IItemHandler itemHandler = new ForgePlateInventoryItemHandler(this.getInventory());

    public ForgePlateBlockBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag,
                                HolderLookup.@NotNull Provider registries) {
        loadClientData(tag, registries);
    }

    @Override
    public void onDataPacket(@NotNull Connection net, ClientboundBlockEntityDataPacket pkt,
                             HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadClientData(tag, registries);
        }
    }
}