package org.compilers.grammar.parser.parse_table;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.grammar.context_free.first.First1;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.parser.parse_table.action.Action;
import org.compilers.grammar.parser.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.util.*;

public final class ParseTableImpl implements ParseTable {

    public final Map<? extends Indices, ? extends NextMove> nonTerminalTable;
    public final Map<? extends Indices, ? extends Action> terminalTable;

    public ParseTableImpl(
            final Map<? extends Indices, ? extends NextMove> nonTerminalTable,
            final Map<? extends Indices, ? extends Action> terminalTable
    ) {
        this.nonTerminalTable = nonTerminalTable;
        this.terminalTable = terminalTable;
    }

    public static <T extends AbstractContextFreeGrammar<AbstractContextFreeProduction>> ParseTableImpl of(final T grammar) {
        final var nonTerminalTable = initializeNonTerminalTable(grammar);
        final var terminalTable = initializeTerminalTable(grammar);
        return new ParseTableImpl(nonTerminalTable, terminalTable);
    }

    // getters
    @Override
    public NextMove get(final NonTerminal rowNonTerminal, final Terminal columnTerminal) {
        return nonTerminalTable.get(Indices.of(rowNonTerminal, columnTerminal));
    }

    @Override
    public Action get(final Terminal rowTerminal, final Terminal columnTerminal) {
        return terminalTable.get(Indices.of(rowTerminal, columnTerminal));
    }

    // initializers, the actual algorithm of building the parsing table (for implementation purposes, the table was
    // split in a non-terminal table and a terminal table)
    // Note: made public for testing
    public static <T extends AbstractContextFreeGrammar<AbstractContextFreeProduction>>
    Map<? extends Indices, ? extends NextMove> initializeNonTerminalTable(final T grammar) {

        final var first = grammar.first();
        final var follow = grammar.follow();
        final var nonTerminals = grammar.nonTerminals();

        final Map<Indices, NextMove> nonTerminalTable = new HashMap<>();

        // for each of the non-terminals in the grammar, i.e., each row of the non-terminals parse table
        nonTerminals.forEach(nonTerminal -> {

            if (nonTerminal.value().equals("declarationStatement")) {
                final var a = 1;
            }

            // get the productions of the non-terminal
            final var productionsOfNonTerminal = grammar.productionsOf(nonTerminal);

            // for each of the productions of the current non-terminal
            productionsOfNonTerminal.forEach(production -> {

                // obtain the index of the production from the grammar
                final var productionIndex = grammar.indexOf(production);

                if (nonTerminal.value().equals("declarationStatement")) {
                    final var a = 1;
                }

                final var rightSide = production.rightSide();
                // obtain the 'first1' of the right side of the production
                final var firstOfRightSide = First1.first(rightSide, first);

                // for each of the symbols in the 'first' of the right side of the current production ...
                firstOfRightSide.forEach(firstSymbol -> {

                    if (nonTerminal.value().equals("declarationStatement")) {
                        final var a = 1;
                    }

                    // ... if the current 'first symbol' happens to be 'epsilon' ...
                    if ("".equals(firstSymbol)) {

                        // ... get the follow of the current non-terminal (,i.e., the row index)
                        final var followOfNonTerminal = follow.get(nonTerminal);

                        // for each symbol in the follow of the current non-terminal ...
                        followOfNonTerminal.forEach(followSymbol -> {

                            Terminal terminal;

                            // ... if the current follow symbol happens to be 'epsilon', map it to the DOLLAR_TERMINAL
                            if ("".equals(followSymbol)) {
                                terminal = DOLLAR_TERMINAL;
                            } else {
                                terminal = new TerminalImpl(followSymbol);
                            }

                            final var index = Indices.of(nonTerminal, terminal);
                            final var move = NextMove.of(rightSide, productionIndex);
                            updateNonTerminalTable(index, move, nonTerminalTable);
                        });

                        // ... otherwise (,i.e., the current 'first symbol' is not 'epsilon') ...
                    } else {
                        final var terminal = new TerminalImpl(firstSymbol);

                        final var index = Indices.of(nonTerminal, terminal);
                        final var move = NextMove.of(rightSide, productionIndex);
                        updateNonTerminalTable(index, move, nonTerminalTable);
                    }

                });
            });
        });

        return nonTerminalTable;
    } // tested, good

    private static void updateNonTerminalTable(
            final Indices index,
            final NextMove move,
            final Map<Indices, NextMove> nonTerminalTable
    ) {
        try {
            checkUnfilledCell(index, nonTerminalTable); // make sure the cell is not already filled
        } catch (final Exception e) {
            System.err.println("Could not put move in cell: " + move);
            throw e;
        }
        nonTerminalTable.put(index, move);
    } // test not needed

    // Note: made public for testing
    public static <T extends AbstractContextFreeGrammar<? extends AbstractContextFreeProduction>>
    Map<? extends Indices, ? extends Action> initializeTerminalTable(final T grammar) {

        final var terminals = grammar.terminals();
        final Map<Indices, Action> terminalTable = new HashMap<>();

        // fill the terminal table with 'POP' on the primary diagonal and put 'ACCEPT' at the '$-$' indices
        terminals.forEach(terminal -> terminalTable.put(Indices.of(terminal, terminal), Action.POP));
        terminalTable.put(Indices.of(DOLLAR_TERMINAL, DOLLAR_TERMINAL), Action.ACCEPT);

        return terminalTable;
    } // tested, good


    private static void checkUnfilledCell(final Indices index, final Map<? extends Indices, ? extends NextMove> nonTerminalTable) {
        // ... if the cell pointed to by the index "currentNonTerminal - current(Terminal)Symbol" is already filled,
        // then the grammar is not LL(1)
        if (nonTerminalTable.containsKey(index)) {
            System.err.println("Indices of non-LL(1) production: " + index);
            System.err.println("Already written in the cell with the index from above: " + nonTerminalTable.get(index));
            throw new RuntimeException("THE GRAMMAR IS NOT LL(1)");
        }
    } // doesn't need to be tested
}
