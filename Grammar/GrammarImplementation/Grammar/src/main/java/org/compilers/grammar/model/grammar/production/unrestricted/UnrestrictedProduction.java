package org.compilers.grammar.model.grammar.production.unrestricted;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.builder.UnrestrictedProductionBuilder;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public interface UnrestrictedProduction extends Production {
    static void validateLeftSide(final List<? extends Symbol> side) {
        validateSide(side);
        if (side.isEmpty()) {
            throw new IllegalArgumentException("Left side of unrestricted production must not be empty");
        }
    }

    static void validateRightSide(final List<? extends Symbol> side) {
        validateSide(side);
    }

    static boolean isUnrestrictedProduction(final Production production) {
        return production instanceof UnrestrictedProduction;
    }

    static ProductionBuilder<? extends UnrestrictedProduction> builder() {
        return new UnrestrictedProductionBuilder();
    }

    private static void validateSide(final List<? extends Symbol> side) {
        if (Objects.isNull(side)) {
            throw new IllegalArgumentException("Right side of unrestricted production must not be null");
        }
    }
}
