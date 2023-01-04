package org.compilers.grammar.model.grammar.unrestricted;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface UnrestrictedGrammar extends Grammar {
    @Override
    Set<? extends UnrestrictedProduction> productions();

    @Override
    Set<? extends UnrestrictedProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends UnrestrictedProduction> haveSymbolInLeftSide(final Symbol symbol);
}
