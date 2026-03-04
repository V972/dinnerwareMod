package net.v972.dinnerware.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.custom.PlateBlock;
import org.jetbrains.annotations.NotNull;

public class DinnerwareHelper {

    /// "blocks.dinnerware.some_plate" -> "some_plate"
    public static @NotNull String getBlockId(Block block) {
        String[] pathComponents = block.getDescriptionId().split("\\.");
        return pathComponents[pathComponents.length-1];
    }

    public static ModelFile getPlateModelWithMaterial(PlateBlock plateBlock, BlockModelProvider models) {
        ResourceLocation materialBlockName = ForgeRegistries.BLOCKS.getKey(plateBlock.MATERIAL);
        ResourceLocation finalTexture = getFinalTexture(materialBlockName, plateBlock);

        return models
                .getBuilder(DinnerwareHelper.getBlockId(plateBlock))
                .parent(models.getExistingFile(ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "plate_block")))
                .texture("0", finalTexture)
                .texture("particle", finalTexture);
    }

    private static @NotNull ResourceLocation getFinalTexture(ResourceLocation materialBlockName, PlateBlock plateBlock) {
        ResourceLocation materialTexture = ResourceLocation.fromNamespaceAndPath(materialBlockName.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + materialBlockName.getPath());
        ResourceLocation materialTextureTop = materialTexture.withSuffix("_top");

        return plateBlock.MATERIAL.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId()) ||
                plateBlock.MATERIAL instanceof RotatedPillarBlock
                ? materialTextureTop
                : materialTexture;
    }
}
