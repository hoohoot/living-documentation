package org.hoohoot.livingdocumentation.processing.wordcount.model;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.hoohoot.livingdocumentation.processing.ToGeneratedOutput;

import java.util.stream.Collectors;

public class WordCloud implements ToGeneratedOutput {
    private final Multiset<String> bag;
    private int max;

    public WordCloud() {
        this.bag = HashMultiset.create();
        this.max = 0;
    }

    public void add(String token) {
        this.bag.add(token.trim().toLowerCase());
        int count = bag.count(token);
        this.max = Math.max(max, count);
    }

    @Override
    public String toGeneratedOutput() {
        final double scaling = 50. / max;
        return this.bag.entrySet().stream().map(entry ->
                        String.format("""
                                    {
                                        "text": "%s", 
                                        "size": %f 
                                    }""", entry.getElement(), scaling * entry.getCount()))
                .collect(Collectors.joining(",\n"));
    }
}
