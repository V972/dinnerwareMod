package net.v972.dinnerware.forge.registry;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.v972.dinnerware.DinnerwareCommon;

import net.v972.dinnerware.advancement.ManualCriterionTrigger;

public final class ForgeModCriterionTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER =
            DeferredRegister.create(Registries.TRIGGER_TYPE, DinnerwareCommon.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, ManualCriterionTrigger> MANUAL_TRIGGER =
            TRIGGER.register("manual_trigger", ManualCriterionTrigger::new);

    private ForgeModCriterionTriggers() {
    }

    public static ManualCriterionTrigger manualTrigger() {
        return MANUAL_TRIGGER.get();
    }
}