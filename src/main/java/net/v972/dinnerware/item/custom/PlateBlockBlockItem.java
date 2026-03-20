package net.v972.dinnerware.item.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;

import java.util.function.Consumer;

public class PlateBlockBlockItem extends BlockItem {
    public PlateBlockBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return DinnerwareBEWLR.INSTANCE;
            }
        });
    }
}
