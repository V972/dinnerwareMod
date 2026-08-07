package net.v972.dinnerware.screen;

import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public final class DinnerwareMenuTypes {
    private static Supplier<MenuType<PlateMenu>> plateMenuSupplier;

    private DinnerwareMenuTypes() {
    }

    public static void setPlateMenuSupplier(Supplier<MenuType<PlateMenu>> supplier) {
        plateMenuSupplier = supplier;
    }

    public static MenuType<PlateMenu> plateMenu() {
        if (plateMenuSupplier == null) {
            throw new IllegalStateException("Dinnerware menu types have not been initialized");
        }

        return plateMenuSupplier.get();
    }
}