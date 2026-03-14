package net.v972.dinnerware.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.custom.PlateBlock;
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

    public static ModelFile getPlateModelWithMaterial(PlateBlock plateBlock, BlockModelProvider models) {
        ResourceLocation materialBlockName = ForgeRegistries.BLOCKS.getKey(plateBlock.MATERIAL);

        boolean isColumn =
                plateBlock.MATERIAL.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId()) ||
                plateBlock.MATERIAL instanceof RotatedPillarBlock;

        ResourceLocation finalTexture = getFinalTexture(materialBlockName, isColumn);

        ResourceLocation parentModelLoc =
                ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID,
                        plateBlock.MATERIAL instanceof RotatedPillarBlock &&
                        !plateBlock.MATERIAL.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId())
                            ? "plate_block_column"
                            : "plate_block"
                );

        return models
                .getBuilder(DinnerwareHelper.getBlockId(plateBlock))
                .parent(models.getExistingFile(parentModelLoc))
                .texture("0", finalTexture)
                .texture("particle", finalTexture);
    }

    private static @NotNull ResourceLocation getFinalTexture(ResourceLocation materialBlockName, boolean isColumn) {
        ResourceLocation materialTexture = ResourceLocation.fromNamespaceAndPath(materialBlockName.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + materialBlockName.getPath());
        ResourceLocation materialTextureTop = materialTexture.withSuffix("_top");

        return isColumn
            ? materialTextureTop
            : materialTexture;
    }
}
