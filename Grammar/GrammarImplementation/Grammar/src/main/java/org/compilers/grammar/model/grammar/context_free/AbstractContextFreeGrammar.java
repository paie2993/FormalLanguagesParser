package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.grammar.AbstractGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractContextFreeGrammar<T extends ContextFreeProduction> extends AbstractGrammar<T> implements ContextFreeGrammar<T> {
    protected final Map<? extends Symbol, ? extends Set<String>> first;
    protected final Map<? extends NonTerminal, ? extends Set<String>> follow;

    public AbstractContextFreeGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends T> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
        this.first = ContextFreeGrammar.first(terminals, nonTerminals, productions);
        this.follow = ContextFreeGrammar.follow(this.first, nonTerminals, startSymbol, productions);
    }

    @Override
    public Set<String> first(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.first.get(symbol);
    }

    @Override
    public Set<String> follow(final NonTerminal nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return this.follow.get(nonTerminal);
    }

    @Override
    public Set<? extends T> productionsOf(final NonTerminal nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return this.productions
                .stream()
                .filter(production -> nonTerminal.equals(production.leftSideNonTerminal()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
