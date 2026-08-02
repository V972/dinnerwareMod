package net.v972.dinnerware.fabric.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FabricModItems {
    private static final Map<PlateDefinition, PlateBlockBlockItem> PLATE_ITEMS =
        new EnumMap<>(PlateDefinition.class);

    private static Item icon;
    private static TrayItem ironTray;
    private static TrayItem goldTray;

    private static boolean registered;

    private FabricModItems() {
    }

    public static void register() {
        if (registered) return;

        icon = registerItem(
            DinnerwareRegistryNames.Items.DINNERWARE_ICON,
            new Item(new Item.Properties().fireResistant())
        );

        ironTray = registerItem(
            DinnerwareRegistryNames.Items.IRON_TRAY,
            new TrayItem(
                Blocks.IRON_BLOCK,
                new Item.Properties().stacksTo(1))
        );

        goldTray = registerItem(
            DinnerwareRegistryNames.Items.GOLD_TRAY,
            new TrayItem(
                Blocks.GOLD_BLOCK,
                new Item.Properties().stacksTo(1))
        );

        for (PlateDefinition definition : PlateDefinition.values()) {
            PlateBlockBlockItem item = new PlateBlockBlockItem(
                FabricModBlocks.plateBlock(definition),
                new Item.Properties());

            PLATE_ITEMS.put(definition, item);
            registerItem(definition.item(), item);
        }

        registerPlateFuels();

        registered = true;
    }

    public static Item icon() {
        ensureRegistered();
        return icon;
    }

    public static TrayItem ironTray() {
        ensureRegistered();
        return ironTray;
    }

    public static TrayItem goldTray() {
        ensureRegistered();
        return goldTray;
    }

    public static Item[] getTrayItemsArray() {
        ensureRegistered();
        return new Item[] { ironTray, goldTray };
    }

    public static Set<Item> getTrayItemsSet() {
        ensureRegistered();
        return Set.of(ironTray, goldTray);
    }

    public static Item[] getSurvivalPlateItemsArray() {
        ensureRegistered();

        return PLATE_ITEMS.entrySet()
            .stream()
            .filter(entry -> entry.getKey() != PlateDefinition.BEDROCK)
            .map(entry -> (Item) entry.getValue())
            .toArray(Item[]::new);
    }

    public static Set<Item> getKnownPlateItemsSet() {
        ensureRegistered();

        return PLATE_ITEMS.values()
            .stream()
            .map(item -> (Item) item)
            .collect(Collectors.toUnmodifiableSet());
    }

    public static PlateBlockBlockItem[] getKnownPlateItemsArray() {
        ensureRegistered();

        return PLATE_ITEMS.values()
            .toArray(PlateBlockBlockItem[]::new);
    }

    public static PlateBlockBlockItem plateItem(
            PlateDefinition definition
    ) {
        ensureRegistered();

        PlateBlockBlockItem item = PLATE_ITEMS.get(definition);

        if (item == null) {
            throw new IllegalArgumentException(
                    "Unknown plate definition: " + definition
            );
        }

        return item;
    }

    private static void registerPlateFuels() {
        registerFuel(PlateDefinition.OAK);
        registerFuel(PlateDefinition.BIRCH);
        registerFuel(PlateDefinition.SPRUCE);
        registerFuel(PlateDefinition.JUNGLE);
        registerFuel(PlateDefinition.ACACIA);
        registerFuel(PlateDefinition.DARK_OAK);
        registerFuel(PlateDefinition.CHERRY);
        registerFuel(PlateDefinition.MANGROVE);
        registerFuel(PlateDefinition.BAMBOO);
    }

    private static void registerFuel(PlateDefinition definition) {
        PlateBlockBlockItem item = PLATE_ITEMS.get(definition);

        if (item == null) {
            throw new IllegalStateException(
                "Plate item has not been created: " + definition
            );
        }

        FuelRegistry.INSTANCE.add(item, 150);
    }

    private static <T extends Item> T registerItem(String path, T item) {
        return Registry.register(BuiltInRegistries.ITEM,
            DinnerwareConstants.id(path), item);
    }

    private static void ensureRegistered() {
        if (!registered) {
            throw new IllegalStateException(
                "Fabric items have not been registered"
            );
        }
    }
}