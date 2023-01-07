package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;


import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractGrammar<T extends Production> implements Grammar<T> {
    protected final Set<? extends NonTerminal> nonTerminals;
    protected final Set<? extends Terminal> terminals;
    protected final List<T> productions;
    protected final NonTerminal startSymbol;

    public AbstractGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends T> productions,
            final NonTerminal startSymbol) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);
        Objects.requireNonNull(productions);
        Objects.requireNonNull(startSymbol);

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = List.copyOf(productions);
        this.startSymbol = startSymbol;
    }

    @Override
    public Set<? extends NonTerminal> nonTerminals() {
        return this.nonTerminals;
    }

    @Override
    public Set<? extends Terminal> terminals() {
        return this.terminals;
    }

    @Override
    public Set<? extends T> productions() {
        return Set.copyOf(this.productions);
    }

    @Override
    public NonTerminal startSymbol() {
        return this.startSymbol;
    }

    @Override
    public boolean containsSymbol(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.hasNonTerminal(symbol) || this.hasTerminal(symbol);
    }

    @Override
    public boolean containsNonTerminal(final Symbol nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return this.hasNonTerminal(nonTerminal);
    }

    @Override
    public boolean containsTerminal(final Symbol terminal) {
        Objects.requireNonNull(terminal);

        return this.hasTerminal(terminal);
    }

    @Override
    public boolean containsProduction(final Production production) {
        Objects.requireNonNull(production);

        return this.productions.contains(production);
    }

    @Override
    public int indexOf(final Production production) {
        Objects.requireNonNull(production);

        return this.productions.indexOf(production);
    }

    @Override
    public T productionAt(int index) {
        return this.productions.get(index);
    }

    @Override
    public Set<? extends T> haveSymbolInRightSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> production.hasSymbolInLeftSide(symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<? extends T> haveSymbolInLeftSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> production.hasSymbolInRightSide(symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean hasNonTerminal(final Symbol symbol) {
        return NonTerminal.isInstance(symbol) && this.nonTerminals.contains((NonTerminal) symbol);
    }

    private boolean hasTerminal(final Symbol symbol) {
        return Terminal.isInstance(symbol) && this.terminals.contains((Terminal) symbol);
    }
}
