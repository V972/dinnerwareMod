package net.v972.dinnerware.forge.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;

public final class ForgeModCriterionTriggers {
    private static ManualCriterionTrigger manualTrigger;

    private ForgeModCriterionTriggers() {
    }

    public static void register() {
        manualTrigger = CriteriaTriggers.register(
            new ManualCriterionTrigger()
        );
    }

    public static ManualCriterionTrigger manualTrigger() {
        if (manualTrigger == null) {
            throw new IllegalStateException(
                "Forge criterion triggers have not been registered"
            );
        }

        return manualTrigger;
    }
}