package net.v972.dinnerware.util;

import java.util.function.Supplier;

// Small wrapper class so we can safely create BEWLRs and initialize them lazily
public class DinnerwareRegistryObject<T> implements Supplier<T> {
    private final Supplier<T> factory;
    private T instance;

    /**
     * Constructs a new registry object.
     * @param factory A supplier that creates the object.
     */
    public DinnerwareRegistryObject(Supplier<T> factory) {
        this.factory = factory;
    }

    /**
     * Lazily creates and returns the instance of the registered object.
     * @return The registered object instance.
     */
    @Override
    public T get() {
        if (this.instance == null)
            this.instance = this.factory.get();
        return this.instance;
    }
}