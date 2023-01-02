package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class ContextFreeProductionImpl extends AbstractProduction implements Production {
    public ContextFreeProductionImpl(List<Symbol> leftSide, List<Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextFreeProduction.validateLeftSide(leftSide);
        ContextFreeProduction.validateRightSide(rightSide);
    }
}
