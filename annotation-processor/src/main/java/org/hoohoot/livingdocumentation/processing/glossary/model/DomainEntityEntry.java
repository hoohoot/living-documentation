package org.hoohoot.livingdocumentation.processing.glossary.model;

import org.hoohoot.livingdocumentation.processing.ToGeneratedOutput;

import java.util.List;

public record DomainEntityEntry(String name,
                                String description,
                                List<String> links
) implements ToGeneratedOutput {

    @Override
    public String toGeneratedOutput() {
        var links = String.join("\n- ", this.links);

        return String.format("""
                ### %s
                %s
                %s
                """, name, description, links);
    }
}
