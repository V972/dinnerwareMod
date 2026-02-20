package net.v972.dinnerware;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = DinnerwareMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue TRAY_PLATE_PILE_MAX_SIZE = BUILDER
            .comment(
                    " Max number of plates that can be carried on a tray.\n" +
                    " Set 0 for infinite amount.\n" +
                    "Default: 4"
            )
            .defineInRange("trayPlatePileMaxSize", 4, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.BooleanValue FRAGILE_PLATES = BUILDER
            .comment(
                    " If set to true, plates become fragile\n" +
                    " i.e. break and drop their content under various circumstances\n" +
                    "Default: false"
            )
            .define("fragilePlates", false);

//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int trayPlatePileMaxSize;
    public static boolean fragilePlates;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;

//    private static boolean validateItemName(final Object obj)
//    {
//        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
//    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        trayPlatePileMaxSize = TRAY_PLATE_PILE_MAX_SIZE.get();
        fragilePlates = FRAGILE_PLATES.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
