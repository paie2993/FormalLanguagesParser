package org.compilers.grammar.ui;


import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.parser.ll1.LL1ParserImpl;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class CommandLineInterface {

    private static final String grammarFile = "src/main/java/org/compilers/grammar/g1.txt";

    private final Grammar<? extends Production> grammar;

    public CommandLineInterface() {
        this.grammar = UnrestrictedGrammar.builder().file(grammarFile).build();
        final var parser = new LL1ParserImpl(ContextFreeGrammar.builder().grammar(this.grammar).build());
        System.out.println(parser.parse("a*(a+a)").asProductionString().stream().map(Production::toString).collect(Collectors.joining(", ")));
        System.out.println(parser.parse("a*(a+a)").asDerivationString().stream().map(sententialForm -> sententialForm.stream().map(Symbol::value).collect(Collectors.joining())).collect(Collectors.joining("=>")));
    }

    private static void printMenu() {
        System.out.println(
                """
                        1. non-terminals
                        2. terminals
                        3. set of productions
                        4. productions for non-terminal
                        5. is context free grammar
                        6. exit
                        """
        );
    }

    public void run() {
        final Scanner scanner = new Scanner(System.in);

        boolean done = false;
        while (!done) {

            printMenu();

            System.out.print("Your option: ");
            final String command = readInput(scanner);

            switch (command) {
                case "1" -> printNonTerminals();
                case "2" -> printTerminals();
                case "3" -> printProductions();
                case "4" -> handleProductionsOfNonTerminal(scanner);
                case "5" -> printIsContextFree();
                case "6" -> done = true;
                default -> System.out.println("Try again");
            }

        }
    }

    private static String readInput(final Scanner scanner) {
        return scanner.nextLine().trim();
    }

    // non-terminals option
    private void printNonTerminals() {
        final var nonTerminals = grammar.nonTerminals();
        final var nonTerminalsString = prepareSetForPrinting(nonTerminals);
        System.out.println(nonTerminalsString);
    }

    // terminals option
    private void printTerminals() {
        final var terminals = grammar.terminals();
        final var terminalsString = prepareSetForPrinting(terminals);
        System.out.println(terminalsString);
    }

    // set of productions option
    private void printProductions() {
        final var productions = grammar.productions();
        productions
                .stream()
                .map(production -> String.format("%s - %d", production, grammar.indexOf(production)))
                .forEach(System.out::println);
//        productions.forEach(System.out::println); // bound method reference
    }

    // productions of non-terminal option
    private void handleProductionsOfNonTerminal(final Scanner scanner) {
        System.out.print("Non-Terminal: ");
        final var nonTerminalString = readInput(scanner);

        final var nonTerminalOptional = validateNonTerminal(nonTerminalString);

        if (nonTerminalOptional.isEmpty()) {
            return;
        }

        final var nonTerminal = nonTerminalOptional.get();
        printProductionsOfNonTerminal(nonTerminal);
    }

    private Optional<NonTerminalImpl> validateNonTerminal(final String nonTerminalString) {
        final var nonTerminal = new NonTerminalImpl(nonTerminalString);

        if (!grammar.containsNonTerminal(nonTerminal)) {
            System.out.println("The given non-terminal " + nonTerminal + " is not in the grammar");
            return Optional.empty();
        }

        return Optional.of(nonTerminal);
    }


    private void printProductionsOfNonTerminal(final NonTerminalImpl nonTerminal) {
        try {
            final ContextFreeGrammar<? extends ContextFreeProduction> cfg = ContextFreeGrammar.builder().grammar(this.grammar).build();
            final var productions = cfg.productionsOf(nonTerminal);
            productions.forEach(System.out::println);
        } catch (RuntimeException exception) {
            System.out.println("The given grammar is not context free");
        }
    }

    // is context free grammar option
    private void printIsContextFree() {
        try {
            ContextFreeGrammar.builder().grammar(this.grammar).build();
            System.out.println("The grammar is context free");
        } catch (RuntimeException exception) {
            System.out.println("The grammar is not context free");
        }
    }

    // set printing option
    private static String prepareSetForPrinting(final Set<?> objects) {
        final var joiner = new StringJoiner(", ", "{ ", " }");
        objects.stream().map(Object::toString).forEach(joiner::add); // unbound method reference
        return joiner.toString();
    }
}
