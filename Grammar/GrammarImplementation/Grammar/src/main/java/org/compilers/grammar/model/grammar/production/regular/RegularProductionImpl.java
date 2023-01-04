package org.compilers.grammar.model.grammar.production.regular;

import org.compilers.grammar.model.grammar.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public class RegularProductionImpl extends AbstractContextFreeProduction implements RegularProduction {
    public RegularProductionImpl(final List<Symbol> leftSide, final List<Symbol> rightSide) {
        super(leftSide, rightSide);
        RegularProduction.validateLeftSide(leftSide);
        RegularProduction.validateRightSide(rightSide);
    }

    public RegularProductionImpl(NonTerminal leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        RegularProduction.validateLeftSide(List.of(leftSide));
        RegularProduction.validateRightSide(rightSide);
    }
}
