package net.v972.dinnerware.block.entity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.util.DinnerwareHelper;

public class PlateBlockBlockEntityRenderer implements BlockEntityRenderer<PlateBlockBlockEntity> {
    public PlateBlockBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(PlateBlockBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        NonNullList<ItemStack> stacks = pBlockEntity.getInventoryStacks();
        int nonEmptyCount = pBlockEntity.getNonEmptySlotsCount();
        Level beLevel = pBlockEntity.getLevel();
        int lightLevel = beLevel == null
                ? pPackedLight
                : DinnerwareHelper.getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos());

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        DinnerwareHelper.positionAndRenderPlateItems(pPoseStack, pBuffer, itemRenderer,
                stacks, nonEmptyCount, facing, beLevel, lightLevel);
    }
}
