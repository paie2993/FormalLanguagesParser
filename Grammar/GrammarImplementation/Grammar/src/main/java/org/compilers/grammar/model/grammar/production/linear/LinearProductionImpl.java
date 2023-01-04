package org.compilers.grammar.model.grammar.production.linear;

import org.compilers.grammar.model.grammar.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public class LinearProductionImpl extends AbstractContextFreeProduction implements LinearProduction {
    public LinearProductionImpl(final List<Symbol> leftSide, final List<Symbol> rightSide) {
        super(leftSide, rightSide);
        LinearProduction.validateLeftSide(leftSide);
        LinearProduction.validateRightSide(rightSide);
    }

    public LinearProductionImpl(NonTerminal leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        LinearProduction.validateLeftSide(List.of(leftSide));
        LinearProduction.validateRightSide(rightSide);
    }
}
