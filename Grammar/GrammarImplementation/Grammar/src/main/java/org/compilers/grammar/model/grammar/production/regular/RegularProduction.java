package org.compilers.grammar.model.grammar.production.regular;

import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.grammar.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;
import org.compilers.grammar.model.grammar.vocabulary.terminal.Terminal;

import java.util.List;

public interface RegularProduction extends RightLinearProduction {
    static void validateLeftSide(final List<Symbol> side) {
        RightLinearProduction.validateLeftSide(side);
    }

    static void validateRightSide(final List<Symbol> side) {
        RightLinearProduction.validateRightSide(side);
        final int length = side.size();
        if (length >= 3) {
            throw new IllegalArgumentException("Right side of regular production must not contain more than 2 symbols");
        }
        if (length == 0 || length == 1 && Terminal.isTerminal(side.get(0))) {
            return;
        }
        if (!Terminal.isTerminal(side.get(0)) || !NonTerminal.isNonTerminal(side.get(1))) {
            throw new IllegalArgumentException("Right side of regular production is not valid");
        }
    }
}
