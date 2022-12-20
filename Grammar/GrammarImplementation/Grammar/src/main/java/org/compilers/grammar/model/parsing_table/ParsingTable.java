package org.compilers.grammar.model.parsing_table;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.*;

public final class ParsingTable {

    private static final String DOLLAR = "$";
    private static final Terminal DOLLAR_TERMINAL = new Terminal(DOLLAR);

//    private final Map<Map.Entry<NonTerminal, Terminal>, NextMove> nonTerminalTable;
//    private final Map<Map.Entry<Terminal, Terminal>, Action> terminalTable;


    public static ParsingTable of(final Grammar grammar) {
        final var nonTerminals = grammar.nonTerminals();
        final var terminals = grammar.terminals();

        final var terminalsEnriched = enrichTerminals(terminals);
        final var terminalTable = initializeTerminalTable(terminals);

        final var nonTerminalsTable = initializeNonTerminalTable(grammar, terminalsEnriched);
        return null;
    }

    private static Map<Map.Entry<NonTerminal, Terminal>, NextMove> initializeNonTerminalTable(
            final Grammar grammar,
            final Set<Terminal> terminalsEnriched

    ) {
        final var terminals = grammar.terminals();
        final var nonTerminals = grammar.nonTerminals();
        final var productions = grammar.productions();

        final var first = Grammar.first(terminals, nonTerminals, productions);
        final var follow = grammar.follow();

        nonTerminals.forEach(nonTerminal -> {
            final var productionsOfNonTerminal = grammar.productionsOf(nonTerminal);
            terminalsEnriched.forEach(terminal -> {

                if (first.get(nonTerminal).contains(terminal.value())) { // null pointer exception
                    final var firstOfNonTerminal = first.get(nonTerminal);
                }
            });
        });

        return null;
    }


    private static Set<Terminal> enrichTerminals(final Set<Terminal> terminals) {
        final var newTerminals = new HashSet<>(terminals);
        newTerminals.add(DOLLAR_TERMINAL);
        return newTerminals;
    }

    private static Map<Map.Entry<Terminal, Terminal>, Action> initializeTerminalTable(final Set<Terminal> terminals) {
        final var terminalTable = new HashMap<Map.Entry<Terminal, Terminal>, Action>();
        terminals.forEach(terminal -> {
            terminals.forEach(innerTerminal -> {
                if (terminal.equals(innerTerminal)) {
                    terminalTable.put(new AbstractMap.SimpleEntry<>(terminal, terminal), Action.POP);
                } else {
                    terminalTable.put(new AbstractMap.SimpleEntry<>(terminal, innerTerminal), Action.ERROR);
                }
            });
        });
        terminalTable.put(new AbstractMap.SimpleEntry<>(DOLLAR_TERMINAL, DOLLAR_TERMINAL), Action.ACCEPT);
        return terminalTable;
    }
}
