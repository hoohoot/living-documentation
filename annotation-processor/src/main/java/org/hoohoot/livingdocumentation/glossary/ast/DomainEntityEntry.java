package org.hoohoot.livingdocumentation.glossary.ast;

import java.util.List;

public record DomainEntityEntry(String name,
                                String description,
                                List<String> links) implements ToMarkdown {

    @Override
    public String toMarkdown() {
        var links = String.join("\n- ", this.links);

        return String.format("""
                ### %s
                %s
                %s
                """, name, description, links);
    }
}
