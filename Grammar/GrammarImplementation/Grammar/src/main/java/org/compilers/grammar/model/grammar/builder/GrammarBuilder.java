package org.compilers.grammar.model.grammar.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;

import java.util.Set;
import java.util.stream.Collectors;

public interface GrammarBuilder<T1 extends Production, T2 extends Grammar<? extends T1>> {
    String SEPARATOR = ";";

    GrammarBuilder<? extends T1, ? extends T2> file(final String fileName);

    GrammarBuilder<? extends T1, ? extends T2> file(final String fileName, final String separator);

    GrammarBuilder<? extends T1, ? extends T2> grammar(final Grammar<? extends Production> grammar);

    T2 build();

    static <T> Set<? extends T> prepareProductions(final Set<? extends Production> productions, final ProductionBuilder<? extends T> builder) {
        return productions
                .stream()
                .map(builder::production)
                .map(ProductionBuilder::build)
                .collect(Collectors.toUnmodifiableSet());
    }
}
