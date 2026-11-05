package net.v972.dinnerware.mixins.bewlr;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemDynamicRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique private static boolean ATTACHED_RENDERERS;

    // This method is a good place to attach our BEWLRs to their items,
    // it runs after Forge's enqueued client init, and before game rendering.
    @Inject(method = "reloadShaders", at = @At("TAIL"))
    public void onShaderReload(ResourceProvider resourceProvider, CallbackInfo ci) {
        // NOTE: Since this method gets called on every reload, we have to ensure the BEWLRs are only attached once
        if (!ATTACHED_RENDERERS) {
            DinnerwareBEWLRManager.RENDERERS.forEach((item, factory) -> {
                ((IDinnerwareItemDynamicRendering) item).dinnerware$setBEWLR(factory.get());
            });
            ATTACHED_RENDERERS = true;
        }
    }
}