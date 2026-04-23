package net.v972.dinnerware.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class PlateMenu extends AbstractContainerMenu {
    public final PlateBlockBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public PlateMenu(int pContainerId, Inventory pInv, FriendlyByteBuf pExtraData) {
        this(pContainerId, pInv, pInv.player.level().getBlockEntity(pExtraData.readBlockPos()),
                new SimpleContainerData(1));
    }

    public PlateMenu(int pContainerId, Inventory pInv, BlockEntity pBlockEntity, ContainerData pData)
    {
        super(ModMenuTypes.PLATE_MENU.get(), pContainerId);

        checkContainerSize(pInv, PlateBlockBlockEntity.SLOT_COUNT);
        blockEntity = ((PlateBlockBlockEntity)pBlockEntity);
        this.level = pInv.player.level();
        this.data = pData;

        addPlayerInventory(pInv);
        addPlayerHotbar(pInv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            if (Config.rightToLeft) {
                //    2
                //
                // 1     0
                this.addSlot(new SlotItemHandler(iItemHandler, 0, 100, 49));
                this.addSlot(new SlotItemHandler(iItemHandler, 1, 60, 49));
            } else {
                //    2
                //
                // 0     1
                this.addSlot(new SlotItemHandler(iItemHandler, 0, 60, 49));
                this.addSlot(new SlotItemHandler(iItemHandler, 1, 100, 49));
            }

            this.addSlot(new SlotItemHandler(iItemHandler, 2, 80, 31));
        });

        addDataSlots(data);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 3;
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            } else {
                checkIfPlateAndAwardInception(copyOfSourceStack, pPlayer);
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void clicked(int pSlotId, int pButton, @NotNull ClickType pClickType, @NotNull Player pPlayer) {
        if (!level.isClientSide()) {
            System.out.println("pSlotId: " + pSlotId);
            System.out.println("pButton: " + pButton);
            System.out.println("pClickType: " + pClickType);

            ItemStack item1Pre = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX).getItem().copy();
            ItemStack item2Pre = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX + 1).getItem().copy();
            ItemStack item3Pre = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX + 2).getItem().copy();

            super.clicked(pSlotId, pButton, pClickType, pPlayer);

            if (pClickType != ClickType.QUICK_MOVE && pSlotId >= TE_INVENTORY_FIRST_SLOT_INDEX &&
                (pSlotId < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
            ) {
                System.out.println(slots.get(pSlotId).getItem());
                if (pClickType != ClickType.QUICK_CRAFT) {
                    checkIfPlateAndAwardInception(slots.get(pSlotId).getItem(), pPlayer);
                } else {


                    ItemStack item1Post = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX).getItem().copy();
                    ItemStack item2Post = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX + 1).getItem().copy();
                    ItemStack item3Post = slots.get(TE_INVENTORY_FIRST_SLOT_INDEX + 2).getItem().copy();

                    System.out.println(item1Pre + " -> " + item1Post);
                    System.out.println(item2Pre + " -> " + item2Post);
                    System.out.println(item3Pre + " -> " + item3Post);
                }
            }
        } else super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }

    private void checkIfPlateAndAwardInception(ItemStack pStack, Player pPlayer) {
        if (ModItems.getPlateItemsSet().contains(pStack.getItem()) && (pPlayer instanceof ServerPlayer pServerPlayer)) {
            ModCriterionTriggers.MANUAL_TRIGGER.trigger(pServerPlayer,
                ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "put_plate_in_plate"));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, blockEntity.getBlock(level));
    }

    public int getRoundRobinSelectedSlot() {
        return this.data.get(0);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
