package net.v972.dinnerware.block.entity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.v972.dinnerware.mixins_interfaces.IDinnerwareItemDynamicRendering;
import net.v972.dinnerware.util.DinnerwareRegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DinnerwareBEWLRManager {
    @Deprecated(forRemoval = false)
    public static final Map<Item, DinnerwareRegistryObject<BlockEntityWithoutLevelRenderer>> RENDERERS = new HashMap<>();

    /** Registers a single {@link ItemLike} instance to a single {@link BlockEntityWithoutLevelRenderer} instance, and provides {@link Minecraft}. */
    public static void register(ItemLike item, Function<Minecraft, BlockEntityWithoutLevelRenderer> renderer) {
        Objects.requireNonNull(item.asItem(), "item is null");
        Objects.requireNonNull(renderer, "renderer is null");

        if (DinnerwareBEWLRManager.RENDERERS.putIfAbsent(item.asItem(), new DinnerwareRegistryObject<>(() -> renderer.apply(Minecraft.getInstance()))) != null)
            throw new IllegalArgumentException("Item " + BuiltInRegistries.ITEM.getKey(item.asItem()) + " is already registered as a BEWLR!");
    }

    /** Registers a single {@link ItemLike} instance to a single {@link BlockEntityWithoutLevelRenderer} instance, and provides {@link BlockEntityRenderDispatcher} and {@link EntityModelSet}. */
    public static void register(ItemLike item, BiFunction<BlockEntityRenderDispatcher, EntityModelSet, BlockEntityWithoutLevelRenderer> renderer) {
        Objects.requireNonNull(renderer, "renderer is null");
        DinnerwareBEWLRManager.register(item, minecraft -> renderer.apply(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels()));
    }

    /** Registers a {@link Set} of {@link ItemLike} instances to a single {@link BlockEntityWithoutLevelRenderer} instance, and provides {@link Minecraft}. */
    public static void register(Set<ItemLike> items, Function<Minecraft, BlockEntityWithoutLevelRenderer> renderer) {
        Objects.requireNonNull(items, "items is null");
        Objects.requireNonNull(renderer, "renderer is null");
        if (items.isEmpty()) throw new IllegalArgumentException("items is empty");

        for (ItemLike item : items) {
            Objects.requireNonNull(item, "items contains null element");
            Objects.requireNonNull(item.asItem(), "item.asItem() is null for element in set");
        }

        // Creates a single DinnerwareRegistryObject instance that will be shared by all ItemLike instances in the given Set
        DinnerwareRegistryObject<BlockEntityWithoutLevelRenderer> registryObject = new DinnerwareRegistryObject<>(() -> renderer.apply(Minecraft.getInstance()));
        for (ItemLike item : items)
            if (DinnerwareBEWLRManager.RENDERERS.putIfAbsent(item.asItem(), registryObject) != null)
                throw new IllegalArgumentException("Item " + BuiltInRegistries.ITEM.getKey(item.asItem()) + " is already registered as a BEWLR!");
    }

    /** Registers a {@link Set} of {@link ItemLike} instances to a single {@link BlockEntityWithoutLevelRenderer} instance, and provides {@link BlockEntityRenderDispatcher} and {@link EntityModelSet}. */
    public static void register(Set<ItemLike> items, BiFunction<BlockEntityRenderDispatcher, EntityModelSet, BlockEntityWithoutLevelRenderer> renderer) {
        Objects.requireNonNull(renderer, "renderer is null");
        DinnerwareBEWLRManager.register(items, minecraft -> renderer.apply(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels()));
    }

    /**
     * Used to retrieve the {@link BlockEntityWithoutLevelRenderer} that corresponds to the given {@link ItemLike}.
     * <p>
     * <strong>NOTE:</strong> Avoid calling this method when possible, instead cast {@code item} to
     * {@link IDinnerwareItemDynamicRendering} and use {@link IDinnerwareItemDynamicRendering#dinnerware$getBEWLR()}.</p>
     */
    @Deprecated(forRemoval = false)
    public static DinnerwareRegistryObject<BlockEntityWithoutLevelRenderer> get(ItemLike item) {
        Objects.requireNonNull(item.asItem(), "item is null");
        return DinnerwareBEWLRManager.RENDERERS.get(item.asItem());
    }
}