package org.compilers.grammar.model.grammar.unrestricted;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.builder.UnrestrictedGrammarBuilder;

public interface UnrestrictedGrammar<T extends UnrestrictedProduction> extends Grammar<T> {
    static GrammarBuilder<? extends UnrestrictedProduction, ? extends UnrestrictedGrammar<? extends UnrestrictedProduction>> builder() {
        return new UnrestrictedGrammarBuilder();
    }
}
