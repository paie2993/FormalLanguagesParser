package org.compilers.grammar.parser.ll1.configuration;

import org.compilers.grammar.model.parse_table.ParseTable;
import org.compilers.grammar.model.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationImpl implements Configuration {
    private final Stack<Terminal> inputStack;

    private final Stack<Symbol> workStack;

    private final List<Integer> output;

    public ConfigurationImpl(
            final List<? extends Terminal> inputSequence,
            final NonTerminal startSymbol
    ) {
        Objects.requireNonNull(inputSequence);
        Objects.requireNonNull(startSymbol);

        this.inputStack = new Stack<>();
        this.workStack = new Stack<>();
        this.output = new ArrayList<>();

        final List<Terminal> inputSequenceCopy = new ArrayList<>(inputSequence);
        Collections.reverse(inputSequenceCopy);
        this.inputStack.push(ParseTable.DOLLAR_TERMINAL);
        this.inputStack.addAll(inputSequenceCopy);
        this.workStack.push(ParseTable.DOLLAR_TERMINAL);
        this.workStack.push(startSymbol);
    }

    @Override
    public void push(NextMove nextMove) {
        Objects.requireNonNull(nextMove);

        List<Symbol> rightSide = new ArrayList<>(nextMove.rightSide());

        Collections.reverse(rightSide);

        this.workStack.pop();
        this.workStack.addAll(rightSide);
        this.output.add(nextMove.productionNumber());
    }

    @Override
    public void pop() {
        this.inputStack.pop();
        this.workStack.pop();
    }

    @Override
    public Terminal inputStackPeek() {
        return this.inputStack.peek();
    }

    @Override
    public Symbol workStackPeek() {
        return this.workStack.peek();
    }

    @Override
    public Stack<? extends Terminal> inputStack() {
        return inputStack;
    }

    @Override
    public Stack<? extends Symbol> workStack() {
        return workStack;
    }

    @Override
    public List<? extends Integer> output() {
        return output;
    }

    @Override
    public String toString() {
        final var inputStackCopy = new ArrayList<>(this.inputStack);
        final var workStackCopy = new ArrayList<>(this.workStack);
        Collections.reverse(inputStackCopy);
        Collections.reverse(workStackCopy);
        return String.format("(%s, %s, %s)", inputStackCopy.stream().map(Symbol::value).collect(Collectors.joining()), workStackCopy.stream().map(Symbol::value).collect(Collectors.joining()), String.join("", this.output.stream().map(Object::toString).toList()));
    }
}
