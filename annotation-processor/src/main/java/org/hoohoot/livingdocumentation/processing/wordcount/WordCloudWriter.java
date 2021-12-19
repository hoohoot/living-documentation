package org.hoohoot.livingdocumentation.processing.wordcount;

import org.hoohoot.livingdocumentation.exception.ResourceNotFoundException;
import org.hoohoot.livingdocumentation.processing.FileHelper;
import org.hoohoot.livingdocumentation.processing.Writer;
import org.hoohoot.livingdocumentation.processing.wordcount.model.WordCloud;

import java.io.IOException;

public record WordCloudWriter() implements Writer<WordCloud> {

    @Override
    public void write(WordCloud wordCloud) throws IOException, ResourceNotFoundException {
        var template = FileHelper.readResource("wordcloud/template.html");
        var renderedTemplate = template
                .replace("{0}", "WordCloud")
                .replace("{1}", wordCloud.toGeneratedOutput());
        var d3Content = FileHelper.readResource("wordcloud/d3.js");
        var d3LayoutContent = FileHelper.readResource("wordcloud/d3.layout.cloud.js");

        FileHelper.write(d3LayoutContent, "d3.layout.cloud.js");
        FileHelper.write(d3Content, "d3.js");
        FileHelper.write(renderedTemplate, "wordcloud.html");
    }

    @Override
    public String outputFile() {
        return "wordcloud.html";
    }
}
