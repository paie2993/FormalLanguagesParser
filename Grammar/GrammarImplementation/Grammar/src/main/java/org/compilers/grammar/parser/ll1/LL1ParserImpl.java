package org.compilers.grammar.parser.ll1;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.parse_table.ParseTable;
import org.compilers.grammar.model.parse_table.ParseTableImpl;
import org.compilers.grammar.model.parse_table.action.Action;
import org.compilers.grammar.model.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.compilers.grammar.parser.ll1.configuration.ConfigurationImpl;
import org.compilers.grammar.parser.output.ParserOutput;
import org.compilers.grammar.parser.output.ParserOutputImpl;

import java.util.*;

public class LL1ParserImpl implements LL1Parser {
    private final ContextFreeGrammar<? extends ContextFreeProduction> grammar;

    private final ParseTable parseTable;

    public LL1ParserImpl(ContextFreeGrammar<? extends ContextFreeProduction> grammar) {
        this.grammar = grammar;
        this.parseTable = ParseTableImpl.of(grammar);
    }

    @Override
    public ParserOutput parse(String word) {
        final List<? extends Terminal> wordAsList = word.chars().mapToObj(e -> (char)e).map(String::valueOf).map(TerminalImpl::new).toList();
        ConfigurationImpl initialConfiguration = new ConfigurationImpl(wordAsList, this.grammar.startSymbol());

        if (this.parse(initialConfiguration)) {
            return new ParserOutputImpl(this.grammar, initialConfiguration.output());
        }
        return null;
    }

    private boolean parse(final ConfigurationImpl configuration) {
        while (true) {
            final Symbol workStackPeek = configuration.workStackPeek();
            final Terminal inputStackPeek = configuration.inputStackPeek();
            if (NonTerminal.isNonTerminal(workStackPeek)) {
                final NextMove nextMove = this.parseTable.get((NonTerminal) workStackPeek, inputStackPeek);
                if (Objects.isNull(nextMove)) {
                    return false;
                }
                configuration.push(nextMove);
            } else {
                final Action action = this.parseTable.get((Terminal) workStackPeek, inputStackPeek);
                if (Objects.isNull(action)) {
                    return false;
                }
                if (action.equals(Action.ACCEPT)) {
                    return true;
                }
                configuration.pop();
            }
        }
    }
}
