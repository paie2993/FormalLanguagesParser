package org.compilers.grammar.model.grammar.right_linear;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public class RightLinearGrammarImpl extends AbstractContextFreeGrammar<RightLinearProduction> implements RightLinearGrammar<RightLinearProduction> {
    public RightLinearGrammarImpl(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals,
            final Set<? extends RightLinearProduction> productions,
            final NonTerminal startSymbol) {
        super(nonTerminals, terminals, productions, startSymbol);
    }
}
