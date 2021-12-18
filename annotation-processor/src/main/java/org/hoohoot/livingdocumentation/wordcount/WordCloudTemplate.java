package org.hoohoot.livingdocumentation.wordcount;

import org.hoohoot.livingdocumentation.FileHelper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;

public class WordCloudTemplate {

    private WordCloudTemplate() {
    }

    public static String evaluate(final String template, String title, String content) {
        return template.replace("{0}", title).replace("{1}", content);
    }

    public static void write(ProcessingEnvironment processingEnvironment, String content, String bag) throws IOException {
        final var wordCloud = content.replace("{0}", "WordCloud").replace("{1}", bag);
        var d3Content = FileHelper.readResource("wordcloud/d3.js");
        var d3LayoutContent = FileHelper.readResource("wordcloud/d3.layout.cloud.js");
        FileHelper.write(d3LayoutContent, "d3.layout.cloud.js");
        FileHelper.write(d3Content, "d3.js");
        FileHelper.write(wordCloud, "wordcloud.html");
    }
}