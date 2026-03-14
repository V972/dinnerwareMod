package net.v972.dinnerware;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

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
                    "Otherwise, anything will fit. Rendering mileage may vary. \n" +
                    "Default: true"
            )
            .define("onlyFoodOnPlate", true);

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

//    private static final ForgeConfigSpec.IntValue TRAY_PLATE_PILE_MAX_SIZE = BUILDER
//            .comment(
//                    "\n Max number of plates that can be carried on a tray.\n" +
//                            " Set 0 for infinite amount. \n" +
//                            "Default: 8"
//            )
//            .defineInRange("trayPlatePileMaxSize", 8, 0, Integer.MAX_VALUE);

//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int maxPlateStackSize;
    public static boolean allowOverEating;
    public static boolean onlyFoodOnPlate;
    public static boolean fragilePlates;
    public static boolean rightToLeft;
    public static EATING_MODES eatingMode;
    //public static int trayPlatePileMaxSize;

//    public static String magicNumberIntroduction;
//    public static Set<Item> items;
//    private static boolean validateItemName(final Object obj)
//    {
//        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
//    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        maxPlateStackSize = MAX_PLATE_STACK_SIZE.get();
        allowOverEating = ALLOW_OVEREATING.get();
        onlyFoodOnPlate = ONLY_FOOD_ON_PLATE.get();
        fragilePlates = FRAGILE_PLATES.get();
        rightToLeft = RIGHT_TO_LEFT.get();
        eatingMode = EATING_MODE.get();
        //trayPlatePileMaxSize = TRAY_PLATE_PILE_MAX_SIZE.get();

//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }

    public enum EATING_MODES {
        QUEUE,
        ROUND_ROBIN,
        AIMING
    }
}
