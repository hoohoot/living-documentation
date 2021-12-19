package org.hoohoot.livingdocumentation.processing.glossary.model;

import org.hoohoot.livingdocumentation.processing.ToGeneratedOutput;

import java.util.List;
import java.util.stream.Collectors;

public record BoundedContextEntry(
        String title,
        List<String> links,
        List<DomainEntityEntry> entries
) implements ToGeneratedOutput {

    @Override
    public String toGeneratedOutput() {
        var links = String.join("\n- ", this.links);
        var entities = entries.stream()
                .map(DomainEntityEntry::toGeneratedOutput)
                .collect(Collectors.joining("\n\n"));

        return String.format("""
                ## %s
                %s
                %s
                """, title, links, entities);
    }
}
