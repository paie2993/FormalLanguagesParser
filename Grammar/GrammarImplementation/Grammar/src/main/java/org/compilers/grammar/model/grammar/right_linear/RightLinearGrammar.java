package org.compilers.grammar.model.grammar.right_linear;

import org.compilers.grammar.model.grammar.linear.LinearGrammar;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Set;

public interface RightLinearGrammar extends LinearGrammar {
    @Override
    Set<? extends RightLinearProduction> productions();

    @Override
    Set<? extends RightLinearProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends RightLinearProduction> haveSymbolInLeftSide(final Symbol symbol);
}
