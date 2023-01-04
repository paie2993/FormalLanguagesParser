package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface ContextFreeGrammar extends ContextDependentGrammar {
    @Override
    Set<? extends ContextFreeProduction> productions();

    @Override
    Set<? extends ContextFreeProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends ContextFreeProduction> haveSymbolInLeftSide(final Symbol symbol);
}
