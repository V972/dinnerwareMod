package net.v972.dinnerware.fabric.datagen;

import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class NamedDataProvider implements DataProvider {

    private final String name;
    private final DataProvider delegate;

    public NamedDataProvider(String name, DataProvider delegate) {
        this.name = name;
        this.delegate = delegate;
    }

    @Override
    public void run(@NotNull HashCache output) throws IOException {
        delegate.run(output);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}