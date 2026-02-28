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
import net.v972.dinnerware.Config;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class PlateBlockBlockEntityRenderer implements BlockEntityRenderer<PlateBlockBlockEntity> {
    public PlateBlockBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(PlateBlockBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        positionItems(pBlockEntity, pPoseStack, pBuffer);
    }


    private void positionItems(PlateBlockBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        NonNullList<ItemStack> stacks = pBlockEntity.getInventoryItems();

        int nonEmptyCount = (int) stacks.stream().filter(item -> !item.isEmpty()).count();
        switch (nonEmptyCount) {
            case 1 -> {
                int firstNonEmptySlot = IntStream.range(0, stacks.size())
                    .filter(i -> !stacks.get(i).isEmpty())
                    .findFirst().getAsInt();
                renderItem(
                    pBlockEntity, pPoseStack,
                    itemRenderer, pBuffer,
                    stacks.get(firstNonEmptySlot), facing, 0.045f,
                    (PoseStack p) -> {}, 1
                );
            }
            case 2 -> positionTwoItems(
                    pBlockEntity, pPoseStack,
                    itemRenderer, pBuffer,
                    stacks, facing
            );
            case 3 -> positionThreeItems(
                    pBlockEntity, pPoseStack,
                    itemRenderer, pBuffer,
                    stacks, facing
            );
        }
    }

    private void positionTwoItems(PlateBlockBlockEntity pBlockEntity, PoseStack pPoseStack,
                                    ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                                    NonNullList<ItemStack> pStacks, Direction facing) {
        ItemStack extraDish = pStacks.get(2);
        if (!extraDish.isEmpty()) {
            renderItem(
                pBlockEntity, pPoseStack,
                pItemRenderer, pBuffer,
                extraDish, facing, 0.085f,
                this::positionExtraDish, 2
            );

            ItemStack leftOverDish = !pStacks.get(0).isEmpty()
                    ? pStacks.get(0)
                    : pStacks.get(1);
            int slot = pStacks.indexOf(leftOverDish);
            renderItem(
                    pBlockEntity, pPoseStack,
                    pItemRenderer, pBuffer,
                    leftOverDish, facing, 0.045f,
                    (PoseStack p) -> {}, slot
            );
        } else {
            boolean rtl = Config.rightToLeft;

            ItemStack mainDish = pStacks.get(0);
            renderItem(
                    pBlockEntity, pPoseStack,
                    pItemRenderer, pBuffer,
                    mainDish, facing, rtl ? 0.07f : 0.045f,
                    (PoseStack p) -> positionMainDish(p, false), 0
            );

            ItemStack sideDish = pStacks.get(1);
            renderItem(
                    pBlockEntity, pPoseStack,
                    pItemRenderer, pBuffer,
                    sideDish, facing, rtl ? 0.045f : 0.07f,
                    (PoseStack p) -> positionSideDish(p, false), 1
            );
        }
    }

    private void positionThreeItems(PlateBlockBlockEntity pBlockEntity, PoseStack pPoseStack,
                                    ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                                    NonNullList<ItemStack> pStacks, Direction facing) {

        boolean rtl = Config.rightToLeft;

        for (int pSlot = 0; pSlot < pBlockEntity.getInventorySize(); pSlot++) {
            float yLevel = switch (pSlot) {
                case 0 -> rtl ? 0.07f : 0.045f;
                case 1 -> rtl ? 0.045f : 0.07f;
                case 2 -> 0.085f;
                default -> 0.045f;
            };

            Consumer<PoseStack> precisePositioner = switch (pSlot) {
                case 0 -> (PoseStack p) -> {
                    if (rtl) positionSideDish(p, true);
                    else positionMainDish(p, true);
                };
                case 1 -> (PoseStack p) -> {
                    if (rtl) positionMainDish(p, true);
                    else positionSideDish(p, true);
                };
                case 2 -> this::positionExtraDish;
                default -> (PoseStack p) -> {};
            };

            renderItem(
                    pBlockEntity, pPoseStack,
                    pItemRenderer, pBuffer,
                    pStacks.get(pSlot), facing, yLevel,
                    precisePositioner, pSlot
            );
        }
    }

    private void renderItem(PlateBlockBlockEntity pBlockEntity, PoseStack pPoseStack,
                            ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                            ItemStack pStack, Direction facing,
                            float pY, Consumer<PoseStack> precisePositioner,
                            int pSeed) {
        pPoseStack.pushPose();

        // Move to the center
        pPoseStack.translate(0.5f, pY, 0.5f);

        // Rotate based on facing
        switch (facing) {
            case NORTH -> {} // pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
        }

        // Lie flat
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        // Precise positioning
        precisePositioner.accept(pPoseStack);

        // Scale down
        pPoseStack.scale(0.4f, 0.4f, 0.4f);

        pItemRenderer.renderStatic(pStack,
                ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), pSeed);
        pPoseStack.popPose();
    }

    private void positionSideDish(PoseStack pPoseStack, boolean lower) {
        // Move to plate edge
        pPoseStack.translate(-0.15f, lower ? -0.05f : 0f, -0.01f);

        // Rotate
        pPoseStack.mulPose(Axis.ZN.rotationDegrees(30));

        // Tilt against plate edge
        pPoseStack.mulPose(Axis.YN.rotationDegrees(15));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(15));
    }

    private void positionMainDish(PoseStack pPoseStack, boolean lower) {
        // Move to plate edge
        pPoseStack.translate(0.05f, lower ? -0.05f : 0f, -0.01f);

        // Rotate
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));

        // Tilt against plate edge
        pPoseStack.mulPose(Axis.YN.rotationDegrees(10));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(10));
    }

    private void positionExtraDish(PoseStack pPoseStack) {
        // Move to plate edge
        pPoseStack.translate(0f, 0.2f, -0.01f);

        // Rotate
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(40));

        // Tilt against plate edge
        pPoseStack.mulPose(Axis.YP.rotationDegrees(20));
        pPoseStack.mulPose(Axis.XN.rotationDegrees(20));
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
