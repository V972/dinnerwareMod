package net.v972.dinnerware.mixins_interfaces;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public interface IDinnerwareItemDynamicRendering {
    BlockEntityWithoutLevelRenderer dinnerware$getBEWLR();
    void dinnerware$setBEWLR(BlockEntityWithoutLevelRenderer renderer);
}