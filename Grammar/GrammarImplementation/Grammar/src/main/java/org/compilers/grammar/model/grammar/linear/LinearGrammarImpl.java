package org.compilers.grammar.model.grammar.linear;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class LinearGrammarImpl extends AbstractContextFreeGrammar<LinearProduction> implements LinearGrammar {
    public LinearGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends LinearProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
