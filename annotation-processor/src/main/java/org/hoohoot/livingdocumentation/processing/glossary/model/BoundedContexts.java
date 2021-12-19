package org.hoohoot.livingdocumentation.processing.glossary.model;

import org.hoohoot.livingdocumentation.processing.ToGeneratedOutput;

import java.util.List;
import java.util.stream.Collectors;

public record BoundedContexts(List<BoundedContextEntry> boundedContexts) implements ToGeneratedOutput {
    @Override
    public String toGeneratedOutput() {
        return boundedContexts.stream()
                .map(BoundedContextEntry::toGeneratedOutput).collect(Collectors.joining("\n\n"));
    }
}
