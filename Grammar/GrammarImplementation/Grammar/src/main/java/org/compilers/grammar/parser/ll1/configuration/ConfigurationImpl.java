package org.compilers.grammar.parser.ll1.configuration;

import org.compilers.grammar.parser.parse_table.ParseTable;
import org.compilers.grammar.parser.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;

// this whole class needs some tests
public class ConfigurationImpl implements Configuration {

    private final Stack<Terminal> inputStack;

    private final Stack<Symbol> workStack;

    private final List<Integer> output;

    // constructor
    public ConfigurationImpl(
            final List<? extends Terminal> inputSequence,
            final NonTerminal startSymbol
    ) {
        Objects.requireNonNull(inputSequence);
        Objects.requireNonNull(startSymbol);

        this.inputStack = new Stack<>();
        this.workStack = new Stack<>();
        this.output = new ArrayList<>();

        final var inputSequenceCopy = new ArrayList<>(inputSequence);
        Collections.reverse(inputSequenceCopy);

        this.inputStack.push(ParseTable.DOLLAR_TERMINAL);
        this.inputStack.addAll(inputSequenceCopy);

        this.workStack.push(ParseTable.DOLLAR_TERMINAL);
        this.workStack.push(startSymbol);
    }

    // modifiers of stacks
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // modifies the working stack
    @Override
    public void push(final NextMove nextMove) {
        Objects.requireNonNull(nextMove);

        var rightSide = new ArrayList<>(nextMove.rightSide());
        Collections.reverse(rightSide);

        workStack.pop();
        workStack.addAll(rightSide);

        output.add(nextMove.productionNumber());
    }

    // modifies both stacks
    @Override
    public void pop() {
        inputStack.pop();
        workStack.pop();
    }

    // peeks
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Terminal inputStackPeek() {
        return inputStack.peek();
    }

    @Override
    public Symbol workStackPeek() {
        return workStack.peek();
    }


    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    // general utility
    @Override
    public String toString() {
        final var inputStackCopy = new ArrayList<>(inputStack);
        final var workStackCopy = new ArrayList<>(workStack);
        Collections.reverse(inputStackCopy);
        Collections.reverse(workStackCopy);
        return "(" +
                inputStackCopy.stream().map(Symbol::value).collect(Collectors.joining(" ")) +
                ", " +
                workStackCopy.stream().map(Symbol::value).collect(Collectors.joining(" ")) +
                ", " +
                output.stream().map(Object::toString).toList() +
                ")";
    }
}
