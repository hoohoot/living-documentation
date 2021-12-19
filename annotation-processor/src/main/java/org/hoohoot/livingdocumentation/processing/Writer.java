package org.hoohoot.livingdocumentation.processing;

import java.io.IOException;

public interface Writer<T extends ToGeneratedOutput> {
    default void write(T t) throws IOException {
        var content = t.toGeneratedOutput();
        FileHelper.write(content, outputFile());
    }

    String outputFile();
}
