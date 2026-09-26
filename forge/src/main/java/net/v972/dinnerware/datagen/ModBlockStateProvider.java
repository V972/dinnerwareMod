package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModBlocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.v972.dinnerware.block.custom.PlateBlock;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, DinnerwareConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(Block block : ForgeModBlocks.getKnownPlateBlocksIterable()) {
            PlateBlock plateBlock = (PlateBlock)block;

            ModelFile model = DinnerwareDatagenHelper.getPlateModelWithMaterial(plateBlock, models());

            this.getVariantBuilder(plateBlock)
                .forAllStatesExcept(state ->
                    ConfiguredModel
                        .builder()
                        .modelFile(model)
                        .rotationY((int)state.getValue(HORIZONTAL_FACING).toYRot())
                        .build(),
                    WATERLOGGED);
        }
    }
}
