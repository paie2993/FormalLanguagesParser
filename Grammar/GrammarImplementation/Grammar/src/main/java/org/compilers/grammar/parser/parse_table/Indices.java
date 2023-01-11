package org.compilers.grammar.parser.parse_table;

import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

public final record Indices(
        Symbol rowIndex,
        Terminal columnIndex
) {
    public static Indices of(final Symbol rowIndex, final Terminal columnIndex) {
        return new Indices(rowIndex, columnIndex);
    }
}
