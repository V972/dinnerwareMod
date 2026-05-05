package net.v972.dinnerware.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.util.DinnerwareHelper;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class DinnerwareBEWLR extends BlockEntityWithoutLevelRenderer {

    public DinnerwareBEWLR(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext pDisplayContext, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        boolean isPlate = stack.is(ModTags.Items.PLATES);
        boolean isTray = stack.is(ModTags.Items.TRAYS);

        if (isPlate && isTray) {
            throw new RuntimeException("ItemStack cannot be both Tray and Plate");
        }

        if (isPlate) {
            renderPlate(stack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        } else if (isTray) {
            renderTray(stack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }

    private void renderPlate(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        PlateBlock plateBlock = (PlateBlock)((PlateBlockBlockItem)stack.getItem()).getBlock();
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        BakedModel bakedModel = modelManager.getModel(
            BlockModelShaper.stateToModelLocation(plateBlock.defaultBlockState())
        );

        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.translate(0, 0, 0.485);
            poseStack.scale(0.625f, 0.625f, 0.625f);

            boolean isLeftHand = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
            itemRenderer.render(stack, ItemDisplayContext.FIXED, isLeftHand, poseStack, buffer, packedLight, packedOverlay, bakedModel);
        }
        poseStack.popPose();

        Direction facing = Direction.SOUTH;
        NonNullList<ItemStack> stacks = DinnerwareHelper.plateContentFromNBT(stack.getTag());
        int nonEmptyCount = DinnerwareHelper.getNonEmptySlotsCount(stacks);

        DinnerwareHelper.positionAndRenderPlateItems(poseStack, buffer, itemRenderer, stacks, nonEmptyCount, facing, null, packedLight);
    }

    private void renderTray(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        // Tray itself is rendered via mixin

        Direction facing = Direction.SOUTH;
        NonNullList<ItemStack> stacks = DinnerwareHelper.trayContentFromNBT(pStack.getTag());

        DinnerwareHelper.positionAndRenderTrayItems(pPoseStack, pBuffer, itemRenderer,
                stacks, facing, pDisplayContext, null, pPackedLight);
    }
}