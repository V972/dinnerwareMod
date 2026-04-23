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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
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
    public void renderByItem(ItemStack pStack, @NotNull ItemDisplayContext pDisplayContext, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Optional<PlateBlockBlockItem> plateBlockItem = Arrays.stream(ModItems.getPlateItemsArray()).filter(pStack::is).findFirst();
        Optional<TrayItem> trayItem = Arrays.stream(ModItems.getTrayItemsArray()).filter(pStack::is).findFirst();

        if (plateBlockItem.isPresent() && trayItem.isPresent()) {
            throw new RuntimeException("ItemStack cannot be both Tray and Plate");
        }

        if (plateBlockItem.isEmpty() && trayItem.isEmpty()) {
            super.renderByItem(pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        } else if (plateBlockItem.isPresent()) {
            renderPlate(plateBlockItem.get(), pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        } else if (trayItem.isPresent()) {
            renderTray(trayItem.get(), pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }

    private void renderPlate(PlateBlockBlockItem pPlateItem, ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        PlateBlock plateBlock = (PlateBlock)pPlateItem.getBlock();
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
        NonNullList<ItemStack> stacks = DinnerwareHelper.plateContentFromNBT(pStack.getTag());
        int nonEmptyCount = DinnerwareHelper.getNonEmptySlotsCount(stacks);

        DinnerwareHelper.positionAndRenderPlateItems(pPoseStack, pBuffer, itemRenderer,
                stacks, nonEmptyCount, facing, null, pPackedLight);
    }

    private void renderTray(TrayItem pTrayItem, ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        BakedModel bakedModel = modelManager.getModel(
            Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(pTrayItem))
        );

//        pPoseStack.pushPose();
//        {
//            pPoseStack.translate(0.5, 0.5, 0.5);
//            pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
//            pPoseStack.translate(0, 0, 0.485);
//            pPoseStack.scale(0.625f, 0.625f, 0.625f);
//
//            boolean isLeftHand =
//                    pDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
//                            pDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
//            //                          pDisplayContext
//            itemRenderer.render(pStack, ItemDisplayContext.FIXED, isLeftHand, pPoseStack, pBuffer,
//                pPackedLight, pPackedOverlay, bakedModel
//            );
//        }
//        pPoseStack.popPose();

        Direction facing = Direction.SOUTH;
        NonNullList<ItemStack> stacks = DinnerwareHelper.trayContentFromNBT(pStack.getTag());

        DinnerwareHelper.positionAndRenderTrayItems(pPoseStack, pBuffer, itemRenderer,
                stacks, facing, null, pPackedLight);
    }
}
