package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractGrammar<T extends Production> implements Grammar<T> {

    protected final Set<? extends NonTerminal> nonTerminals;

    protected final Set<? extends Terminal> terminals;

    protected final List<T> productions;

    protected final NonTerminal startNonTerminal;


    public AbstractGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<T> productions,
            final NonTerminal startNonTerminal
    ) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);
        Objects.requireNonNull(productions);
        Objects.requireNonNull(startNonTerminal);

        uniqueProductions(productions);

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = productions;
        this.startNonTerminal = startNonTerminal;
    }

    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Set<? extends NonTerminal> nonTerminals() {
        return nonTerminals;
    }

    @Override
    public Set<? extends Terminal> terminals() {
        return terminals;
    }

    @Override
    public List<T> productions() {
        return productions;
    }

    @Override
    public NonTerminal startNonTerminal() {
        return startNonTerminal;
    }

    // complex getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean containsNonTerminal(final NonTerminal nonTerminal) {
        Objects.requireNonNull(nonTerminal);
        return nonTerminals.contains(nonTerminal);
    }

    @Override
    public int indexOf(final T production) {
        Objects.requireNonNull(production);
        return productions.indexOf(production);
    }

    @Override
    public T at(final int index) {
        return productions.get(index);
    }

    // validator
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // check for uniqueness of productions
    // public to allow testing
    public static <T extends Production> void uniqueProductions(final Collection<? extends T> productions) {
        final var set = Set.copyOf(productions);
        if (set.size() != productions.size()) {
            throw new IllegalArgumentException("Productions must be unique in a grammar");
        }
    }
}
