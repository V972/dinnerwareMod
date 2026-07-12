package net.v972.dinnerware.client.animation;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public final class DinnerwareItemAnimations {
    private static final Map<Item, DinnerwareThirdPersonAnimationProvider> THIRD_PERSON_PROVIDERS =
            new IdentityHashMap<>();

    private DinnerwareItemAnimations() {
    }

    public static void registerThirdPerson(Item item, DinnerwareThirdPersonAnimationProvider provider) {
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(provider, "provider");

        DinnerwareThirdPersonAnimationProvider previous = THIRD_PERSON_PROVIDERS.putIfAbsent(item, provider);
        if (previous != null) {
            throw new IllegalStateException("Third-person animation provider already registered for item: " + item);
        }
    }

    public static @Nullable DinnerwareThirdPersonAnimationProvider getThirdPerson(Item item) {
        return THIRD_PERSON_PROVIDERS.get(item);
    }
}