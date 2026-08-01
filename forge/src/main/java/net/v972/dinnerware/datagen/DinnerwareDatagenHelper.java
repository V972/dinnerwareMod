package net.v972.dinnerware.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.util.DinnerwareHelper;
import org.jetbrains.annotations.NotNull;

public final class DinnerwareDatagenHelper {
    private DinnerwareDatagenHelper() {
    }

    public static ModelFile getPlateModelWithMaterial(PlateBlock plateBlock, BlockModelProvider models) {
        ResourceLocation finalTexture = getTextureForModel(plateBlock.MATERIAL);

        ResourceLocation parentModelLoc =
            DinnerwareConstants.id(
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

    public static @NotNull ResourceLocation getTextureForModel(Block pMaterial) {
        ResourceLocation materialBlockName = ForgeRegistries.BLOCKS.getKey(pMaterial);

        boolean isColumn =
            pMaterial.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId()) ||
            pMaterial instanceof RotatedPillarBlock;

        ResourceLocation materialTexture = ResourceLocation
            .fromNamespaceAndPath(
                materialBlockName.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + materialBlockName.getPath());
        ResourceLocation materialTextureTop = materialTexture.withSuffix("_top");

        return isColumn
            ? materialTextureTop
            : materialTexture;
    }
}