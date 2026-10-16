package net.v972.dinnerware.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ManualCriterionTrigger extends SimpleCriterionTrigger<ManualCriterionTrigger.TriggerInstance> {

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer pPlayer, ResourceLocation id) {
        this.trigger(pPlayer, triggerInstance -> triggerInstance.matches(id));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation triggerId)
        implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC =
            RecordCodecBuilder.create(instance ->
                instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC
                        .optionalFieldOf("player")
                        .forGetter(TriggerInstance::player),

                    ResourceLocation.CODEC
                        .fieldOf("triggerId")
                        .forGetter(TriggerInstance::triggerId)
                ).apply(instance, TriggerInstance::new)
            );

        public static Criterion<TriggerInstance> byId(ResourceLocation id) {
            return byId(ModCriterionTriggers.manualTrigger(), id);
        }

        public static Criterion<TriggerInstance> byId(ManualCriterionTrigger trigger, ResourceLocation id) {
            return trigger.createCriterion(new TriggerInstance(Optional.empty(), id));
        }

        public boolean matches(ResourceLocation pTriggerId) {
            return triggerId.equals(pTriggerId);
        }
    }
}