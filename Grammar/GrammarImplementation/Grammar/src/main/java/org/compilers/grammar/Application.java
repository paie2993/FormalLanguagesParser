package org.compilers.grammar;

import org.compilers.grammar.ui.CommandLineInterface;

public final class Application {
    public static void main(final String[] args) {
        final var cmd = new CommandLineInterface();
        cmd.run();
    }
}
