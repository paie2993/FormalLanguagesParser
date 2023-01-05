package org.compilers.grammar.model.grammar.context_dependent;

import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.context_dependent.builder.ContextDependentGrammarBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;

public interface ContextDependentGrammar<T extends ContextDependentProduction> extends UnrestrictedGrammar<T> {
    static GrammarBuilder<? extends ContextDependentProduction, ? extends ContextDependentGrammar<? extends ContextDependentProduction>> builder() {
        return new ContextDependentGrammarBuilder();
    }
}
