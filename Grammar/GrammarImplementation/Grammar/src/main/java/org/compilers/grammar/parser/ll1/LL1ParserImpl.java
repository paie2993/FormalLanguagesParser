package org.compilers.grammar.parser.ll1;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.parser.ll1.configuration.Configuration;
import org.compilers.grammar.parser.output.ParserOutputImpl;
import org.compilers.grammar.parser.parse_table.ParseTable;
import org.compilers.grammar.parser.parse_table.ParseTableImpl;
import org.compilers.grammar.parser.parse_table.action.Action;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.compilers.grammar.parser.ll1.configuration.ConfigurationImpl;
import org.compilers.grammar.parser.output.ParserOutput;

import java.util.*;

// this whole class needs testing
public final class LL1ParserImpl implements LL1Parser
{

    private final AbstractContextFreeGrammar<? extends AbstractContextFreeProduction> grammar;

    private final ParseTable parseTable;

    // constructor
    public LL1ParserImpl(final AbstractContextFreeGrammar<AbstractContextFreeProduction> grammar)
    {
        this.grammar = grammar;
        this.parseTable = ParseTableImpl.of(grammar);
    }

    // parsing algorithm
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ParserOutput parse(final List<String> word)
    {
        final var configuration = parseWord(word);
        return ParserOutputImpl.of(grammar, configuration.output());
    }

    private Configuration parseWord(final List<String> word)
    {

        final var wordTerminals = word.stream().map(TerminalImpl::new).toList();
        final var startNonTerminal = grammar.startNonTerminal();
        final var configuration = new ConfigurationImpl(wordTerminals, startNonTerminal);

        while (true)
        {

            final var inputStackTop = configuration.inputStackPeek();
            final var workStackTop = configuration.workStackPeek();

            if (NonTerminal.isInstance(workStackTop))
            {

                final var nonTerminalTop = (NonTerminal) workStackTop;
                final var nextMove = parseTable.get(nonTerminalTop, inputStackTop);

                if (nextMove == null)
                {
                    throw new RuntimeException("Error in parsing table, at row '" + nonTerminalTop + "', column '" + inputStackTop + "'");
                }

                configuration.push(nextMove);

            }
            else
            {

                final var terminalTop = (Terminal) workStackTop;
                final var action = parseTable.get(terminalTop, inputStackTop);

                if (action == null)
                {
                    throw new RuntimeException("Error in parsing table, at row " + terminalTop + ", column " + inputStackTop);
                }

                if (action.equals(Action.ACCEPT))
                {
                    return configuration;
                }

                configuration.pop();

            }
        }
    }
}