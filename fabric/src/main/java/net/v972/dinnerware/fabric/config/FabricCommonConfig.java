package net.v972.dinnerware.fabric.config;

import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.v972.dinnerware.config.DinnerwareEatingMode;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Config(name = "dinnerware-common")
public final class FabricCommonConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    private static final Logger LOGGER = LogUtils.getLogger();

    @Comment("""
        Maximum amount of items one slot of a plate can hold.
        Does not override the item's maximum stack size.
        Default: 16
        """)
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int maxPlateStackSize = 16;

    @Comment("""
        If set to true, allow player to keep eating off the plate, even if they're not hungry.
        Food buffs/debuffs are still applied.
        Default: false
        """)
    public boolean allowOvereating = false;

    @Comment("""
        If true, only edible items and items in the dinnerware:additional_food tag may be placed on plates.
        Otherwise, anything will fit. Your rendering mileage may vary though.
        Default: true
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean onlyFoodOnPlate = true;

    @ConfigEntry.Gui.Excluded
    private static final List<String> DEFAULT_FOOD_BLACKLIST = List.of(
        "artifacts:everlasting_beef",
        "artifacts:eternal_steak"
    );

    @Comment("""
        A list of items to forbid from being eaten, even if they're part of "dinnerware:additional_food" tag.
        Items listed here may still be placed on a plate.
        Default: ["artifacts:everlasting_beef", "artifacts:eternal_steak"]
        """)
    @ConfigEntry.Gui.Tooltip
    public List<String> foodBlacklist;

    @Comment("""
        If set to true, plates break under various circumstances.
        Like walking on them.
        Entities inside "fragile_plate_ignored" tag are ignored.
        Default: false
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean fragilePlates = false;

    @Comment("""
        If true, the bottom-right slot becomes the main plate slot.
        This effectively mirrors the plate.
        Be careful when changing this with existing plates in a world.
        Default: false
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean rightToLeft = false;

    @Comment("""
        The mode in which the food will be eaten off of a plate.
         QUEUE:       all the food is eaten in the Main dish slot first, then in Side dish slot, then in Extra dish slot;
         ROUND_ROBIN: each new right-click will take the food from the new slot. Clicks with no food eaten still count;
         AIMING:      the food is eaten depending on where and crosshair is aimed at the time of right-click;
        Default: AIMING
        """)
    @ConfigEntry.Gui.Tooltip
    public FabricEatingMode eatingMode = FabricEatingMode .AIMING;

    @Comment("""
        If set to true, the tray will behave like a bundle: 
        If there's a matching plates stack, the picked up one will be merged into it.
        Otherwise, the merging only happens for the topmost plate.
        Default: false
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean trayMergeMatchingItem = false;

    @Comment("""
        If set to true, dispensers will place plate blocks.
        Disable this if another mod provides similar behavior.
        Default: true
        """)
    @ConfigEntry.Gui.Tooltip
    public boolean dispensersPlacePlates = true;

    @Override
    public void validatePostLoad() throws ValidationException {
        maxPlateStackSize = Math.max(1, Math.min(64, maxPlateStackSize));

        if (eatingMode == null) {
            LOGGER.error(
                "Invalid null eatingMode in Dinnerware config; using AIMING"
            );
            eatingMode = FabricEatingMode.AIMING;
        }

        if (foodBlacklist == null) {
            foodBlacklist = new ArrayList<>(DEFAULT_FOOD_BLACKLIST);
            return;
        }

        sanitizeFoodBlacklist();
    }

    public boolean sanitizeFoodBlacklist() {
        if (foodBlacklist == null) {
            LOGGER.error(
                "Dinnerware food blacklist was null; replacing it with an empty list"
            );
            foodBlacklist = new ArrayList<>();
            return true;
        }

        LinkedHashSet<String> sanitized = new LinkedHashSet<>();
        boolean changed = false;

        for (String itemName : foodBlacklist) {
            if (!isValidItemName(itemName)) {
                LOGGER.error(
                    "Removing invalid item '{}' from Dinnerware food blacklist",
                    itemName
                );
                changed = true;
                continue;
            }

            if (!sanitized.add(itemName)) changed = true;
        }

        if (changed) foodBlacklist = new ArrayList<>(sanitized);

        return changed;
    }

    private static boolean isValidItemName(String itemName) {
        if (itemName == null || itemName.isBlank()) return false;

        ResourceLocation itemId = ResourceLocation.tryParse(itemName);
        if (itemId == null) return false;

        boolean namespaceLoaded = FabricLoader
            .getInstance()
            .isModLoaded(itemId.getNamespace());

        return !namespaceLoaded || BuiltInRegistries.ITEM.containsKey(itemId);
    }
}