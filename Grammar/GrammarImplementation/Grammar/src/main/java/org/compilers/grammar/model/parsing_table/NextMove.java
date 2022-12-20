package org.compilers.grammar.model.parsing_table;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public record NextMove(List<Symbol> rightSide, int productionNumber) {
}
