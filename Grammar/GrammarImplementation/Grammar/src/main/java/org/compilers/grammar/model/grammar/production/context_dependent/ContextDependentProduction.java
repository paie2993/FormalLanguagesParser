package org.compilers.grammar.model.grammar.production.context_dependent;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.builder.ContextDependentProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public interface ContextDependentProduction extends UnrestrictedProduction {
    static void validateLeftSide(final List<Symbol> side) {
        UnrestrictedProduction.validateLeftSide(side);
        final boolean hasNoNonTerminal = side.stream().noneMatch(NonTerminal::isNonTerminal);
        if (hasNoNonTerminal) {
            throw new IllegalArgumentException("Left side of context dependent production must contain at least one non-terminal symbol");
        }
    }

    static void validateRightSide(final List<Symbol> side) {
        UnrestrictedProduction.validateRightSide(side);
    }

    static boolean isContextDependentProduction(final Production production) {
        return production instanceof ContextDependentProduction;
    }

    static ProductionBuilder<? extends ContextDependentProduction> builder() {
        return new ContextDependentProductionBuilder();
    }
}
