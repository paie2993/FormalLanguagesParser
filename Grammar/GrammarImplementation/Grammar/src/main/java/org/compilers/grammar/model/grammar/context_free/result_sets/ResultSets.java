package org.compilers.grammar.model.grammar.context_free.result_sets;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ResultSets {

    public static <T extends Symbol> Map<? extends T, ? extends Set<String>> copyResultSets(
            final Map<? extends T, ? extends Set<String>> resultSets
    ) {
        return resultSets.entrySet().stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> new HashSet<>(entry.getValue())
                        )
                );
    } // tested, good
}
