package org.hoohoot.livingdocumentation;

import javax.lang.model.element.Name;
import java.util.stream.IntStream;

public record PackageNameFixture(String name) implements Name {

    @Override
    public int length() {
        return this.name.length();
    }

    @Override
    public char charAt(int index) {
        return this.name.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.name.subSequence(start, end);
    }

    @Override
    public IntStream chars() {
        return this.name.chars();
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return this.name.contentEquals(cs);
    }

    @Override
    public String toString() {
        return this.name;
    }
}