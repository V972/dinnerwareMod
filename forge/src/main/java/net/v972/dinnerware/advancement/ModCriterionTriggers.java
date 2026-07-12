package net.v972.dinnerware.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public interface ModCriterionTriggers {
    public static final ManualCriterionTrigger MANUAL_TRIGGER = register(ManualCriterionTrigger::new);

    static <T extends CriterionTrigger<?>> T register(Supplier<T> creator) {
        return CriteriaTriggers.register(creator.get());
    }

    public static void init() {}
}
