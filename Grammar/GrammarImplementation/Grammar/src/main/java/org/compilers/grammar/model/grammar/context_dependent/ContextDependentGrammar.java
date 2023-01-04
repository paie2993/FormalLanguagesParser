package org.compilers.grammar.model.grammar.context_dependent;

import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface ContextDependentGrammar extends UnrestrictedGrammar {
    @Override
    Set<? extends ContextDependentProduction> productions();

    @Override
    Set<? extends ContextDependentProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends ContextDependentProduction> haveSymbolInLeftSide(final Symbol symbol);
}
