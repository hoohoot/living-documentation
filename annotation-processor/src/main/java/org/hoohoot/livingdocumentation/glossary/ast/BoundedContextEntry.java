package org.hoohoot.livingdocumentation.glossary.ast;

import java.util.List;
import java.util.stream.Collectors;

public record BoundedContextEntry(
        String title,
        List<String> links,
        List<DomainEntityEntry> entries
) implements ToMarkdown {

    @Override
    public String toMarkdown() {
        var links = String.join("\n- ", this.links);
        var entities = entries.stream()
                .map(DomainEntityEntry::toMarkdown)
                .collect(Collectors.joining("\n\n"));

        return String.format("""
                ## %s
                %s
                %s
                """, title, links, entities);
    }
}
