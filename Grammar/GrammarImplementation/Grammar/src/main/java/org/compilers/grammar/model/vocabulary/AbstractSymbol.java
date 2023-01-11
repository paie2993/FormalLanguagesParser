package org.compilers.grammar.model.vocabulary;

import java.util.Objects;

public abstract class AbstractSymbol implements Symbol {

    private final String value;

    // constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public AbstractSymbol(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    // getter
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String value() {
        return this.value;
    }

    // general utility
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final var other = (AbstractSymbol) o;
        return Objects.equals(other.value, this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
