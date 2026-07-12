package net.v972.dinnerware.client.animation;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// Might have been _inspired by_ MoonlightLib's approach
public interface DinnerwareThirdPersonAnimationProvider {
    boolean poseRightArm(ItemStack stack, HumanoidModel<?> model, LivingEntity entity, HumanoidArm mainArm);

    boolean poseLeftArm(ItemStack stack, HumanoidModel<?> model, LivingEntity entity, HumanoidArm mainArm);

    default boolean isTwoHanded() {
        return false;
    }
}