package org.compilers.grammar.parser.output;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntryImpl;

import java.util.*;

public class ParserOutputImpl implements ParserOutput
{

    private final List<? extends AbstractContextFreeProduction> productionsString;

    private final List<? extends List<? extends Symbol>> derivationString;

    private final List<? extends TableEntry> table;

    // constructor
    public ParserOutputImpl(
        final List<? extends AbstractContextFreeProduction> productionsString,
        final List<? extends List<? extends Symbol>> derivationString,
        final List<? extends TableEntry> table
    )
    {
        Objects.requireNonNull(productionsString);
        Objects.requireNonNull(derivationString);
        Objects.requireNonNull(table);
        this.productionsString = productionsString;
        this.derivationString = derivationString;
        this.table = table;
    }

    public static ParserOutputImpl of(
        final AbstractContextFreeGrammar<? extends AbstractContextFreeProduction> grammar,
        final List<? extends Integer> productionIndicesString
    )
    {
        Objects.requireNonNull(grammar);
        Objects.requireNonNull(productionIndicesString);

        final var productionsString = buildProductionString(grammar, productionIndicesString);
        final var derivationsString = buildDerivationString(grammar, productionIndicesString);
        final var table = buildFatherSiblingTable(grammar, productionIndicesString);

        return new ParserOutputImpl(productionsString, derivationsString, table);
    }

    // getters
    @Override
    public List<? extends AbstractContextFreeProduction> asProductionString()
    {
        return productionsString;
    }

    @Override
    public List<? extends List<? extends Symbol>> asDerivationString()
    {
        return derivationString;
    }

    @Override
    public List<? extends TableEntry> asFatherSiblingTable()
    {
        return this.table;
    }

    // builders
    private static List<? extends AbstractContextFreeProduction> buildProductionString(
        final AbstractContextFreeGrammar<? extends AbstractContextFreeProduction> grammar,
        final List<? extends Integer> productionIndicesString
    )
    {
        return productionIndicesString.stream().map(grammar::at).toList();
    }

    // production string is the output of the LL1 parsing algorithm, ex: 14714721....
    private static List<? extends List<? extends Symbol>> buildDerivationString(
        final AbstractContextFreeGrammar<? extends AbstractContextFreeProduction> grammar,
        final List<? extends Integer> productionsSequence
    )
    {
        // initialize the derivation sequence (final result like so: S => 𝜶1 => 𝜶2 =>... => 𝜶n = w)
        List<List<? extends Symbol>> derivationSequence = new ArrayList<>();

        // get the first production from the productions sequence
        final var firstProductionIndex = productionsSequence.get(0);
        final var firstProduction = grammar.at(firstProductionIndex);

        // add the left side of the first production to the derivation sequence
        derivationSequence.add(firstProduction.leftSide());

        // the first 'sentential form' in the list of derivations strings is the right side of the first production
        List<Symbol> currentSententialForm = new ArrayList<>(firstProduction.rightSide());

        // add the first sentential form as the second element in the derivation sequence (after copying it)
        final var firstSententialFormCopy = new ArrayList<>(currentSententialForm);
        derivationSequence.add(firstSententialFormCopy);

        // for every production (production index from the 'productionsSequence')
        for (int index = 1; index < productionsSequence.size(); index++)
        {

            // get the production index (index in the grammar)
            final var currentProductionIndex = productionsSequence.get(index);
            // get the current production from the grammar, using the index from the productions string
            final var currentProduction = grammar.at(currentProductionIndex);

            // from left to right, get the index of the current non-terminal (the current non-terminal of the iteration
            // is the non-terminal on the left side of the current production)
            final var indexOfNonTerminal = currentSententialForm.indexOf(currentProduction.leftSideNonTerminal());
            // using the index of the current non-terminal, remove it from the current sentential form, and at the
            // index from which the current non-terminal has been removed, ...
            currentSententialForm.remove(indexOfNonTerminal);

            // ... add instead the right side of the current production
            final var rightSide = currentProduction.rightSide();
            currentSententialForm.addAll(indexOfNonTerminal, rightSide);

            // make a copy of the current sentential form and add it to the derivation string
            final var sententialFormCopy = new ArrayList<>(currentSententialForm);
            derivationSequence.add(sententialFormCopy);
        }

        return derivationSequence;
    } // tested, good

    private static List<? extends TableEntry> buildFatherSiblingTable(
        final AbstractContextFreeGrammar<? extends AbstractContextFreeProduction> grammar,
        final List<? extends Integer> productionIndicesString
    )
    {
        final List<TableEntry> tableEntries = new ArrayList<>();

        int productionIndex = productionIndicesString.get(0);
        var production = grammar.at(productionIndex);
        tableEntries.add(new TableEntryImpl(production.leftSideNonTerminal(), -1, -1));
        List<Map.Entry<Symbol, Integer>> currentSententialForm = new ArrayList<>();
        currentSententialForm.add(new AbstractMap.SimpleImmutableEntry<>(production.leftSideNonTerminal(), 0));

        for (Integer integer : productionIndicesString)
        {
            productionIndex = integer;
            final var currentProduction = grammar.at(productionIndex);
            Map.Entry<Symbol, Integer> father = currentSententialForm.stream().filter(entry -> currentProduction.leftSideNonTerminal().equals(entry.getKey())).findFirst().orElse(null);
            final int indexOfNonTerminal = currentSententialForm.indexOf(father);
            if (Objects.isNull(father))
            {
                continue;
            }
            final int fatherIndex = father.getValue();
            currentSententialForm.remove(father);
            if (currentProduction.rightSide().isEmpty())
            {
                continue;
            }
            TableEntry currentSibling = new TableEntryImpl(currentProduction.rightSide().get(0), fatherIndex, -1);
            tableEntries.add(currentSibling);
            currentSententialForm.add(indexOfNonTerminal, new AbstractMap.SimpleImmutableEntry<>(currentSibling.symbol(), tableEntries.indexOf(currentSibling)));
            for (int siblingIndex = 1; siblingIndex < currentProduction.rightSide().size(); siblingIndex++)
            {
                currentSibling = new TableEntryImpl(currentProduction.rightSide().get(siblingIndex), fatherIndex, tableEntries.indexOf(currentSibling));
                tableEntries.add(currentSibling);
                currentSententialForm.add(indexOfNonTerminal + siblingIndex, new AbstractMap.SimpleImmutableEntry<>(currentSibling.symbol(), tableEntries.indexOf(currentSibling)));
            }
        }
        return tableEntries;
    }
}
