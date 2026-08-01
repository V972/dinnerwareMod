package net.v972.dinnerware.fabric.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.screen.PlateMenu;

public final class FabricModMenuTypes {
    private static MenuType<PlateMenu> plateMenu;

    private static boolean registered;

    private FabricModMenuTypes() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        plateMenu = Registry.register(
            BuiltInRegistries.MENU,
            DinnerwareConstants.id(DinnerwareRegistryNames.Menus.PLATE_MENU),
            new ExtendedScreenHandlerType<>(PlateMenu::new)
        );

        registered = true;
    }

    public static MenuType<PlateMenu> plateMenu() {
        if (!registered) {
            throw new IllegalStateException(
                "Fabric menu types have not been registered"
            );
        }

        return plateMenu;
    }
}