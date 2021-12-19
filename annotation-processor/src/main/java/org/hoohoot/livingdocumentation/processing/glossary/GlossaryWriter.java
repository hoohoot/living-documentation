package org.hoohoot.livingdocumentation.processing.glossary;

import org.hoohoot.livingdocumentation.processing.Writer;
import org.hoohoot.livingdocumentation.processing.glossary.model.BoundedContexts;

public record GlossaryWriter() implements Writer<BoundedContexts> {
    @Override
    public String outputFile() {
        return "glossary.md";
    }
}
