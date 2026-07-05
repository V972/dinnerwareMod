package net.v972.dinnerware.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.block.DinnerwareBlocks;
import net.v972.dinnerware.block.entity.DinnerwareBlockEntities;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.forge.config.DinnerwareForgeConfigs;
import net.v972.dinnerware.forge.config.ForgeDinnerwareConfigBackend;
import net.v972.dinnerware.forge.registry.*;
import net.v972.dinnerware.item.DinnerwareItems;
import net.v972.dinnerware.platform.PlatformHooks;
import net.v972.dinnerware.screen.DinnerwareMenuTypes;

public final class DinnerwareForgeBootstrap {
    private DinnerwareForgeBootstrap() {
    }

    public static IEventBus getModEventBus(FMLJavaModLoadingContext context) {
        return context.getModEventBus();
    }

    public static void registerRegistries(IEventBus modEventBus) {
        DinnerwareForgeRegistries.register(modEventBus);
    }

    public static void registerConfigs(FMLJavaModLoadingContext context) {
        DinnerwareForgeConfigs.register(context);
    }

    public static void registerForgeEventHandlers() {
        MinecraftForge.EVENT_BUS.register(DinnerwareForgeLifecycleEvents.class);
    }

    public static void registerModEventHandlers(IEventBus modEventBus) {
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::commonSetup);
    }

    // ========================================

    public static void registerPlatformHooks() {
        PlatformHooks.setPlatform(new DinnerwareForgePlatform());
        DinnerwareConfig.setBackend(ForgeDinnerwareConfigBackend.INSTANCE);
        DinnerwareMenuTypes.setPlateMenuSupplier(ForgeModMenuTypes::plateMenu);

        DinnerwareBlockEntities.setPlateBlockEntityTypeSupplier(ForgeModBlockEntities::plateBlockEntityType);
        DinnerwareBlocks.setKnownPlateBlocksIterableSupplier(ForgeModBlocks::getKnownPlateBlocksIterable);
        DinnerwareBlocks.setKnownPlateBlocksArraySupplier(ForgeModBlocks::getKnownPlateBlocksArray);

        DinnerwareItems.setTrayItemsArraySupplier(ForgeModItems::getTrayItemsArray);
        DinnerwareItems.setSurvivalPlateItemsArraySupplier(ForgeModItems::getSurvivalPlateItemsArray);
        DinnerwareItems.setKnownPlateItemsSetSupplier(ForgeModItems::getKnownPlateItemsSet);
        DinnerwareItems.setTrayItemsSetSupplier(ForgeModItems::getTrayItemsSet);
    }
}