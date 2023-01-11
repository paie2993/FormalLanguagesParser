package org.compilers.grammar.model.grammar.unrestricted;

import org.compilers.grammar.model.production.unrestricted.AbstractUnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

public final class UnrestrictedGrammar extends AbstractUnrestrictedGrammar<AbstractUnrestrictedProduction> {

    public UnrestrictedGrammar(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final List<AbstractUnrestrictedProduction> productions,
            final NonTerminal startNonTerminal
    ) {
        super(nonTerminals, terminals, productions, startNonTerminal);
    }
}
