package org.compilers.grammar.model.grammar.production.right_linear;

import org.compilers.grammar.model.grammar.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;

public class RightLinearProductionImpl extends AbstractContextFreeProduction implements RightLinearProduction {
    public RightLinearProductionImpl(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
        RightLinearProduction.validateLeftSide(leftSide);
        RightLinearProduction.validateRightSide(rightSide);
    }

    public RightLinearProductionImpl(final NonTerminal leftSide, final List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
        RightLinearProduction.validateLeftSide(List.of(leftSide));
        RightLinearProduction.validateRightSide(rightSide);
    }
}