package org.compilers.grammar.model.grammar.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.production.Production;

public interface GrammarBuilder<T1 extends Production, T2 extends Grammar<? extends T1>> {
    String SEPARATOR = ";";

    GrammarBuilder<? extends T1, ? extends T2> file(final String fileName);

    GrammarBuilder<? extends T1, ? extends T2> file(final String fileName, final String separator);

    GrammarBuilder<? extends T1, ? extends T2> grammar(final Grammar<? extends Production> grammar);

    T2 build();
}
