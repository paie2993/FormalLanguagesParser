package org.compilers.grammar.model.grammar.linear;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;

public interface LinearGrammar<T extends LinearProduction> extends ContextFreeGrammar<T> {
}
