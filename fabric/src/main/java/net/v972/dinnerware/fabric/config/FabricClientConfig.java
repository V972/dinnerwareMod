package net.v972.dinnerware.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "dinnerware-client")
public final class FabricClientConfig implements ConfigData {

    @Comment("""
        Number of item stacks shown in a tray tooltip.
        The rest will be omitted under "and N more...".
        Default: 5
        """)
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int maxTrayTooltipLines = 5;

    @Comment("""
        If enabled plates towers between ~5 and ~16 will render on the same height.
        Otherwise, the rendering height will not be corrected.
        Only affects first person perspective.
        Default: true
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean trayDynamicPlateOffset = true;

    @Comment("""
        Horizontal offset of the tray selection GUI.
        Currently unused.
        Default: 0
        """)
    @ConfigEntry.Gui.Excluded
    //@ConfigEntry.Gui.Tooltip
    public int trayGuiX = 0;

    @Comment("""
        Vertical offset of the tray selection GUI.
        Currently unused.
        Default: 0
        """)
    @ConfigEntry.Gui.Excluded
    //@ConfigEntry.Gui.Tooltip
    public int trayGuiY = 0;
}