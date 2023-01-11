package org.compilers.grammar.model.production.context_free;

import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public final class ContextFreeProduction extends AbstractContextFreeProduction {

    // constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ContextFreeProduction(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        super(leftSide, rightSide);
    }

    public ContextFreeProduction(final NonTerminal leftSide, List<? extends Symbol> rightSide) {
        super(List.of(leftSide), rightSide);
    }

    @Override
    public NonTerminal leftSideNonTerminal() {
        return (NonTerminal) leftSide.get(0);
    }
}
