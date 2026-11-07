package net.v972.dinnerware.mixins.bewlr;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemDynamicRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class ItemMixin implements IDinnerwareItemDynamicRendering {
    @Unique private BlockEntityWithoutLevelRenderer dinnerware$BEWLR;

    @Override
    public BlockEntityWithoutLevelRenderer dinnerware$getBEWLR() {
        return this.dinnerware$BEWLR;
    }

    @Override
    public void dinnerware$setBEWLR(BlockEntityWithoutLevelRenderer renderer) {
        this.dinnerware$BEWLR = renderer;
    }
}