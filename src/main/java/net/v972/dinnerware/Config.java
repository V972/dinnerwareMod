package net.v972.dinnerware;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = DinnerwareMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAX_PLATE_STACK_SIZE = BUILDER
            .comment(
                    "\n" +
                    "Maximum amount of items one slot of a plate can hold. \n" +
                    "Does not override item's max stack size.\n " +
                    "Default: 16"
            )
            .defineInRange("maxPlateStackSize", 16, 1, 64);

    private static final ForgeConfigSpec.BooleanValue ALLOW_OVEREATING = BUILDER
            .comment(
                    "\n" +
                    "If set to true, allow player to keep eating off the plate, even if they're not hungry. \n" +
                    "Food buffs/debuffs are still applied.\n" +
                    "Default: false"
            )
            .define("allowOvereating", false);

    private static final ForgeConfigSpec.BooleanValue ONLY_FOOD_ON_PLATE = BUILDER
            .comment(
                    "\n" +
                    "If true, only food items & items inside \"dinnerware:additional_food\" tag are allowed to be placed on plates. \n" +
                    "Otherwise, anything will fit. Your rendering mileage may vary. \n" +
                    "Default: true"
            )
            .define("onlyFoodOnPlate", true);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> FOOD_BLACKLIST = BUILDER
            .comment(
                    "\n" +
                    "A list of items to forbid from being eaten, even if they're part of \"dinnerware:additional_food\" tag. \n" +
                    "Items listed here may still be placed on a plate. \n" +
                    "Default: [\"artifacts:everlasting_beef\", \"artifacts:eternal_steak\"]"
            )
            .defineListAllowEmpty("foodBlacklist", List.of(
                    "artifacts:everlasting_beef",
                    "artifacts:eternal_steak"
            ), Config::validateItemName);

    private static final ForgeConfigSpec.BooleanValue FRAGILE_PLATES = BUILDER
            .comment(
                    "\n" +
                    "If set to true, plates become fragile, i.e. break under various circumstances. \n" +
                    "Like walking on them. \n" +
                    "Default: false"
            )
            .define("fragilePlates", false);

    private static final ForgeConfigSpec.BooleanValue RIGHT_TO_LEFT = BUILDER
            .comment(
                    "\n" +
                    "If set to true, the bottom right slot becomes the main one. \n" +
                    "The plate is basically gets mirrored. \n" +
                    "Be careful when changing this with already existing plates in the world. \n" +
                    "Default: false"
            )
            .define("rightToLeft", false);

    private static final ForgeConfigSpec.EnumValue<EATING_MODES> EATING_MODE = BUILDER
            .comment(
                    "\n" +
                    "The mode in which the food will be eaten off of a plate. \n" +
                    " QUEUE:       all the food is eaten in the Main dish slot first, then in Side dish slot, then in Extra dish slot; \n" +
                    " ROUND_ROBIN: each new right-click will take the food from the new slot. Clicks with no food eaten still count; \n" +
                    " AIMING:      the food is eaten depending on where and crosshair is aimed at the time of right-click; \n" +
                    "Default: AIMING"
            )
            .defineEnum("eatingMode", EATING_MODES.AIMING);

    private static final ForgeConfigSpec.BooleanValue TRAY_MERGE_MATCHING_ITEM = BUILDER
            .comment(
                    "\n" +
                    "If set to true, the tray will behave like a bundle: \n" +
                    "If there's a matching plates stack, the picked up one will be merged into it. \n" +
                    "Otherwise, the merging only happens for the topmost plate. \n" +
                    "Default: false"
            )
            .define("trayMergeMatchingItem", false);

    private static final ForgeConfigSpec.IntValue MAX_TRAY_TOOLTIP_LINES = BUILDER
            .comment(
                    "\n" +
                    "Number of item stacks to show in tray's tooltip. \n" +
                    "The rest will be omitted under \"and N more...\". \n" +
                    "Default: 5"
            )
            .defineInRange("maxTrayTooltipLines", 5, 1, 64);

//    private static final ForgeConfigSpec.IntValue TRAY_GUI_X = BUILDER
//            .comment(
//                    "\n" +
//                    "Horizontal offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//    private static final ForgeConfigSpec.IntValue TRAY_GUI_Y = BUILDER
//            .comment(
//                    "\n" +
//                    "Vertical offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int maxPlateStackSize;
    public static boolean allowOverEating;
    public static boolean onlyFoodOnPlate;
    public static boolean fragilePlates;
    public static boolean rightToLeft;
    public static EATING_MODES eatingMode;
    public static boolean trayMergeMatchingItem;
    public static int maxTrayTooltipLines;
    public static Set<ResourceLocation> foodBlacklist;
//    public static int trayGuiX;
//    public static int trayGuiY;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        maxPlateStackSize = MAX_PLATE_STACK_SIZE.get();
        allowOverEating = ALLOW_OVEREATING.get();
        onlyFoodOnPlate = ONLY_FOOD_ON_PLATE.get();
        fragilePlates = FRAGILE_PLATES.get();
        rightToLeft = RIGHT_TO_LEFT.get();
        eatingMode = EATING_MODE.get();
        trayMergeMatchingItem = TRAY_MERGE_MATCHING_ITEM.get();
        maxTrayTooltipLines = MAX_TRAY_TOOLTIP_LINES.get();
        foodBlacklist = FOOD_BLACKLIST.get().stream()
                .map(ResourceLocation::parse)
                .collect(Collectors.toSet());
//        trayGuiX = TRAY_GUI_X.get();
//        trayGuiY = TRAY_GUI_Y.get();
    }

    private static boolean validateItemName(final Object obj)
    {
        if (!(obj instanceof final String itemName)) return false;
        var resLoc = ResourceLocation.parse(itemName);
        return
            !ModList.get().isLoaded(resLoc.getNamespace()) ||
            ForgeRegistries.ITEMS.containsKey(resLoc);
    }

    public static boolean isInFoodBlacklist(final ItemStack stack) {
        return isInFoodBlacklist(stack.getItem());
    }

    public static boolean isInFoodBlacklist(final Item item) {
        return foodBlacklist.contains(ForgeRegistries.ITEMS.getKey(item));
    }


    public enum EATING_MODES {
        QUEUE,
        ROUND_ROBIN,
        AIMING
    }
}
