package org.compilers.grammar.parser.output.father_sibling_table.entry;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.Objects;

public class TableEntryImpl implements TableEntry {
    private final Symbol info;

    private final Integer parentIndex;

    private final Integer rightSiblingIndex;

    public TableEntryImpl(Symbol info, Integer parentIndex, Integer rightSiblingIndex) {
        this.info = info;
        this.parentIndex = parentIndex;
        this.rightSiblingIndex = rightSiblingIndex;
    }

    @Override
    public Symbol symbol() {
        return this.info;
    }

    @Override
    public int parentIndex() {
        return this.parentIndex;
    }

    @Override
    public int rightSiblingIndex() {
        return this.rightSiblingIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TableEntryImpl that = (TableEntryImpl) o;
        return Objects.equals(info, that.info) && Objects.equals(parentIndex, that.parentIndex) && Objects.equals(rightSiblingIndex, that.rightSiblingIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, parentIndex, rightSiblingIndex);
    }

    @Override
    public String toString() {
        return String.format("(%s, %d, %d)", this.info.value(), this.parentIndex, this.rightSiblingIndex);
    }
}
