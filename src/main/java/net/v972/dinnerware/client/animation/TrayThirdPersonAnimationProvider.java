package net.v972.dinnerware.client.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class TrayThirdPersonAnimationProvider implements DinnerwareThirdPersonAnimationProvider {
    @Override
    public boolean poseRightArm(ItemStack stack, HumanoidModel<?> model, LivingEntity entity, HumanoidArm mainArm) {
        float ageInTicks = entity.tickCount + getPartialTick();

        model.rightArm.xRot = -((float) Math.PI / 8F);
        AnimationUtils.bobModelPart(model.rightArm, ageInTicks, -1.0F);  // * -1 from regular

        return true;
    }

    @Override
    public boolean poseLeftArm(ItemStack stack, HumanoidModel<?> model, LivingEntity entity, HumanoidArm mainArm) {
        float ageInTicks = entity.tickCount + getPartialTick();

        model.leftArm.xRot = -((float) Math.PI / 8F);
        AnimationUtils.bobModelPart(model.leftArm, ageInTicks, 1.0F);  // * -1 from regular

        return true;
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    /** @return The current partialTick. */
    private static float getPartialTick() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.isPaused() ? minecraft.pausePartialTick : minecraft.getFrameTime();
    }
}