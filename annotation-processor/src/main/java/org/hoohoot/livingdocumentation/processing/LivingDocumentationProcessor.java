package org.hoohoot.livingdocumentation.processing;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import java.io.IOException;

public interface LivingDocumentationProcessor<W extends Writer<T>, T extends ToGeneratedOutput> {
    default void process(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) throws IOException {
        if (!roundEnvironment.processingOver()) {
            final var output = output(roundEnvironment, processingEnvironment);
            final var writer = writer();
            writer.write(output);
        }
    }

    W writer();
    T output(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) throws IOException;
}
