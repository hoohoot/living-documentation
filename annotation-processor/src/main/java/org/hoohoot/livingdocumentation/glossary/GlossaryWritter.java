package org.hoohoot.livingdocumentation.glossary;

import org.hoohoot.livingdocumentation.FileHelper;
import org.hoohoot.livingdocumentation.glossary.ast.BoundedContexts;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;

public class GlossaryWritter {
    private GlossaryWritter() {
    }

    public static void write(BoundedContexts boundedContexts, ProcessingEnvironment processingEnvironment) throws IOException {
        var markdown = boundedContexts.toMarkdown();
        FileHelper.write(markdown, "glossary.md");
    }
}
