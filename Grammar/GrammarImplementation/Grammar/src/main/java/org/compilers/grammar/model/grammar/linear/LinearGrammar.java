package org.compilers.grammar.model.grammar.linear;

import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.linear.builder.LinearGrammarBuilder;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;

public interface LinearGrammar<T extends LinearProduction> extends ContextFreeGrammar<T> {
    static GrammarBuilder<? extends LinearProduction, ? extends LinearGrammar<? extends LinearProduction>> builder() {
        return new LinearGrammarBuilder();
    }
}
