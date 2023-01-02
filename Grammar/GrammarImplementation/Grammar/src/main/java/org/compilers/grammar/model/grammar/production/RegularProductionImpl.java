package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class RegularProductionImpl extends AbstractProduction implements RegularProduction {
    public RegularProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        RegularProduction.validateLeftSide(leftSide);
        RegularProduction.validateRightSide(rightSide);
    }
}
