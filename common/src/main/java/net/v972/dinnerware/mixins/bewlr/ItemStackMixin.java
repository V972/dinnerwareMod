package net.v972.dinnerware.mixins.bewlr;

import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemStackDynamicRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IDinnerwareItemStackDynamicRendering {
    @Unique private boolean dinnerware$isDynamicRendering;

    @Override
    public boolean dinnerware$isDynamicRendering() {
        return this.dinnerware$isDynamicRendering;
    }

    @Override
    public void dinnerware$setDynamicRendering(boolean value) {
        this.dinnerware$isDynamicRendering = value;
    }
}