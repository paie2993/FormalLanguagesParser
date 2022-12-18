package org.compilers.grammar.model.vocabulary;

import java.util.Objects;

public abstract class Symbol {

    private final String value;

    public Symbol(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

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
        final var other = (Symbol) o;
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
