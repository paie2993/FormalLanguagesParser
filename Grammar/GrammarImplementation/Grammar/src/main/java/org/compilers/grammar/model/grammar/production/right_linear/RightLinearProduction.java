package org.compilers.grammar.model.grammar.production.right_linear;

import org.compilers.grammar.model.grammar.production.linear.LinearProduction;
import org.compilers.grammar.model.grammar.vocabulary.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;
import org.compilers.grammar.model.grammar.vocabulary.Terminal;

import java.util.List;

public interface RightLinearProduction extends LinearProduction {
    static void validateLeftSide(final List<Symbol> side) {
        LinearProduction.validateLeftSide(side);
    }

    static void validateRightSide(final List<Symbol> side) {
        LinearProduction.validateRightSide(side);
        final int length = side.size();
        if (length == 0) {
            return;
        }
        final long noTerminals = side.stream().filter(Terminal::isTerminal).count();
        if (noTerminals == length) {
            return;
        }
        if (noTerminals != length - 1 || !NonTerminal.isNonTerminal(side.get(length - 1))) {
            throw new IllegalArgumentException("Right side of right - linear production must contain only one non-terminal symbol at the end");
        }
    }
}
