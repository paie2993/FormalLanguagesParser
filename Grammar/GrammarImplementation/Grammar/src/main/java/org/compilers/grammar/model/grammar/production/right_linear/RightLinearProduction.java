package org.compilers.grammar.model.grammar.production.right_linear;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;
import org.compilers.grammar.model.grammar.production.right_linear.builder.RightLinearProductionBuilder;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;

public interface RightLinearProduction extends LinearProduction {
    static void validateLeftSide(final List<? extends Symbol> side) {
        LinearProduction.validateLeftSide(side);
    }

    static void validateRightSide(final List<? extends Symbol> side) {
        LinearProduction.validateRightSide(side);
        final int length = side.size();
        if (length == 0) {
            return;
        }
        final long noTerminals = side.stream().filter(Terminal::isInstance).count();
        if (noTerminals == length) {
            return;
        }
        if (noTerminals != length - 1 || !NonTerminal.isInstance(side.get(length - 1))) {
            throw new IllegalArgumentException("Right side of right - linear production must contain only one non-terminal symbol at the end");
        }
    }

    static boolean isRightLinearProduction(final Production production) {
        return production instanceof RightLinearProduction;
    }

    static ProductionBuilder<? extends RightLinearProduction> builder() {
        return new RightLinearProductionBuilder();
    }
}
