package org.compilers.grammar.model.grammar.production.context_free;

import org.compilers.grammar.model.grammar.production.AbstractProduction;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public class ContextFreeProductionImpl extends AbstractProduction implements Production {
    public ContextFreeProductionImpl(final List<Symbol> leftSide, final List<Symbol> rightSide) {
        super(leftSide, rightSide);
        ContextFreeProduction.validateLeftSide(leftSide);
        ContextFreeProduction.validateRightSide(rightSide);
    }
}
