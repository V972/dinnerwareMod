package net.v972.dinnerware.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.util.DinnerwareRegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.IdentityHashMap;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    // Tracks the ItemStack instances currently inside our BEWLR injection pipeline.
    // Prevents re-entry on the same object while allowing different instances to nest.
    // NOTE: This uses an identity-map directly, instead of using "Collections.newSetFromMap" to reduce overhead.
    @Unique private static final ThreadLocal<IdentityHashMap<ItemStack, Boolean>> ACTIVE_RENDER_CHAIN = ThreadLocal.withInitial(IdentityHashMap::new);

    // Renders the BEWLR after normal items
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V", shift = At.Shift.AFTER))
    public void injectAfterRenderModelLists(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci) {
        this.dinnerware$renderCustomBEWLR(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
    }

    // Renders the BEWLR after custom model items, e.g. entity builtin
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockEntityWithoutLevelRenderer;renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", shift = At.Shift.AFTER))
    public void injectAfterRenderByItem(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci) {
        this.dinnerware$renderCustomBEWLR(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
    }

    /**
     * Used to render custom {@code BEWLR} instances after any other item, meaning with this its possible to stack a json-model based item and a {@code BEWLR} on the same item.
     * <br/><br/>
     * Alternatively the item can still use {@code "parent": "builtin/entity"} if a fully {@code BEWLR} rendered item is desired.
     * <br/><br/>
     * Rendering a {@code BlockEntity} with this system remains unchanged to the way it would normally be done using a {@code BEWLR}.
     */
    @Unique private void dinnerware$renderCustomBEWLR(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        // Skips early if the item doesn't need to be handled by our BEWLR
        DinnerwareRegistryObject<BlockEntityWithoutLevelRenderer> renderer = DinnerwareBEWLRManager.get(stack.getItem());
        if (renderer == null) return;
        // Blocks if this exact instance is in the current call chain (prevents recursion)
        IdentityHashMap<ItemStack, Boolean> chain = ACTIVE_RENDER_CHAIN.get();
        if (chain.containsKey(stack)) return;
        // Lastly if renderer wasn't null and the stack is new we simply: push -> render -> pop
        chain.put(stack, Boolean.TRUE);
        try {
            renderer.get().renderByItem(stack, context, poseStack, buffer, combinedLight, combinedOverlay);
        } finally {
            chain.remove(stack);
        }
    }
}