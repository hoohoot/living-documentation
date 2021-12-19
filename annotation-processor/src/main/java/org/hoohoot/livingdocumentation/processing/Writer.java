package org.hoohoot.livingdocumentation.processing;

import org.hoohoot.livingdocumentation.exception.ResourceNotFoundException;

import java.io.IOException;

public interface Writer<T extends ToGeneratedOutput> {
    default void write(T t) throws IOException, ResourceNotFoundException {
        var content = t.toGeneratedOutput();
        FileHelper.write(content, outputFile());
    }

    String outputFile();
}
