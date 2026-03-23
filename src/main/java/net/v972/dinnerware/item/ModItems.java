package net.v972.dinnerware.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinnerwareMod.MOD_ID);

    public  static final RegistryObject<Item> ICON = ITEMS.register("dinnerware_icon",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> TRAY = ITEMS.register("tray",
            () -> new TrayItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BEDROCK = ITEMS.register("bedrock_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BEDROCK.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_QUARTZ = ITEMS.register("quartz_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_QUARTZ.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_QUARTZ = ITEMS.register("chiseled_quartz_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHISELED_QUARTZ.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_IRON = ITEMS.register("iron_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_IRON.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GOLD = ITEMS.register("gold_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GOLD.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DIAMOND = ITEMS.register("diamond_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_DIAMOND.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OBSIDIAN = ITEMS.register("obsidian_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_OBSIDIAN.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_POLISHED_BLACKSTONE = ITEMS.register("polished_blackstone_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_POLISHED_BLACKSTONE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHISELED_NETHER_BRICKS = ITEMS.register("chiseled_nether_bricks_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHISELED_NETHER_BRICKS.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPUR = ITEMS.register("purpur_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPUR.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_OAK = ITEMS.register("oak_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_OAK.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BIRCH = ITEMS.register("birch_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BIRCH.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_SPRUCE = ITEMS.register("spruce_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_SPRUCE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_JUNGLE = ITEMS.register("jungle_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_JUNGLE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ACACIA = ITEMS.register("acacia_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ACACIA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_DARK_OAK = ITEMS.register("dark_oak_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_DARK_OAK.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CHERRY = ITEMS.register("cherry_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CHERRY.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MANGROVE = ITEMS.register("mangrove_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MANGROVE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BAMBOO = ITEMS.register("bamboo_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BAMBOO.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CRIMSON = ITEMS.register("crimson_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CRIMSON.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WARPED = ITEMS.register("warped_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WARPED.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_TERRACOTTA = ITEMS.register("terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_TERRACOTTA.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_TERRACOTTA = ITEMS.register("white_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WHITE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_TERRACOTTA = ITEMS.register("orange_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ORANGE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_TERRACOTTA = ITEMS.register("magenta_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MAGENTA_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_TERRACOTTA = ITEMS.register("light_blue_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_TERRACOTTA = ITEMS.register("yellow_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_YELLOW_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_TERRACOTTA = ITEMS.register("lime_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIME_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_TERRACOTTA = ITEMS.register("pink_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PINK_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_TERRACOTTA = ITEMS.register("gray_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GRAY_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_TERRACOTTA = ITEMS.register("light_gray_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_TERRACOTTA = ITEMS.register("cyan_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CYAN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_TERRACOTTA = ITEMS.register("purple_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPLE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_TERRACOTTA = ITEMS.register("blue_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLUE_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_TERRACOTTA = ITEMS.register("brown_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BROWN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_TERRACOTTA = ITEMS.register("green_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GREEN_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_TERRACOTTA = ITEMS.register("red_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_RED_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_TERRACOTTA = ITEMS.register("black_terracotta_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLACK_TERRACOTTA.get(), new Item.Properties()));

    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_WHITE_CONCRETE = ITEMS.register("white_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_WHITE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_ORANGE_CONCRETE = ITEMS.register("orange_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_ORANGE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_MAGENTA_CONCRETE = ITEMS.register("magenta_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_MAGENTA_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_BLUE_CONCRETE = ITEMS.register("light_blue_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_BLUE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_YELLOW_CONCRETE = ITEMS.register("yellow_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_YELLOW_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIME_CONCRETE = ITEMS.register("lime_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIME_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PINK_CONCRETE = ITEMS.register("pink_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PINK_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GRAY_CONCRETE = ITEMS.register("gray_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GRAY_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_LIGHT_GRAY_CONCRETE = ITEMS.register("light_gray_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_LIGHT_GRAY_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_CYAN_CONCRETE = ITEMS.register("cyan_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_CYAN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_PURPLE_CONCRETE = ITEMS.register("purple_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_PURPLE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLUE_CONCRETE = ITEMS.register("blue_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLUE_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BROWN_CONCRETE = ITEMS.register("brown_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BROWN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_GREEN_CONCRETE = ITEMS.register("green_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_GREEN_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_RED_CONCRETE = ITEMS.register("red_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_RED_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<PlateBlockBlockItem> PLATE_ITEM_BLACK_CONCRETE = ITEMS.register("black_concrete_plate",
            () -> new PlateBlockBlockItem(ModBlocks.PLATE_BLOCK_BLACK_CONCRETE.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
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
