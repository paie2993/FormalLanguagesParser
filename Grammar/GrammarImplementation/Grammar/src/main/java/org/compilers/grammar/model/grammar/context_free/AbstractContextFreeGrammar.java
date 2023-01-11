package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.grammar.context_dependent.AbstractContextDependentGrammar;
import org.compilers.grammar.model.grammar.context_free.first.First1;
import org.compilers.grammar.model.grammar.context_free.follow.Follow1;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractContextFreeGrammar<T extends AbstractContextFreeProduction> extends AbstractContextDependentGrammar<T> {

    protected final Map<? extends Symbol, ? extends Set<String>> first;
    protected final Map<? extends NonTerminal, ? extends Set<String>> follow;

    // constructor
    public AbstractContextFreeGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<T> productions,
            final NonTerminal startNonTerminal
    ) {
        super(nonTerminals, terminals, productions, startNonTerminal);

        final var first = First1.first(terminals, nonTerminals, productions);
        this.first = first;
        this.follow = Follow1.follow(first, nonTerminals, startNonTerminal, productions);
    }

    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Map<? extends Symbol, ? extends Set<String>> first() {
        return first;
    }

    public Map<? extends NonTerminal, ? extends Set<String>> follow() {
        return follow;
    }

    // all the productions for which the left side is equal to the given non-terminal
    public Set<? extends T> productionsOf(final NonTerminal nonTerminal) {
        Objects.requireNonNull(nonTerminal);
        return productionsOf(nonTerminal, productions);
    }

    // Note: Made public for testing
    public static <T extends AbstractContextFreeProduction> Set<? extends T> productionsOf(
            final NonTerminal nonTerminal,
            final Collection<? extends T> productions
    ) {
        Objects.requireNonNull(nonTerminal);
        Objects.requireNonNull(productions);
        return productions.stream()
                .filter(production -> nonTerminal.equals(production.leftSideNonTerminal()))
                .collect(Collectors.toUnmodifiableSet());
    } // pending testing
}
