package org.compilers.grammar.parsing;

import org.compilers.grammar.model.Grammar;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Set;

class ConcatenationTest {

    @org.junit.jupiter.api.Test
    void concatenate() {
        Set<String> s1, s2, result;
        s1 = Set.of("a", "b");
        s2 = Set.of("0", "1");
        Assertions.assertEquals(Concatenation.concatenate(List.of(s1, s2)), s1);
        s1 = Set.of("a", Grammar.EPSILON);
        result = Set.of("a", "0", "1");
        Assertions.assertEquals(Concatenation.concatenate(List.of(s1, s2)), result);
        s1 = Set.of(Grammar.EPSILON);
        s2 = Set.of(Grammar.EPSILON);
        Assertions.assertEquals(Concatenation.concatenate(List.of(s1, s2)), s2);
        s1 = Set.of("a", "b");
        Assertions.assertEquals(Concatenation.concatenate(List.of(s1, s2)), s1);
    }
}