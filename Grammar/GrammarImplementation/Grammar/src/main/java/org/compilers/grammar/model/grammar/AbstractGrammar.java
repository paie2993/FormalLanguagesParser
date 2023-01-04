package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;
import org.compilers.grammar.model.grammar.vocabulary.terminal.Terminal;


import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractGrammar implements Grammar {
    private final Set<? extends NonTerminal> nonTerminals;
    private final Set<? extends Terminal> terminals;
    private final List<? extends Production> productions;
    private final NonTerminal startSymbol;

    public AbstractGrammar(final Set<? extends NonTerminal> nonTerminals, final Set<? extends Terminal> terminals, final Set<? extends Production> productions, final NonTerminal startSymbol) {
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
    public Set<? extends Production> productions() {
        return Set.copyOf(this.productions);
    }

    @Override
    public NonTerminal startSymbol() {
        return this.startSymbol;
    }

    @Override
    public boolean containsNonTerminal(final Symbol nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return NonTerminal.isNonTerminal(nonTerminal) && this.nonTerminals.contains((NonTerminal) nonTerminal);
    }

    @Override
    public boolean containsTerminal(final Symbol terminal) {
        Objects.requireNonNull(terminal);

        return Terminal.isTerminal(terminal) && this.terminals.contains((Terminal) terminal);
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
    public Set<Production> haveSymbolInLeftSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> production.hasSymbolInLeftSide(symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Production> haveSymbolInRightSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> production.hasSymbolInRightSide(symbol))
                .collect(Collectors.toUnmodifiableSet());
    }
}
