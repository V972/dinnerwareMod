package net.v972.dinnerware.forge.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.forge.item.ForgePlateBlockBlockItem;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForgeModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinnerwareCommon.MOD_ID);

    // ========================================

    public  static final RegistryObject<Item> ICON = ITEMS.register(DinnerwareRegistryNames.Items.DINNERWARE_ICON,
        () -> new Item(new Item.Properties().fireResistant()));


    public static final RegistryObject<TrayItem> TRAY_IRON = ITEMS.register(DinnerwareRegistryNames.Items.IRON_TRAY,
        () -> new TrayItem(Blocks.IRON_BLOCK, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<TrayItem> TRAY_GOLD = ITEMS.register(DinnerwareRegistryNames.Items.GOLD_TRAY,
        () -> new TrayItem(Blocks.GOLD_BLOCK, new Item.Properties().stacksTo(1)));


    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BEDROCK =
        registerPlateItem(PlateDefinition.BEDROCK, ForgeModBlocks.PLATE_BLOCK_BEDROCK);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_QUARTZ =
        registerPlateItem(PlateDefinition.QUARTZ, ForgeModBlocks.PLATE_BLOCK_QUARTZ);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_QUARTZ =
        registerPlateItem(PlateDefinition.CHISELED_QUARTZ, ForgeModBlocks.PLATE_BLOCK_CHISELED_QUARTZ);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_IRON =
        registerPlateItem(PlateDefinition.IRON, ForgeModBlocks.PLATE_BLOCK_IRON);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GOLD =
        registerPlateItem(PlateDefinition.GOLD, ForgeModBlocks.PLATE_BLOCK_GOLD);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DIAMOND =
        registerPlateItem(PlateDefinition.DIAMOND, ForgeModBlocks.PLATE_BLOCK_DIAMOND);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OBSIDIAN =
        registerPlateItem(PlateDefinition.OBSIDIAN, ForgeModBlocks.PLATE_BLOCK_OBSIDIAN);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_POLISHED_BLACKSTONE =
        registerPlateItem(PlateDefinition.POLISHED_BLACKSTONE, ForgeModBlocks.PLATE_BLOCK_POLISHED_BLACKSTONE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_NETHER_BRICKS =
        registerPlateItem(PlateDefinition.CHISELED_NETHER_BRICKS, ForgeModBlocks.PLATE_BLOCK_CHISELED_NETHER_BRICKS);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPUR =
        registerPlateItem(PlateDefinition.PURPUR, ForgeModBlocks.PLATE_BLOCK_PURPUR);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OAK =
        registerPlateItem(PlateDefinition.OAK, ForgeModBlocks.PLATE_BLOCK_OAK, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BIRCH =
        registerPlateItem(PlateDefinition.BIRCH, ForgeModBlocks.PLATE_BLOCK_BIRCH, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_SPRUCE =
        registerPlateItem(PlateDefinition.SPRUCE, ForgeModBlocks.PLATE_BLOCK_SPRUCE, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_JUNGLE =
        registerPlateItem(PlateDefinition.JUNGLE, ForgeModBlocks.PLATE_BLOCK_JUNGLE, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ACACIA =
        registerPlateItem(PlateDefinition.ACACIA, ForgeModBlocks.PLATE_BLOCK_ACACIA, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DARK_OAK =
        registerPlateItem(PlateDefinition.DARK_OAK, ForgeModBlocks.PLATE_BLOCK_DARK_OAK, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHERRY =
        registerPlateItem(PlateDefinition.CHERRY, ForgeModBlocks.PLATE_BLOCK_CHERRY, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MANGROVE =
        registerPlateItem(PlateDefinition.MANGROVE, ForgeModBlocks.PLATE_BLOCK_MANGROVE, 150);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BAMBOO =
        registerPlateItem(PlateDefinition.BAMBOO, ForgeModBlocks.PLATE_BLOCK_BAMBOO, 150);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CRIMSON =
        registerPlateItem(PlateDefinition.CRIMSON, ForgeModBlocks.PLATE_BLOCK_CRIMSON);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WARPED =
        registerPlateItem(PlateDefinition.WARPED, ForgeModBlocks.PLATE_BLOCK_WARPED);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_TERRACOTTA =
        registerPlateItem(PlateDefinition.TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_TERRACOTTA);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_TERRACOTTA =
        registerPlateItem(PlateDefinition.WHITE_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_WHITE_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_TERRACOTTA =
        registerPlateItem(PlateDefinition.ORANGE_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_ORANGE_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_TERRACOTTA =
        registerPlateItem(PlateDefinition.MAGENTA_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_MAGENTA_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_TERRACOTTA =
        registerPlateItem(PlateDefinition.LIGHT_BLUE_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_TERRACOTTA =
        registerPlateItem(PlateDefinition.YELLOW_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_YELLOW_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_TERRACOTTA =
        registerPlateItem(PlateDefinition.LIME_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_LIME_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_TERRACOTTA =
        registerPlateItem(PlateDefinition.PINK_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_PINK_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_TERRACOTTA =
        registerPlateItem(PlateDefinition.GRAY_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_GRAY_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_TERRACOTTA =
        registerPlateItem(PlateDefinition.LIGHT_GRAY_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_TERRACOTTA =
        registerPlateItem(PlateDefinition.CYAN_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_CYAN_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_TERRACOTTA =
        registerPlateItem(PlateDefinition.PURPLE_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_PURPLE_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_TERRACOTTA =
        registerPlateItem(PlateDefinition.BLUE_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_BLUE_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_TERRACOTTA =
        registerPlateItem(PlateDefinition.BROWN_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_BROWN_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_TERRACOTTA =
        registerPlateItem(PlateDefinition.GREEN_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_GREEN_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_TERRACOTTA =
        registerPlateItem(PlateDefinition.RED_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_RED_TERRACOTTA);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_TERRACOTTA =
        registerPlateItem(PlateDefinition.BLACK_TERRACOTTA, ForgeModBlocks.PLATE_BLOCK_BLACK_TERRACOTTA);

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_CONCRETE =
        registerPlateItem(PlateDefinition.WHITE_CONCRETE, ForgeModBlocks.PLATE_BLOCK_WHITE_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_CONCRETE =
        registerPlateItem(PlateDefinition.ORANGE_CONCRETE, ForgeModBlocks.PLATE_BLOCK_ORANGE_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_CONCRETE =
        registerPlateItem(PlateDefinition.MAGENTA_CONCRETE, ForgeModBlocks.PLATE_BLOCK_MAGENTA_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_CONCRETE =
        registerPlateItem(PlateDefinition.LIGHT_BLUE_CONCRETE, ForgeModBlocks.PLATE_BLOCK_LIGHT_BLUE_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_CONCRETE =
        registerPlateItem(PlateDefinition.YELLOW_CONCRETE, ForgeModBlocks.PLATE_BLOCK_YELLOW_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_CONCRETE =
        registerPlateItem(PlateDefinition.LIME_CONCRETE, ForgeModBlocks.PLATE_BLOCK_LIME_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_CONCRETE =
        registerPlateItem(PlateDefinition.PINK_CONCRETE, ForgeModBlocks.PLATE_BLOCK_PINK_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_CONCRETE =
        registerPlateItem(PlateDefinition.GRAY_CONCRETE, ForgeModBlocks.PLATE_BLOCK_GRAY_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_CONCRETE =
        registerPlateItem(PlateDefinition.LIGHT_GRAY_CONCRETE, ForgeModBlocks.PLATE_BLOCK_LIGHT_GRAY_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_CONCRETE =
        registerPlateItem(PlateDefinition.CYAN_CONCRETE, ForgeModBlocks.PLATE_BLOCK_CYAN_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_CONCRETE =
        registerPlateItem(PlateDefinition.PURPLE_CONCRETE, ForgeModBlocks.PLATE_BLOCK_PURPLE_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_CONCRETE =
        registerPlateItem(PlateDefinition.BLUE_CONCRETE, ForgeModBlocks.PLATE_BLOCK_BLUE_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_CONCRETE =
        registerPlateItem(PlateDefinition.BROWN_CONCRETE, ForgeModBlocks.PLATE_BLOCK_BROWN_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_CONCRETE =
        registerPlateItem(PlateDefinition.GREEN_CONCRETE, ForgeModBlocks.PLATE_BLOCK_GREEN_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_CONCRETE =
        registerPlateItem(PlateDefinition.RED_CONCRETE, ForgeModBlocks.PLATE_BLOCK_RED_CONCRETE);
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_CONCRETE =
        registerPlateItem(PlateDefinition.BLACK_CONCRETE, ForgeModBlocks.PLATE_BLOCK_BLACK_CONCRETE);

    // ========================================

    public static TrayItem[] getTrayItemsArray() {
        return new TrayItem[] {
            TRAY_IRON.get(),
            TRAY_GOLD.get(),
        };
    }

    public static Set<Item> getTrayItemsSet() {
        return Set.of(
            TRAY_IRON.get(),
            TRAY_GOLD.get()
        );
    }

    public static Item[] getSurvivalPlateItemsArray() {
        return Stream.of(getKnownPlateItemsArray())
            .filter(plate -> plate != PLATE_ITEM_BEDROCK.get())
            .toArray(Item[]::new);
    }

    public static Set<Item> getKnownPlateItemsSet() {
        return Stream.of(getKnownPlateItemsArray())
            .collect(Collectors.toSet());
    }

    public static PlateBlockBlockItem[] getKnownPlateItemsArray() {
        return new PlateBlockBlockItem[] {
            PLATE_ITEM_BEDROCK.get(),

            PLATE_ITEM_QUARTZ.get(),
            PLATE_ITEM_CHISELED_QUARTZ.get(),

            PLATE_ITEM_IRON.get(),
            PLATE_ITEM_GOLD.get(),
            PLATE_ITEM_DIAMOND.get(),

            PLATE_ITEM_OBSIDIAN.get(),

            PLATE_ITEM_POLISHED_BLACKSTONE.get(),
            PLATE_ITEM_CHISELED_NETHER_BRICKS.get(),

            PLATE_ITEM_PURPUR.get(),

            PLATE_ITEM_OAK.get(),
            PLATE_ITEM_BIRCH.get(),
            PLATE_ITEM_SPRUCE.get(),
            PLATE_ITEM_JUNGLE.get(),
            PLATE_ITEM_ACACIA.get(),
            PLATE_ITEM_DARK_OAK.get(),
            PLATE_ITEM_CHERRY.get(),
            PLATE_ITEM_MANGROVE.get(),
            PLATE_ITEM_BAMBOO.get(),

            PLATE_ITEM_CRIMSON.get(),
            PLATE_ITEM_WARPED.get(),

            PLATE_ITEM_TERRACOTTA.get(),

            PLATE_ITEM_WHITE_TERRACOTTA.get(),
            PLATE_ITEM_ORANGE_TERRACOTTA.get(),
            PLATE_ITEM_MAGENTA_TERRACOTTA.get(),
            PLATE_ITEM_LIGHT_BLUE_TERRACOTTA.get(),
            PLATE_ITEM_YELLOW_TERRACOTTA.get(),
            PLATE_ITEM_LIME_TERRACOTTA.get(),
            PLATE_ITEM_PINK_TERRACOTTA.get(),
            PLATE_ITEM_GRAY_TERRACOTTA.get(),
            PLATE_ITEM_LIGHT_GRAY_TERRACOTTA.get(),
            PLATE_ITEM_CYAN_TERRACOTTA.get(),
            PLATE_ITEM_PURPLE_TERRACOTTA.get(),
            PLATE_ITEM_BLUE_TERRACOTTA.get(),
            PLATE_ITEM_BROWN_TERRACOTTA.get(),
            PLATE_ITEM_GREEN_TERRACOTTA.get(),
            PLATE_ITEM_RED_TERRACOTTA.get(),
            PLATE_ITEM_BLACK_TERRACOTTA.get(),

            PLATE_ITEM_WHITE_CONCRETE.get(),
            PLATE_ITEM_ORANGE_CONCRETE.get(),
            PLATE_ITEM_MAGENTA_CONCRETE.get(),
            PLATE_ITEM_LIGHT_BLUE_CONCRETE.get(),
            PLATE_ITEM_YELLOW_CONCRETE.get(),
            PLATE_ITEM_LIME_CONCRETE.get(),
            PLATE_ITEM_PINK_CONCRETE.get(),
            PLATE_ITEM_GRAY_CONCRETE.get(),
            PLATE_ITEM_LIGHT_GRAY_CONCRETE.get(),
            PLATE_ITEM_CYAN_CONCRETE.get(),
            PLATE_ITEM_PURPLE_CONCRETE.get(),
            PLATE_ITEM_BLUE_CONCRETE.get(),
            PLATE_ITEM_BROWN_CONCRETE.get(),
            PLATE_ITEM_GREEN_CONCRETE.get(),
            PLATE_ITEM_RED_CONCRETE.get(),
            PLATE_ITEM_BLACK_CONCRETE.get(),
        };
    }

    // ========================================

    private static RegistryObject<PlateBlockBlockItem> registerPlateItem(
        PlateDefinition definition,
        RegistryObject<Block> block
    ) {
        return ITEMS.register(definition.item(),
            () -> new ForgePlateBlockBlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<PlateBlockBlockItem> registerPlateItem(
        PlateDefinition definition,
        RegistryObject<Block> block,
        int burnTime
    ) {
        return ITEMS.register(definition.item(),
            () -> new ForgePlateBlockBlockItem(block.get(), new Item.Properties(), burnTime));
    }

    // ========================================

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // ========================================

    public static Item icon() {
        return ICON.get();
    }
}
