package org.compilers.grammar.model.grammar.reader;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

// a simple dataclass that should be returned by a GrammarReader upon reading a grammar from a file
public final record GrammarElements<T extends Production>(
        Set<? extends NonTerminal> nonTerminals,
        Set<? extends Terminal> terminals,
        List<T> productions,
        NonTerminal startingNonTerminal
) {
}
