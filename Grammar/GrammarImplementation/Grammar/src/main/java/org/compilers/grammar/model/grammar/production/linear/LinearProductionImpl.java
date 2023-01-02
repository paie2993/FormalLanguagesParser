package org.compilers.grammar.model.grammar.production.linear;

import org.compilers.grammar.model.grammar.production.AbstractProduction;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class LinearProductionImpl extends AbstractProduction implements LinearProduction {
    public LinearProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        LinearProduction.validateLeftSide(leftSide);
        LinearProduction.validateRightSide(rightSide);
    }
}
