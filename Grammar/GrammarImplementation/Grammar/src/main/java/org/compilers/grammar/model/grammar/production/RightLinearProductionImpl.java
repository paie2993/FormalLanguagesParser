package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class RightLinearProductionImpl extends AbstractProduction implements RightLinearProduction {
    public RightLinearProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        RightLinearProduction.validateLeftSide(leftSide);
        RightLinearProduction.validateRightSide(rightSide);
    }
}
