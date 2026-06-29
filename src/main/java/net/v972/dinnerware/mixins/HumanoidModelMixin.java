package net.v972.dinnerware.mixins;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.client.animation.DinnerwareItemAnimations;
import net.v972.dinnerware.client.animation.DinnerwareThirdPersonAnimationProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void dinnerware$poseRightArm(LivingEntity entity, CallbackInfo callbackInfo) {
        ProviderLookup lookup = dinnerwareMod$getProviderForArm(entity, HumanoidArm.RIGHT);
        if (lookup == null) return;

        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        if (lookup.provider().poseRightArm(lookup.stack(), model, entity, entity.getMainArm())) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    private void dinnerware$poseLeftArm(LivingEntity entity, CallbackInfo callbackInfo) {
        ProviderLookup lookup = dinnerwareMod$getProviderForArm(entity, HumanoidArm.LEFT);
        if (lookup == null) return;

        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        if (lookup.provider().poseLeftArm(lookup.stack(), model, entity, entity.getMainArm())) {
            callbackInfo.cancel();
        }
    }

    @Unique
    private static @Nullable ProviderLookup dinnerwareMod$getProviderForArm(LivingEntity entity, HumanoidArm arm) {
        ItemStack mainHandStack = entity.getMainHandItem();
        DinnerwareThirdPersonAnimationProvider mainHandProvider =
            DinnerwareItemAnimations.getThirdPerson(mainHandStack.getItem());

        if (mainHandProvider != null && mainHandProvider.isTwoHanded()) {
            return new ProviderLookup(mainHandStack, mainHandProvider);
        }

        ItemStack offHandStack = entity.getOffhandItem();
        DinnerwareThirdPersonAnimationProvider offHandProvider =
            DinnerwareItemAnimations.getThirdPerson(offHandStack.getItem());

        if (offHandProvider != null && offHandProvider.isTwoHanded()) {
            return new ProviderLookup(offHandStack, offHandProvider);
        }

        ItemStack armStack = arm == entity.getMainArm()
            ? mainHandStack
            : offHandStack;

        DinnerwareThirdPersonAnimationProvider armProvider =
            DinnerwareItemAnimations.getThirdPerson(armStack.getItem());

        if (armProvider == null) return null;

        return new ProviderLookup(armStack, armProvider);
    }

    @Unique
    private record ProviderLookup(
        ItemStack stack,
        DinnerwareThirdPersonAnimationProvider provider
    ) { }
}