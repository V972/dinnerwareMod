package net.v972.dinnerware.block.entity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;

public class PlateBlockBlockEntityRenderer implements BlockEntityRenderer<PlateBlockBlockEntity> {
    public PlateBlockBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(PlateBlockBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        NonNullList<ItemStack> stacks = pBlockEntity.getRenderStacks();

        for (int i = 0; i < pBlockEntity.getContainerSize(); i++) {
            positionItemBySlot(
                    pBlockEntity, pPoseStack,
                    itemRenderer, pBuffer,
                    stacks.get(i), i, facing);
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    private void positionItemBySlot(PlateBlockBlockEntity pBlockEntity, PoseStack pPoseStack,
                                    ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                                    ItemStack pStack, int pSlot, Direction facing) {
        pPoseStack.pushPose();

        // Move to the center
        switch(pSlot) {
            case 0 -> pPoseStack.translate(0.5f, 0.07f, 0.5f);
            case 1 -> pPoseStack.translate(0.5f, 0.045f, 0.5f);
            case 2 -> pPoseStack.translate(0.5f, 0.085f, 0.5f);
        }

        // Rotate based on facing
        switch (facing) {
            case NORTH -> {} // pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
        }

        // Lie flat
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        // Move to the "corner" of the plate based on slot
        switch(pSlot) {
            case 0 -> {
                // Move to plate edge
                pPoseStack.translate(-0.15f, -0.1f, 0f);

                // Rotate
                pPoseStack.mulPose(Axis.ZN.rotationDegrees(30));

                // Tilt against plate edge
                pPoseStack.mulPose(Axis.YN.rotationDegrees(15));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(15));
            }
            case 1 -> {
                // Move to plate edge
                pPoseStack.translate(0.05f, -0.05f, 0f);

                // Rotate
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));

                // Tilt against plate edge
                pPoseStack.mulPose(Axis.YN.rotationDegrees(10));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(10));
            }
            case 2 -> {
                // Move to plate edge
                pPoseStack.translate(0f, 0.2f, 0f);

                // Rotate
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(45));

                // Tilt against plate edge
                pPoseStack.mulPose(Axis.YP.rotationDegrees(20));
                pPoseStack.mulPose(Axis.XN.rotationDegrees(20));
            }
        }

        // Scale down
        pPoseStack.scale(0.4f, 0.4f, 0.4f);

        pItemRenderer.renderStatic(pStack,
                ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), pSlot);
        pPoseStack.popPose();
    }
}
