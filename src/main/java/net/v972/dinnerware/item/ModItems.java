package net.v972.dinnerware.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinnerwareCommon.MOD_ID);

    public  static final RegistryObject<Item> ICON = ITEMS.register(DinnerwareRegistryNames.Items.DINNERWARE_ICON,
            () -> new Item(new Item.Properties().fireResistant()));


    public static final RegistryObject<TrayItem> TRAY_IRON = ITEMS.register(DinnerwareRegistryNames.Items.IRON_TRAY,
            () -> new TrayItem(Blocks.IRON_BLOCK, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<TrayItem> TRAY_GOLD = ITEMS.register(DinnerwareRegistryNames.Items.GOLD_TRAY,
            () -> new TrayItem(Blocks.GOLD_BLOCK, new Item.Properties().stacksTo(1)));


    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BEDROCK = ITEMS.register(PlateDefinition.BEDROCK.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BEDROCK.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_QUARTZ = ITEMS.register(PlateDefinition.QUARTZ.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_QUARTZ.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_QUARTZ = ITEMS.register(PlateDefinition.CHISELED_QUARTZ.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHISELED_QUARTZ.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_IRON = ITEMS.register(PlateDefinition.IRON.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_IRON.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GOLD = ITEMS.register(PlateDefinition.GOLD.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GOLD.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DIAMOND = ITEMS.register(PlateDefinition.DIAMOND.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_DIAMOND.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OBSIDIAN = ITEMS.register(PlateDefinition.OBSIDIAN.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_OBSIDIAN.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_POLISHED_BLACKSTONE = ITEMS.register(PlateDefinition.POLISHED_BLACKSTONE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_POLISHED_BLACKSTONE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_NETHER_BRICKS = ITEMS.register(PlateDefinition.CHISELED_NETHER_BRICKS.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHISELED_NETHER_BRICKS.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPUR = ITEMS.register(PlateDefinition.PURPUR.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPUR.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OAK = ITEMS.register(PlateDefinition.OAK.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_OAK.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BIRCH = ITEMS.register(PlateDefinition.BIRCH.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BIRCH.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_SPRUCE = ITEMS.register(PlateDefinition.SPRUCE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_SPRUCE.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_JUNGLE = ITEMS.register(PlateDefinition.JUNGLE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_JUNGLE.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ACACIA = ITEMS.register(PlateDefinition.ACACIA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ACACIA.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DARK_OAK = ITEMS.register(PlateDefinition.DARK_OAK.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_DARK_OAK.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHERRY = ITEMS.register(PlateDefinition.CHERRY.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHERRY.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MANGROVE = ITEMS.register(PlateDefinition.MANGROVE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MANGROVE.get(), new Item.Properties(), 150));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BAMBOO = ITEMS.register(PlateDefinition.BAMBOO.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BAMBOO.get(), new Item.Properties(), 150));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CRIMSON = ITEMS.register(PlateDefinition.CRIMSON.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CRIMSON.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WARPED = ITEMS.register(PlateDefinition.WARPED.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WARPED.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_TERRACOTTA = ITEMS.register(PlateDefinition.TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_TERRACOTTA.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_TERRACOTTA = ITEMS.register(PlateDefinition.WHITE_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WHITE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_TERRACOTTA = ITEMS.register(PlateDefinition.ORANGE_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ORANGE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_TERRACOTTA = ITEMS.register(PlateDefinition.MAGENTA_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MAGENTA_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_TERRACOTTA = ITEMS.register(PlateDefinition.LIGHT_BLUE_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_TERRACOTTA = ITEMS.register(PlateDefinition.YELLOW_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_YELLOW_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_TERRACOTTA = ITEMS.register(PlateDefinition.LIME_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIME_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_TERRACOTTA = ITEMS.register(PlateDefinition.PINK_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PINK_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_TERRACOTTA = ITEMS.register(PlateDefinition.GRAY_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GRAY_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_TERRACOTTA = ITEMS.register(PlateDefinition.LIGHT_GRAY_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_TERRACOTTA = ITEMS.register(PlateDefinition.CYAN_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CYAN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_TERRACOTTA = ITEMS.register(PlateDefinition.PURPLE_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPLE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_TERRACOTTA = ITEMS.register(PlateDefinition.BLUE_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLUE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_TERRACOTTA = ITEMS.register(PlateDefinition.BROWN_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BROWN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_TERRACOTTA = ITEMS.register(PlateDefinition.GREEN_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GREEN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_TERRACOTTA = ITEMS.register(PlateDefinition.RED_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_RED_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_TERRACOTTA = ITEMS.register(PlateDefinition.BLACK_TERRACOTTA.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLACK_TERRACOTTA.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_CONCRETE = ITEMS.register(PlateDefinition.WHITE_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WHITE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_CONCRETE = ITEMS.register(PlateDefinition.ORANGE_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ORANGE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_CONCRETE = ITEMS.register(PlateDefinition.MAGENTA_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MAGENTA_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_CONCRETE = ITEMS.register(PlateDefinition.LIGHT_BLUE_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_BLUE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_CONCRETE = ITEMS.register(PlateDefinition.YELLOW_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_YELLOW_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_CONCRETE = ITEMS.register(PlateDefinition.LIME_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIME_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_CONCRETE = ITEMS.register(PlateDefinition.PINK_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PINK_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_CONCRETE = ITEMS.register(PlateDefinition.GRAY_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GRAY_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_CONCRETE = ITEMS.register(PlateDefinition.LIGHT_GRAY_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_GRAY_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_CONCRETE = ITEMS.register(PlateDefinition.CYAN_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CYAN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_CONCRETE = ITEMS.register(PlateDefinition.PURPLE_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPLE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_CONCRETE = ITEMS.register(PlateDefinition.BLUE_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLUE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_CONCRETE = ITEMS.register(PlateDefinition.BROWN_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BROWN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_CONCRETE = ITEMS.register(PlateDefinition.GREEN_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GREEN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_CONCRETE = ITEMS.register(PlateDefinition.RED_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_RED_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_CONCRETE = ITEMS.register(PlateDefinition.BLACK_CONCRETE.item(),
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLACK_CONCRETE.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

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
        return Stream.of(getPlateItemsArray())
            .filter(plate -> plate != PLATE_ITEM_BEDROCK.get())
            .toArray(Item[]::new);
    }

    public static Set<Item> getPlateItemsSet() {
        return Stream.of(getPlateItemsArray())
            .collect(Collectors.toSet());
    }

    public static PlateBlockBlockItem[] getPlateItemsArray() {
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
}
