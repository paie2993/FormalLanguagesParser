package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class ContextFreeGrammarImpl extends AbstractContextFreeGrammar<ContextFreeProduction> implements ContextFreeGrammar<ContextFreeProduction> {
    public ContextFreeGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends ContextFreeProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
