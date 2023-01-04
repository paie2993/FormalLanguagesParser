package org.compilers.grammar.model.grammar.context_dependent;

import org.compilers.grammar.model.grammar.AbstractGrammar;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class ContextDependentGrammarImpl extends AbstractGrammar<ContextDependentProduction> implements ContextDependentGrammar {
    public ContextDependentGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends ContextDependentProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
