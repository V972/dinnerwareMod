package net.v972.dinnerware.forge.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.screen.PlateMenu;

public class ForgeModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(Registries.MENU, DinnerwareCommon.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<PlateMenu>> PLATE_MENU =
        registerMenuType(DinnerwareRegistryNames.Menus.PLATE_MENU, PlateMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>>
    registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static MenuType<PlateMenu> plateMenu() {
        return PLATE_MENU.get();
    }
}
