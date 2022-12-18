package org.compilers.grammar.model.grammar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class GrammarTest {

    @Test
    void concatenate1() {
        Set<String> s1, s2, result;
        s1 = Set.of("a", "b");
        s2 = Set.of("0", "1");
        Assertions.assertEquals(Grammar.concatenate1(List.of(s1, s2)), s1);
        s1 = Set.of("a", Grammar.EPSILON);
        result = Set.of("a", "0", "1");
        Assertions.assertEquals(Grammar.concatenate1(List.of(s1, s2)), result);
        s1 = Set.of(Grammar.EPSILON);
        s2 = Set.of(Grammar.EPSILON);
        Assertions.assertEquals(Grammar.concatenate1(List.of(s1, s2)), s2);
        s1 = Set.of("a", "b");
        Assertions.assertEquals(Grammar.concatenate1(List.of(s1, s2)), s1);
    }

    @Test
    void first() {
    }

    @Test
    void follow() {
    }
}