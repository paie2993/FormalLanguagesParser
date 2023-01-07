package org.compilers.grammar.model.parse_table;

import org.compilers.grammar.model.parse_table.action.Action;
import org.compilers.grammar.model.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

public interface ParseTable {
    String DOLLAR = "$";
    Terminal DOLLAR_TERMINAL = new TerminalImpl(DOLLAR);

    NextMove get(final NonTerminal nonTerminal, final Terminal terminal);

    Action get(final Terminal terminal1, final Terminal terminal2);
}
