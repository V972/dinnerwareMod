package net.v972.dinnerware.advancement;

import java.util.Objects;
import java.util.function.Supplier;

public final class ModCriterionTriggers {
    private static Supplier<ManualCriterionTrigger> manualTriggerSupplier;

    private ModCriterionTriggers() {
    }

    public static void setManualTriggerSupplier(Supplier<ManualCriterionTrigger> supplier) {
        manualTriggerSupplier = Objects.requireNonNull(supplier);
    }

    public static ManualCriterionTrigger manualTrigger() {
        if (manualTriggerSupplier == null) {
            throw new IllegalStateException(
                "Dinnerware criterion triggers have not been initialized"
            );
        }

        return manualTriggerSupplier.get();
    }
}