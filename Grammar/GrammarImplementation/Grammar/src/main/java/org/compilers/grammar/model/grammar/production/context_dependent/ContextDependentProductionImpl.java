package org.compilers.grammar.model.grammar.production.context_dependent;

import org.compilers.grammar.model.grammar.production.AbstractProduction;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class ContextDependentProductionImpl extends AbstractProduction implements ContextDependentProduction {
    public ContextDependentProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextDependentProduction.validateLeftSide(leftSide);
        ContextDependentProduction.validateRightSide(rightSide);
    }
}
