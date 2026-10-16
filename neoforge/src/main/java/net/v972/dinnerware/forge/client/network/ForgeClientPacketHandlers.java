package net.v972.dinnerware.forge.client.network;

import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.client.network.EatingParticleClientHandler;

public final class ForgeClientPacketHandlers {
    private ForgeClientPacketHandlers() {
    }

    public static void handleEatingParticles(int entityId, ItemStack foodStack) {
        EatingParticleClientHandler.handle(entityId, foodStack);
    }
}