package org.compilers.grammar.parser.ll1;

import java.util.List;

public interface LL1Parser {
    List<? extends Integer> parse(String word);
}
