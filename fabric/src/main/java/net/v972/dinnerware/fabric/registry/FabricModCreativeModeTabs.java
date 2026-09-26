package net.v972.dinnerware.fabric.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public final class FabricModCreativeModeTabs {
    private static CreativeModeTab dinnerwareTab;
    private static boolean registered;

    private FabricModCreativeModeTabs() {
    }

    public static void register() {
        if (registered) return;

        dinnerwareTab = FabricItemGroupBuilder
            .create(DinnerwareConstants.id(
                DinnerwareRegistryNames.CreativeTabs.DINNERWARE_TAB
            ))
            .icon(() -> new ItemStack(FabricModItems.icon()))
            .appendItems(stacks -> {
                for (Item plate : FabricModItems.getKnownPlateItemsArray()) {
                    stacks.add(new ItemStack(plate));
                }

                for (Item tray : FabricModItems.getTrayItemsArray()) {
                    stacks.add(new ItemStack(tray));
                }
            })
            .build();

        dinnerwareTab.setRecipeFolderName(
            DinnerwareRegistryNames.CreativeTabs.DINNERWARE_TAB
        );

        registered = true;
    }

    public static CreativeModeTab dinnerwareTab() {
        if (!registered) {
            throw new IllegalStateException(
                "Fabric creative tabs have not been registered"
            );
        }

        return dinnerwareTab;
    }
}