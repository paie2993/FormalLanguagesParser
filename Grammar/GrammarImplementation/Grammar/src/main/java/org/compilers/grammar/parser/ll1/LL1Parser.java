package org.compilers.grammar.parser.ll1;

import org.compilers.grammar.parser.output.ParserOutput;

import java.util.List;

public interface LL1Parser {
    ParserOutput parse(final List<? extends String> wordAsList);
}
