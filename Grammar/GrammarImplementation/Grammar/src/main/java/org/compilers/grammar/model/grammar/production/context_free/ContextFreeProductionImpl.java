package org.compilers.grammar.model.grammar.production.context_free;

import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public class ContextFreeProductionImpl extends AbstractContextFreeProduction implements ContextFreeProduction {

    public ContextFreeProductionImpl(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextFreeProduction.validateLeftSide(leftSide);
        ContextFreeProduction.validateRightSide(rightSide);
    }

    public ContextFreeProductionImpl(final NonTerminal leftSide, List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextFreeProduction.validateLeftSide(List.of(leftSide));
        ContextFreeProduction.validateRightSide(rightSide);
    }
}
