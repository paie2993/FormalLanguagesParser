package org.compilers.grammar.model.grammar.right_linear.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammar;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammarImpl;

import java.util.Set;
import java.util.function.BiFunction;

public class RightLinearGrammarBuilder extends AbstractGrammarBuilder<RightLinearProduction, RightLinearGrammar<? extends RightLinearProduction>> {
    public RightLinearGrammarBuilder() {
    }

    @Override
    public RightLinearGrammar<? extends RightLinearProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends RightLinearProduction>, RightLinearGrammar<? extends RightLinearProduction>> create = (grammar, productions) -> new RightLinearGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends RightLinearProduction> productionBuilder = RightLinearProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}