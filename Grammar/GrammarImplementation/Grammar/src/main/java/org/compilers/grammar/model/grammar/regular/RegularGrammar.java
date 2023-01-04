package org.compilers.grammar.model.grammar.regular;

import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammar;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface RegularGrammar extends RightLinearGrammar {
    @Override
    Set<? extends RegularProduction> productions();

    @Override
    Set<? extends RegularProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends RegularProduction> haveSymbolInLeftSide(final Symbol symbol);
}
