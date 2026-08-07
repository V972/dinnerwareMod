package net.v972.dinnerware.fabric.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
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
        if (registered) {
            return;
        }

        dinnerwareTab = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            DinnerwareConstants.id(DinnerwareRegistryNames.CreativeTabs.DINNERWARE_TAB),
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(FabricModItems.icon()))
                .title(Component.translatable("creativetab.dinnerware_tab"))
                .displayItems((parameters, output) -> {
                    for (Item plate : FabricModItems.getKnownPlateItemsArray()) {
                        output.accept(plate);
                    }

                    for (Item tray : FabricModItems.getTrayItemsArray()) {
                        output.accept(tray);
                    }
                })
                .build()
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