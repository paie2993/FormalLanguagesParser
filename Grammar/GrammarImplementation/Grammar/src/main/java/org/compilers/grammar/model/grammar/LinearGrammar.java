package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.grammar.production.linear.LinearProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface LinearGrammar extends ContextFreeGrammar {
    @Override
    Set<? extends LinearProduction> productions();

    @Override
    Set<? extends LinearProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends LinearProduction> haveSymbolInLeftSide(final Symbol symbol);
}
