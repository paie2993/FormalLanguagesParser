package org.compilers.grammar.model.grammar.production.context_free;

import org.compilers.grammar.model.grammar.production.AbstractProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public abstract class AbstractContextFreeProduction extends AbstractProduction implements ContextFreeProduction {
    public AbstractContextFreeProduction(List<? extends Symbol> leftSide, List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
    }

    public AbstractContextFreeProduction(NonTerminal leftSide, List<? extends Symbol> rightSide) {
        super(List.of(leftSide), rightSide);
    }

    @Override
    public NonTerminal leftSideNonTerminal() {
        return (NonTerminal) this.leftSide.get(0);
    }
}
