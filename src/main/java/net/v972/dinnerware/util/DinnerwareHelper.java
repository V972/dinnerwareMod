package net.v972.dinnerware.util;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class DinnerwareHelper {

    /// "blocks.dinnerware.some_plate" -> "some_plate"
    public static @NotNull String getBlockId(Block block) {
        String[] pathComponents = block.getDescriptionId().split("\\.");
        return pathComponents[pathComponents.length-1];
    }


}
