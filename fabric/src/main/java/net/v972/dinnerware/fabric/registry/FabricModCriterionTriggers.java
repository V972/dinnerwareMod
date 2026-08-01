package net.v972.dinnerware.fabric.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;

public final class FabricModCriterionTriggers {
    private static ManualCriterionTrigger manualTrigger;
    private static boolean registered;

    private FabricModCriterionTriggers() {
    }

    public static void register() {
        if (registered) return;

        manualTrigger = CriteriaTriggers.register(
            new ManualCriterionTrigger()
        );

        registered = true;
    }

    public static ManualCriterionTrigger manualTrigger() {
        if (!registered || manualTrigger == null) {
            throw new IllegalStateException(
                "Fabric criterion triggers have not been registered"
            );
        }

        return manualTrigger;
    }
}