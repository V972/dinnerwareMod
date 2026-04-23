package net.v972.dinnerware.advancement;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.v972.dinnerware.DinnerwareMod;
import org.jetbrains.annotations.NotNull;

public class ManualCriterionTrigger extends SimpleCriterionTrigger<ManualCriterionTrigger.TriggerInstance> {
    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "manual_trigger");

    @Override
    public ResourceLocation getId() {  return ID; }

    public void trigger(ServerPlayer pPlayer, ResourceLocation id) {
        this.trigger(pPlayer, triggerInstance -> triggerInstance.matches(id));
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, @NotNull DeserializationContext pContext) {
        return new TriggerInstance(pPredicate, ResourceLocation.parse(pJson.get("triggerId").getAsString()));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation triggerId;

        public TriggerInstance(ContextAwarePredicate pPredicate, ResourceLocation id) {
            super(ID, pPredicate);
            triggerId = id;
        }

        public static ManualCriterionTrigger.TriggerInstance byId(ResourceLocation id) {
            return new ManualCriterionTrigger.TriggerInstance(ContextAwarePredicate.ANY, id);
        }

        public boolean matches(ResourceLocation pTriggerId) {
            return triggerId.compareTo(pTriggerId) == 0;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject obj = super.serializeToJson(pConditions);
            obj.add("triggerId", new JsonPrimitive(triggerId.toString()));
            return obj;
        }
    }
}
