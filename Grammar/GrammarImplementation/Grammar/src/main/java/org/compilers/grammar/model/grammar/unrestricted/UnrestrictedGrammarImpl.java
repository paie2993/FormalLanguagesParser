package org.compilers.grammar.model.grammar.unrestricted;

import org.compilers.grammar.model.grammar.AbstractGrammar;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class UnrestrictedGrammarImpl extends AbstractGrammar<UnrestrictedProduction> implements UnrestrictedGrammar {
    public UnrestrictedGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends UnrestrictedProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
