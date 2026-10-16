package net.v972.dinnerware;

import net.minecraft.resources.ResourceLocation;public final class DinnerwareConstants {
    public static final String MOD_ID = "dinnerware";

    private DinnerwareConstants() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
