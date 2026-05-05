package net.v972.dinnerware.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import net.v972.dinnerware.DinnerwareMod;

@Mod.EventBusSubscriber(modid = DinnerwareMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue MAX_TRAY_TOOLTIP_LINES = BUILDER
            .comment(
                    "\n" +
                            "Number of item stacks to show in tray's tooltip. \n" +
                            "The rest will be omitted under \"and N more...\". \n" +
                            "Default: 5"
            )
            .defineInRange("maxTrayTooltipLines", 5, 1, 64);

    public static final ForgeConfigSpec.BooleanValue TRAY_DYNAMIC_PLATE_OFFSET = BUILDER
            .comment(
                    "\n" +
                            "If enabled plates towers between ~5 and ~16 will render on the same height. \n" +
                            "Otherwise, the rendering height will not be corrected. \n" +
                            "Only affects first person perspective. \n" +
                            "Default: true"
            )
            .define("trayDynamicPlateOffset", true);

//    public static final ForgeConfigSpec.IntValue TRAY_GUI_X = BUILDER
//            .comment(
//                    "\n" +
//                    "Horizontal offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//    public static final ForgeConfigSpec.IntValue TRAY_GUI_Y = BUILDER
//            .comment(
//                    "\n" +
//                    "Vertical offset of Tray Selection GUI \n" +
//                    "Default: 0"
//            )
//            .defineInRange("trayGuiY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
