package net.v972.dinnerware.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.v972.dinnerware.block.custom.PlateBlock;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DinnerwareMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(Block block : ModBlocks.getKnownBlocks()) {
            PlateBlock plateBlock = (PlateBlock)block;

            ResourceLocation materialBlockName = ForgeRegistries.BLOCKS.getKey(plateBlock.MATERIAL);
            ResourceLocation finalTexture = getFinalTexture(materialBlockName, plateBlock);

            ModelFile model = models()
                    .getBuilder(getBlockId(block))
                    .parent(models().getExistingFile(modLoc("plate_block")))
                    .texture("0", finalTexture)
                    .texture("particle", finalTexture);

            this.getVariantBuilder(block)
                .forAllStatesExcept(state ->
                    ConfiguredModel
                        .builder()
                        .modelFile(model)
                        .rotationY((int)state.getValue(HORIZONTAL_FACING).toYRot())
                        .build(),
                    WATERLOGGED);
            simpleBlockItem(block, model);
        }
    }

    private static @NotNull ResourceLocation getFinalTexture(ResourceLocation materialBlockName, PlateBlock plateBlock) {
        ResourceLocation materialTexture = new ResourceLocation(materialBlockName.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + materialBlockName.getPath());
        ResourceLocation materialTextureTop = materialTexture.withSuffix("_top");

        ResourceLocation finalTexture =
                plateBlock.MATERIAL.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId()) ||
                plateBlock.MATERIAL instanceof RotatedPillarBlock
                        ? materialTextureTop
                        : materialTexture;
        return finalTexture;
    }

    private static @NotNull String getBlockId(Block block) {
        String[] pathComponents = block.getDescriptionId().split("\\.");
        return pathComponents[pathComponents.length-1];
    }
}
