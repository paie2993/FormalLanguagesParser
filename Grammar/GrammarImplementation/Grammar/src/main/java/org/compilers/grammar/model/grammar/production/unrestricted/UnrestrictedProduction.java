package org.compilers.grammar.model.grammar.production.unrestricted;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public interface UnrestrictedProduction extends Production {
    static void validateLeftSide(final List<Symbol> side) {
        validateSide(side);
        if (side.isEmpty()) {
            throw new IllegalArgumentException("Left side of unrestricted production must not be empty");
        }
    }

    static void validateRightSide(final List<Symbol> side) {
        validateSide(side);
    }

    private static void validateSide(final List<Symbol> side) {
        if (Objects.isNull(side)) {
            throw new IllegalArgumentException("Right side of unrestricted production must not be null");
        }
    }
}
