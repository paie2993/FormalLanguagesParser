package org.compilers.grammar.model.parse_table;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.parse_table.action.Action;
import org.compilers.grammar.model.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.util.*;

public final class ParseTableImpl implements ParseTable {

    public final Map<? extends Map.Entry<NonTerminal, Terminal>, ? extends NextMove> nonTerminalTable;
    public final Map<? extends Map.Entry<Terminal, Terminal>, ? extends Action> terminalTable;

    public ParseTableImpl(Map<Map.Entry<NonTerminal, Terminal>, NextMove> nonTerminalTable, Map<Map.Entry<Terminal, Terminal>, Action> terminalTable) {
        this.nonTerminalTable = nonTerminalTable;
        this.terminalTable = terminalTable;
    }

    public static ParseTableImpl of(final ContextFreeGrammar<? extends ContextFreeProduction> grammar) {
        final var terminalTable = initializeTerminalTable(grammar);
        final var nonTerminalsTable = initializeNonTerminalTable(grammar);
        return new ParseTableImpl(nonTerminalsTable, terminalTable);
    }

    @Override
    public NextMove get(NonTerminal nonTerminal, Terminal terminal) {
        return this.nonTerminalTable.get(new AbstractMap.SimpleImmutableEntry<>(nonTerminal, terminal));
    }

    @Override
    public Action get(Terminal terminal1, Terminal terminal2) {
        return this.terminalTable.get(new AbstractMap.SimpleImmutableEntry<>(terminal1, terminal2));
    }

    private static Map<Map.Entry<NonTerminal, Terminal>, NextMove> initializeNonTerminalTable(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar
    ) {
        Map<Map.Entry<NonTerminal, Terminal>, NextMove> nonTerminalTable = new HashMap<>();
        final var nonTerminals = grammar.nonTerminals();

        nonTerminals.forEach(nonTerminal -> {
            final var productionsOfNonTerminal = grammar.productionsOf(nonTerminal);
            productionsOfNonTerminal.forEach(production -> {
                final var indexOfProduction = grammar.indexOf(production);
                final var firstOfRightSide = grammar.first(production.rightSide());
                firstOfRightSide.forEach(firstSymbol -> {
                    if ("".equals(firstSymbol)) {
                        final var followOfLeftSide = grammar.follow(production.leftSideNonTerminal());
                        followOfLeftSide.forEach(followSymbol -> {
                            Terminal terminalSymbol;
                            if ("".equals(followSymbol)) {
                                terminalSymbol = DOLLAR_TERMINAL;
                            } else {
                                terminalSymbol = new TerminalImpl(followSymbol);
                            }
                            if (nonTerminalTable.containsKey(new AbstractMap.SimpleImmutableEntry<NonTerminal, Terminal>(nonTerminal, terminalSymbol))) {
                                throw new RuntimeException("THE GRAMMAR IS NOT LL(1)");
                            }
                            nonTerminalTable.put(new AbstractMap.SimpleImmutableEntry<>(nonTerminal, terminalSymbol), new NextMove(production.rightSide(), indexOfProduction));
                        });
                    } else {
                        Terminal terminalSymbol = new TerminalImpl(firstSymbol);
                        if (nonTerminalTable.containsKey(new AbstractMap.SimpleImmutableEntry<NonTerminal, Terminal>(nonTerminal, terminalSymbol))) {
                            throw new RuntimeException("THE GRAMMAR IS NOT LL(1)");
                        }
                        nonTerminalTable.put(new AbstractMap.SimpleImmutableEntry<>(nonTerminal, terminalSymbol), new NextMove(production.rightSide(), indexOfProduction));
                    }
                });
            });
        });

        return nonTerminalTable;
    }

    private static Map<Map.Entry<Terminal, Terminal>, Action> initializeTerminalTable(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar
    ) {
        final var terminalTable = new HashMap<Map.Entry<Terminal, Terminal>, Action>();
        final var terminals = grammar.terminals();

        terminals.forEach(terminal -> terminalTable.put(new AbstractMap.SimpleImmutableEntry<>(terminal, terminal), Action.POP));
        terminalTable.put(new AbstractMap.SimpleEntry<>(DOLLAR_TERMINAL, DOLLAR_TERMINAL), Action.ACCEPT);
        return terminalTable;
    }
}
