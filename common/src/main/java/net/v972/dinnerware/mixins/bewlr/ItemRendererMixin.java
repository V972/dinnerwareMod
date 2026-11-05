package net.v972.dinnerware.mixins.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemDynamicRendering;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemStackDynamicRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    // Renders the BEWLR after normal items
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V", shift = At.Shift.AFTER))
    public void injectAfterRenderModelLists(ItemStack stack, ItemTransforms.TransformType context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci) {
        this.dinnerware$renderCustomBEWLR(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
    }

    // Renders the BEWLR after custom model items, e.g. entity builtin
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockEntityWithoutLevelRenderer;renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", shift = At.Shift.AFTER))
    public void injectAfterRenderByItem(ItemStack stack, ItemTransforms.TransformType context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci) {
        this.dinnerware$renderCustomBEWLR(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
    }

    /**
     * Used to render custom {@code BEWLR} instances after any other item, meaning with this its possible to stack a json-model based item and a {@code BEWLR} on the same item.
     * <p>
     * Alternatively the item can still use {@code "parent": "builtin/entity"} if a fully {@code BEWLR} rendered item is desired.
     * </p>
     * Rendering a {@code BlockEntity} with this system remains unchanged to the way it would normally be done using a {@code BEWLR}.
     */
    @Unique private void dinnerware$renderCustomBEWLR(ItemStack stack, ItemTransforms.TransformType context, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        // Skips early if the item doesn't need to be handled by our BEWLR
        BlockEntityWithoutLevelRenderer renderer = ((IDinnerwareItemDynamicRendering)stack.getItem()).dinnerware$getBEWLR();
        if (renderer == null) return;
        // Blocks if this instance has already been rendered
        IDinnerwareItemStackDynamicRendering dynamicRendering = (IDinnerwareItemStackDynamicRendering)(Object)stack;
        if (dynamicRendering.dinnerware$isDynamicRendering()) return;
        // Lastly if renderer wasn't null and the stack is new we simply: add flag -> render -> remove flag
        dynamicRendering.dinnerware$setDynamicRendering(true);
        try {
            renderer.renderByItem(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
        } finally {
            dynamicRendering.dinnerware$setDynamicRendering(false);
        }
    }
}