package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class GrammarImpl implements Grammar {
    private final Set<? extends NonTerminal> nonTerminals;
    private final Set<? extends Terminal> terminals;
    private final List<? extends Production> productions;
    private final NonTerminal startingNonTerminal;

    public GrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends Production> productions,
            final NonTerminal startingNonTerminal
    ) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);
        Objects.requireNonNull(productions);
        Objects.requireNonNull(startingNonTerminal);

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = List.copyOf(productions);
        this.startingNonTerminal = startingNonTerminal;
    }

    @Override
    public Set<? extends NonTerminal> nonTerminals() {
        return nonTerminals;
    }

    @Override
    public Set<? extends Terminal> terminals() {
        return terminals;
    }

    @Override
    public Set<? extends Production> productions() {
        return Set.copyOf(productions);
    }

    @Override
    public NonTerminal startingNonTerminal() {
        return startingNonTerminal;
    }

    @Override
    public Set<Production> productionsOf(final Symbol nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return productions
                .stream()
                .filter(production -> Grammar.sideContainsOnlyNonTerminal(production.leftSide(), nonTerminal))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public int indexOf(final Production production) {
        Objects.requireNonNull(production);

        return this.productions.indexOf(production);
    }

    @Override
    public Set<Production> hasSymbolInRightSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> Grammar.rightSideContainsSymbol(production, symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Production> hasSymbolInLeftSide(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return this.productions
                .stream()
                .filter(production -> Grammar.leftSideContainsSymbol(production, symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean containsNonTerminal(final Symbol nonTerminal) {
        Objects.requireNonNull(nonTerminal);

        return Grammar.isNonTerminal(nonTerminal) && this.nonTerminals.contains((NonTerminal) nonTerminal);
    }

    @Override
    public boolean containsTerminal(final Symbol terminal) {
        Objects.requireNonNull(terminal);

        return Grammar.isTerminal(terminal) && this.terminals.contains((Terminal) terminal);
    }

    @Override
    public boolean isContextFree() {
        return this.productions
                .stream()
                .allMatch(Grammar::isProductionContextFree);
    }
}
