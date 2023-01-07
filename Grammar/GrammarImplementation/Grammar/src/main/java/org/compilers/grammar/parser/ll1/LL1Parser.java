package org.compilers.grammar.parser.ll1;

import org.compilers.grammar.parser.output.ParserOutput;

public interface LL1Parser {
    ParserOutput parse(String word);
}
