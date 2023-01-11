package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

public final class ContextFreeGrammar extends AbstractContextFreeGrammar<AbstractContextFreeProduction> {

    public ContextFreeGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<AbstractContextFreeProduction> productions,
            final NonTerminal startNonTerminal
    ) {
        super(nonTerminals, terminals, productions, startNonTerminal);
    }
}
