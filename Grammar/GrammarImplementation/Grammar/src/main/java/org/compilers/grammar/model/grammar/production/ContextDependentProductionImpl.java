package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class ContextDependentProductionImpl extends AbstractProduction implements ContextDependentProduction {
    public ContextDependentProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextDependentProduction.validateLeftSide(leftSide);
        ContextDependentProduction.validateRightSide(rightSide);
    }
}
