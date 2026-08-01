package net.v972.dinnerware.fabric.registry;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.v972.dinnerware.fabric.inventory.FabricPlateInventoryStorage;

public final class FabricModStorage {
    private static boolean registered;

    private FabricModStorage() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        ItemStorage.SIDED.registerForBlockEntity(
            (plateEntity, direction) -> FabricPlateInventoryStorage.of(plateEntity.getInventory()),
            FabricModBlockEntities.plateBlockEntityType()
        );

        registered = true;
    }
}