package org.compilers.grammar.model.grammar.right_linear;

import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.linear.LinearGrammar;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.grammar.right_linear.builder.RightLinearGrammarBuilder;

public interface RightLinearGrammar<T extends RightLinearProduction> extends LinearGrammar<T> {
    static GrammarBuilder<? extends RightLinearProduction, ? extends RightLinearGrammar<? extends RightLinearProduction>> builder() {
        return new RightLinearGrammarBuilder();
    }
}
