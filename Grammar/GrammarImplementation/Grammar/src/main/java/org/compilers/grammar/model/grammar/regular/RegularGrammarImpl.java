package org.compilers.grammar.model.grammar.regular;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class RegularGrammarImpl extends AbstractContextFreeGrammar<RegularProduction> implements RegularGrammar<RegularProduction> {
    public RegularGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends RegularProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
        RegularGrammar.validateGrammar(productions, startSymbol);
    }
}
