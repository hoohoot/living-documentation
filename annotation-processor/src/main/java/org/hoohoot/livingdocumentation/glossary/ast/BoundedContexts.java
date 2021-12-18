package org.hoohoot.livingdocumentation.glossary.ast;

import java.util.List;
import java.util.stream.Collectors;

public record BoundedContexts(List<BoundedContextEntry> boundedContexts)
        implements ToMarkdown {

    @Override
    public String toMarkdown() {
        return boundedContexts.stream().map(BoundedContextEntry::toMarkdown).collect(Collectors.joining("\n\n"));
    }
}
