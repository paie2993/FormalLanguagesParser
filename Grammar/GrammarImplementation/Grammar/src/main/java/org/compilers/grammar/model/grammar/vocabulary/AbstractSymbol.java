package org.compilers.grammar.model.grammar.vocabulary;

import java.util.Objects;

public abstract class AbstractSymbol implements Symbol {

    private final String value;

    public AbstractSymbol(final String value) {
        Objects.requireNonNull(value);

        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
            return false;
        final AbstractSymbol other = (AbstractSymbol) o;
        return Objects.equals(other.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
