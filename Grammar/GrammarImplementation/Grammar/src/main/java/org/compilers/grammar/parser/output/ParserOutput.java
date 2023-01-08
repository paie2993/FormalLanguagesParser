package org.compilers.grammar.parser.output;

import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;

import java.util.List;

public interface ParserOutput {
    List<? extends ContextFreeProduction> asProductionString();

    List<? extends List<? extends Symbol>> asDerivationString();

    List<? extends TableEntry> asFatherSiblingTable();
}
