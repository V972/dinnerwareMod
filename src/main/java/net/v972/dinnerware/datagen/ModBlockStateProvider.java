package net.v972.dinnerware.datagen;

import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DinnerwareMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Has Properties BlockStateProperties#HORIZONTAL_FACING, BlockStateProperties#WATERLOGGED
        this.getVariantBuilder(ModBlocks.PLATE_BLOCK.get()) // Get variant builder
            .forAllStatesExcept(state -> // For all HORIZONTAL_FACING states
                    ConfiguredModel.builder() // Creates configured model builder
                        .modelFile(
                            models().getExistingFile(modLoc("plate_block"))
                        ) // Can show 'modelFile'
                        .rotationY((int)state.getValue(HORIZONTAL_FACING).toYRot()) // Rotates 'modelFile' on the Y axis depending on the property
                        .build(), // Creates the array of configured models
                    WATERLOGGED); // Ignores WATERLOGGED property
        simpleBlockItem(ModBlocks.PLATE_BLOCK.get(),
                models().getExistingFile(modLoc("plate_block")));
    }
}
