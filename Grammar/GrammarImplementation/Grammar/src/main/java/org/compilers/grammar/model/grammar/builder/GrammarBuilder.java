package org.compilers.grammar.model.grammar.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.production.Production;

public interface GrammarBuilder<T extends Production> {
    String SEPARATOR = ";";

    GrammarBuilder<? extends T> file(final String fileName);

    GrammarBuilder<? extends T> file(final String fileName, final String separator);

    GrammarBuilder<? extends T> grammar(final Grammar<? extends Production> grammar);

    Grammar<? extends T> build();
}
