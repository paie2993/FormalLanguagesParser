package org.compilers.grammar.parser.output;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntryImpl;

import java.util.*;

public class ParserOutputImpl implements ParserOutput {
    private final List<? extends ContextFreeProduction> productionsString;

    private final List<? extends List<? extends Symbol>> derivationString;

    private final List<? extends TableEntry> table;

    public ParserOutputImpl(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar,
            final List<? extends Integer> productionIndicesString
    ) {
        Objects.requireNonNull(grammar);
        Objects.requireNonNull(productionIndicesString);

        this.productionsString = buildProductionString(grammar, productionIndicesString);
        this.derivationString = buildDerivationString(grammar, productionIndicesString);
        this.table = buildFatherSiblingTable(grammar, productionIndicesString);
    }

    @Override
    public List<? extends ContextFreeProduction> asProductionString() {
        return this.productionsString;
    }

    @Override
    public List<? extends List<? extends Symbol>> asDerivationString() {
        return this.derivationString;
    }

    @Override
    public List<? extends TableEntry> asFatherSiblingTable() {
        return this.table;
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
            final List<? extends Integer> productionIndicesString
    ) {
        List<List<? extends Symbol>> derivationString = new ArrayList<>();
        int productionIndex = productionIndicesString.get(0);
        int indexOfNonTerminal;
        ContextFreeProduction production = grammar.productionAt(productionIndex);
        derivationString.add(production.leftSide());
        List<Symbol> currentSententialForm = new ArrayList<>(production.rightSide());

        derivationString.add(currentSententialForm);

        for (int index = 1; index < productionIndicesString.size(); index++) {
            currentSententialForm = new ArrayList<>(currentSententialForm);
            productionIndex = productionIndicesString.get(index);
            production = grammar.productionAt(productionIndex);
            indexOfNonTerminal = currentSententialForm.indexOf(production.leftSideNonTerminal());
            currentSententialForm.remove(indexOfNonTerminal);
            currentSententialForm.addAll(indexOfNonTerminal, production.rightSide());

            derivationString.add(currentSententialForm);
        }

        return derivationString;
    }

    private static List<? extends TableEntry> buildFatherSiblingTable(
            final ContextFreeGrammar<? extends ContextFreeProduction> grammar,
            final List<? extends Integer> productionIndicesString
    ) {
        final List<TableEntry> tableEntries = new ArrayList<>();


        int productionIndex = productionIndicesString.get(0);
        ContextFreeProduction production = grammar.productionAt(productionIndex);
        tableEntries.add(new TableEntryImpl(production.leftSideNonTerminal(), -1, -1));
        List<Map.Entry<Symbol, Integer>> currentSententialForm = new ArrayList<>();
        currentSententialForm.add(new AbstractMap.SimpleImmutableEntry<>(production.leftSideNonTerminal(), 0));


        for (Integer integer : productionIndicesString) {
            productionIndex = integer;
            final ContextFreeProduction currentProduction = grammar.productionAt(productionIndex);
            Map.Entry<Symbol, Integer> father = currentSententialForm.stream().filter(entry -> currentProduction.leftSideNonTerminal().equals(entry.getKey())).findFirst().orElse(null);
            final int indexOfNonTerminal = currentSententialForm.indexOf(father);
            if (Objects.isNull(father)) {
                continue;
            }
            final int fatherIndex = father.getValue();
            currentSententialForm.remove(father);
            if (currentProduction.rightSide().isEmpty()) {
                continue;
            }
            TableEntry currentSibling = new TableEntryImpl(currentProduction.rightSide().get(0), fatherIndex, -1);
            tableEntries.add(currentSibling);
            currentSententialForm.add(indexOfNonTerminal, new AbstractMap.SimpleImmutableEntry<>(currentSibling.symbol(), tableEntries.indexOf(currentSibling)));
            for (int siblingIndex = 1; siblingIndex < currentProduction.rightSide().size(); siblingIndex++) {
                currentSibling = new TableEntryImpl(currentProduction.rightSide().get(siblingIndex), fatherIndex, tableEntries.indexOf(currentSibling));
                tableEntries.add(currentSibling);
                currentSententialForm.add(indexOfNonTerminal + siblingIndex, new AbstractMap.SimpleImmutableEntry<>(currentSibling.symbol(), tableEntries.indexOf(currentSibling)));
            }
        }

        return tableEntries;
    }
}
