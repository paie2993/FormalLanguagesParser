package org.compilers.grammar.model.grammar.right_linear.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammar;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammarImpl;

import java.util.Set;

public class RightLinearGrammarBuilder extends AbstractGrammarBuilder<RightLinearProduction, RightLinearGrammar<? extends RightLinearProduction>> {
    public RightLinearGrammarBuilder() {
    }

    @Override
    public RightLinearGrammar<? extends RightLinearProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends RightLinearProduction> productionBuilder = RightLinearProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends RightLinearProduction> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final RightLinearGrammar<? extends RightLinearProduction> rightLinearGrammar = new RightLinearGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return rightLinearGrammar;
    }
}
