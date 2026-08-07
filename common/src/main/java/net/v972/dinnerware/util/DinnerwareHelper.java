package net.v972.dinnerware.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DinnerwareHelper {

    /// Pair<horizontal, vertical> | 0.0-1.0
    public static Pair<Double, Double> getBlockHitPos(BlockHitResult pHit, Direction pBlockDir) {
        if (pHit.getDirection() == Direction.UP) {
            Vec3 loc = pHit.getLocation();
            double x_fr = fraction3Places(loc.x); // 0.000f
            double z_fr = fraction3Places(loc.z); // 0.000f

            double x_north_east = x_fr > 0 ? 1 - x_fr : Math.abs(x_fr);
            double x_south_west = x_fr > 0 ? x_fr : 1 - Math.abs(x_fr);

            double z_north_west = z_fr > 0 ? z_fr : 1 - Math.abs(z_fr);
            double z_south_east = z_fr > 0 ? 1 - z_fr : Math.abs(z_fr);

            double hor = switch (pBlockDir) {
                case NORTH -> x_north_east;
                case SOUTH -> x_south_west;
                case EAST -> z_south_east;
                case WEST -> z_north_west;
                default -> 0;
            };
            double vert = switch (pBlockDir) {
                case NORTH -> z_north_west;
                case SOUTH -> z_south_east;
                case EAST -> x_north_east;
                case WEST -> x_south_west;
                default -> 0;
            };

            return new Pair<>(hor, vert);
        }

        return new Pair<>(0.0, 0.0);
    }

    ///  ddddd...ddddd.fffff...fffff -> d.fff
    public static double fraction3Places(double a) { return (double) Math.round((a % 1) * 1000) / 1000; }

    /// "blocks.dinnerware.some_plate" -> "some_plate"
    public static @NotNull String getBlockId(Block block) {
        String[] pathComponents = block.getDescriptionId().split("\\.");
        return pathComponents[pathComponents.length-1];
    }

    // ===============================================================

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
