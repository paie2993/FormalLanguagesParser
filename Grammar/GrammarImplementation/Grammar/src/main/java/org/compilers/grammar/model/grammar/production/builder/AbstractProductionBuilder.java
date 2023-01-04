package org.compilers.grammar.model.grammar.production.builder;

import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractProductionBuilder implements ProductionBuilder {
    protected Convention convention = null;

    public void setConvention(
            final Set<NonTerminal> nonTerminals,
            final Set<Terminal> terminals
    ) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);

        this.convention = new Convention(nonTerminals, terminals);
    }

    protected static class Convention {
        private final Set<String> nonTerminalsValues;
        private final Set<String> terminalsValues;

        public Convention(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals) {
            this.nonTerminalsValues = stringSet(nonTerminals);
            this.terminalsValues = stringSet(terminals);
        }

        public static Set<String> stringSet(final Set<? extends Symbol> symbols) {
            return symbols.stream()
                    .map(Symbol::value)
                    .collect(Collectors.toUnmodifiableSet());
        }

        public Function<String, Symbol> getConverter(final String string) {
            if (terminalsValues.contains(string)) { // terminal
                return TerminalImpl::new;
            } else if (nonTerminalsValues.contains(string)) { // non-terminal
                return NonTerminalImpl::new;
            }
            throw new IllegalArgumentException(String.format("The string %s does not follow any convention", string));
        }
    }
}
