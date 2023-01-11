package org.compilers.grammar.parser.parse_table;

import org.compilers.grammar.parser.parse_table.action.Action;
import org.compilers.grammar.parser.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

public interface ParseTable {

    String DOLLAR = "$";

    Terminal DOLLAR_TERMINAL = new TerminalImpl(DOLLAR);

    Action get(final Terminal rowTerminal, final Terminal columnTerminal);

    NextMove get(final NonTerminal rowNonTerminal, final Terminal columnTerminal);
}
