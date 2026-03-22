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
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.util.DinnerwareHelper;

import java.util.Arrays;
import java.util.Optional;

public class DinnerwareBEWLR extends BlockEntityWithoutLevelRenderer {
    public static final DinnerwareBEWLR INSTANCE = new DinnerwareBEWLR(
        Minecraft.getInstance().getBlockEntityRenderDispatcher(),
        Minecraft.getInstance().getEntityModels()
    );

    public DinnerwareBEWLR(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Optional<PlateBlockBlockItem> item = Arrays.stream(ModItems.getPlateItemsArray()).filter(pStack::is).findFirst();
        if (item.isEmpty()) {
            super.renderByItem(pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        } else {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            PlateBlockBlockItem plateItem = item.get();
            PlateBlock plateBlock = (PlateBlock)plateItem.getBlock();
            ModelManager modelManager = Minecraft.getInstance().getModelManager();
            BakedModel bakedModel = modelManager.getModel(
                BlockModelShaper.stateToModelLocation(plateBlock.defaultBlockState())
            );

            pPoseStack.pushPose();
            {
                pPoseStack.translate(0.5, 0.5, 0.5);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
                pPoseStack.translate(0, 0, 0.485);
                pPoseStack.scale(0.625f, 0.625f, 0.625f);

                boolean isLeftHand =
                        pDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
                        pDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
                //                          pDisplayContext
                itemRenderer.render(pStack, ItemDisplayContext.FIXED, isLeftHand, pPoseStack, pBuffer,
                        pPackedLight, pPackedOverlay, bakedModel);
            }
            pPoseStack.popPose();

            Direction facing = Direction.SOUTH;
            NonNullList<ItemStack> stacks = DinnerwareHelper.fromNBT(pStack.getTag());
            int nonEmptyCount = DinnerwareHelper.getNonEmptySlotsCount(stacks);

            DinnerwareHelper.positionAndRenderPlateItems(pPoseStack, pBuffer, itemRenderer,
                    stacks, nonEmptyCount, facing, null, pPackedLight);
        }
    }
}
