package net.v972.dinnerware.forge.config;

import net.neoforged.neoforge.common.ModConfigSpec;


public class ForgeClientConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue MAX_TRAY_TOOLTIP_LINES = BUILDER
            .comment(
                "\n" +
                "Number of item stacks to show in tray tooltip. \n" +
                "The rest will be omitted under \"and N more...\". \n" +
                "Default: 5"
            )
            .defineInRange("maxTrayTooltipLines", 5, 1, 64);

    public static final ModConfigSpec.BooleanValue TRAY_DYNAMIC_PLATE_OFFSET = BUILDER
            .comment(
                "\n" +
                "If enabled plates towers between ~5 and ~16 will render on the same height. \n" +
                "Otherwise, the rendering height will not be corrected. \n" +
                "Only affects first person perspective. \n" +
                "Default: true"
            )
            .define("trayDynamicPlateOffset", true);

    public static final ModConfigSpec.BooleanValue SHOW_EATING_PARTICLES = BUILDER
            .comment(
                "\n" +
                "Show food particles when eating from plates. \n" +
                "Default: true"
            )
            .define("showEatingParticles", true);;

//    public static final ModConfigSpec.IntValue TRAY_GUI_X = BUILDER
//            .comment(
//                    "\n" +
//                    "Horizontal offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//    public static final ModConfigSpec.IntValue TRAY_GUI_Y = BUILDER
//            .comment(
//                    "\n" +
//                    "Vertical offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
