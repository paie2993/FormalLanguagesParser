package org.compilers.grammar.model.grammar.context_dependent;

import org.compilers.grammar.model.grammar.unrestricted.AbstractUnrestrictedGrammar;
import org.compilers.grammar.model.production.context_dependent.AbstractContextDependentProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

public abstract class AbstractContextDependentGrammar
        <T extends AbstractContextDependentProduction>
        extends AbstractUnrestrictedGrammar<T> {

    public AbstractContextDependentGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<T> productions,
            final NonTerminal startNonTerminal
    ) {
        super(nonTerminals, terminals, productions, startNonTerminal);
    }
}
