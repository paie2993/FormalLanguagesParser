package org.compilers.grammar.parser.output.father_sibling_table.entry;

import org.compilers.grammar.model.vocabulary.Symbol;

public interface TableEntry {
    Symbol symbol();

    int parentIndex();

    int rightSiblingIndex();
}
