package org.compilers.grammar.parser.output;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserOutputImpl implements ParserOutput {
    private final List<? extends ContextFreeProduction> productionsString;

    private final List<? extends List<? extends Symbol>> derivationString;

    public ParserOutputImpl(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar,
            final List<? extends Integer> productionIndicesString
    ) {
        Objects.requireNonNull(grammar);
        Objects.requireNonNull(productionIndicesString);

        this.productionsString = buildProductionString(grammar, productionIndicesString);
        this.derivationString = buildDerivationString(grammar, productionIndicesString);

    }

    @Override
    public List<? extends ContextFreeProduction> asProductionString() {
        return this.productionsString;
    }

    @Override
    public List<? extends List<? extends Symbol>> asDerivationString() {
        return this.derivationString;
    }

    private static List<? extends ContextFreeProduction> buildProductionString(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar,
            final List<? extends Integer> productionIndicesString
    ) {
        return productionIndicesString
                .stream()
                .map(grammar::productionAt)
                .toList();
    }

    private static List<? extends List<? extends Symbol>> buildDerivationString(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar,
            final List<? extends Integer> productionsString
    ) {
        List<List<? extends Symbol>> derivationString = new ArrayList<>();
        int productionIndex = productionsString.get(0);
        int indexOfNonTerminal;
        ContextFreeProduction production = grammar.productionAt(productionIndex);
        derivationString.add(production.leftSide());
        List<Symbol> currentSententialForm = new ArrayList<>(production.rightSide());

        derivationString.add(currentSententialForm);

        for (int index = 1; index < productionsString.size(); index++) {
            currentSententialForm = new ArrayList<>(currentSententialForm);
            productionIndex = productionsString.get(index);
            production = grammar.productionAt(productionIndex);
            indexOfNonTerminal = currentSententialForm.indexOf(production.leftSideNonTerminal());
            currentSententialForm.remove(indexOfNonTerminal);
            currentSententialForm.addAll(indexOfNonTerminal, production.rightSide());

            derivationString.add(currentSententialForm);
        }

        return derivationString;
    }
}
