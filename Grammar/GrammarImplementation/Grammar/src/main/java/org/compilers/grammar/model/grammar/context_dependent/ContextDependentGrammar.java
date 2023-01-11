package org.compilers.grammar.model.grammar.context_dependent;

import org.compilers.grammar.model.production.context_dependent.AbstractContextDependentProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

public final class ContextDependentGrammar extends AbstractContextDependentGrammar<AbstractContextDependentProduction> {

    public ContextDependentGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<AbstractContextDependentProduction> productions,
            final NonTerminal startSymbol
    ) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
