package net.v972.dinnerware.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.mixins.LivingEntityAccessor;

public final class EatingParticleClientHandler {
    private EatingParticleClientHandler() {
    }

    public static void handle(int entityId, ItemStack foodStack) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || !DinnerwareConfig.showEatingParticles()) return;

        Entity entity = minecraft.level.getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            ((LivingEntityAccessor)livingEntity).dinnerware$spawnItemParticles(foodStack, 5); // like regular eating
        }
    }
}