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
import net.v972.dinnerware.Config;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlateMenu extends AbstractContainerMenu {
    public final PlateBlockBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final List<Integer> targetIndexCache = new ArrayList<>();

    public PlateMenu(int containerId, Inventory inv, FriendlyByteBuf buffer) {
        this(containerId, inv, inv.player.level().getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(1));
    }

    public PlateMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.PLATE_MENU.get(), containerId);

        AbstractContainerMenu.checkContainerSize(inv, PlateBlockBlockEntity.SLOT_COUNT);
        this.blockEntity = ((PlateBlockBlockEntity)blockEntity);
        this.level = inv.player.level();
        this.data = data;

        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            boolean rightSlotFirst = Config.rightToLeft;
            // | right -> left | left -> right |
            // |       2       |       2       |
            // |     1   0     |     0   1     |
            this.addSlot(new SlotItemHandler(iItemHandler, 0, rightSlotFirst ? 100 : 60, 49));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, rightSlotFirst ? 60 : 100, 49));

            this.addSlot(new SlotItemHandler(iItemHandler, 2, 80, 31));
        });

        this.addDataSlots(this.data);
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
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!this.moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            } else {
                // Checks what successfully ended up in our slots
                this.checkIfPlateAndAwardInception(copyOfSourceStack, player);
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!this.moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void clicked(int slotId, int button, @NotNull ClickType type, @NotNull Player player) {
        // We only want to trigger an advancement on stack insertion, meaning we only need server
        if (!level.isClientSide()) {
            // --- QUICK_CRAFT ---
            // When the "clicked" method gets triggered, if the type is "QUICK_CRAFT" and
            // the phase is "HEADER_END", items have been added to "quickcraftSlots" Set.
            // So we cache them, before letting Minecraft handle insertion and clear the Set.
            if (type == ClickType.QUICK_CRAFT) {
                // If the "QUICK_CRAFT" phase isn't "HEADER_END" yet, to preserve the functionality
                // of "QUICK_CRAFT" we simply let the early phases run, and then exit early.
                if (button != QUICKCRAFT_HEADER_END) {
                    super.clicked(slotId, button, type, player);
                    return;
                }
                // If the phase is "HEADER_END" we proceed to cache and handle the checks
                this.targetIndexCache.clear();
                for (Slot slot : this.quickcraftSlots)
                    if (slot.index >= TE_INVENTORY_FIRST_SLOT_INDEX && slot.index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT)
                        this.targetIndexCache.add(slot.index);
                // Lets Minecraft handle the click/stack logic
                super.clicked(slotId, button, type, player);
                // Checks what successfully ended up in our slots
                if (!this.targetIndexCache.isEmpty()) {
                    for (int slotIndex : this.targetIndexCache) {
                        Slot slot = this.slots.get(slotIndex);
                        if (slot.hasItem())
                            this.checkIfPlateAndAwardInception(slot.getItem(), player);
                    }
                }
            }
            // --- PICKUP ---
            // This gets triggered every time a stack is simply left-clicked, this includes to pickup or to place down.
            // Since we only care about insertion, and this gets triggered for placement, we just have to check if the
            // clicked slot is within the bounds of the block entity, and if so validate it.
            // --- SWAP ---
            // Gets triggered when items are swapped e.g. the player uses hotkeys to swap a hotbar item and the hovered item.
            // Since this type uses the id of the slot that was swapped "into", we can reuse the logic above.
            else if (type == ClickType.PICKUP || type == ClickType.SWAP) {
                // Lets Minecraft handle the click/stack logic
                super.clicked(slotId, button, type, player);
                // Checks what successfully ended up in our slots
                if (slotId >= TE_INVENTORY_FIRST_SLOT_INDEX && slotId < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
                    Slot slot = this.slots.get(slotId);
                    if (slot.hasItem())
                        this.checkIfPlateAndAwardInception(slot.getItem(), player);
                }
            }
            // --- CLONE, THROW, PICKUP_ALL ---
            // Simply delegates for types that can't insert items into the container
            // --- QUICK_MOVE ---
            // Handled entirely in the "quickMoveStack" method, no logic needed here
            else {
                super.clicked(slotId, button, type, player);
            }
        } else {
            // Client side just delegates
            super.clicked(slotId, button, type, player);
        }
    }

    private void checkIfPlateAndAwardInception(ItemStack stack, Player player) {
        if (stack.is(ModTags.Items.PLATES) && (player instanceof ServerPlayer serverPlayer)) {
            ModCriterionTriggers.MANUAL_TRIGGER.trigger(serverPlayer, ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "put_plate_in_plate"));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blockEntity.getBlock(level));
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