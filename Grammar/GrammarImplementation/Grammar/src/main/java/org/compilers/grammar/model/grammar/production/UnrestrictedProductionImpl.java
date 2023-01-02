package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class UnrestrictedProductionImpl extends AbstractProduction implements UnrestrictedProduction {
    public UnrestrictedProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        UnrestrictedProduction.validateLeftSide(leftSide);
        UnrestrictedProduction.validateRightSide(rightSide);
    }
}
