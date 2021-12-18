package org.hoohoot.livingdocumentation.wordcount;

import org.hoohoot.livingdocumentation.FileHelper;
import org.hoohoot.livingdocumentation.annotations.DomainLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.io.IOException;
import java.util.Set;

public record WordCloudProcessor(WordCloudGenerator generator) {
    private static final Logger logger = LoggerFactory.getLogger(WordCloudProcessor.class);

    public void process(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) throws IOException {
        Set<? extends Element> domainLayer = roundEnvironment.getElementsAnnotatedWith(DomainLayer.class);
        for (Element element : domainLayer) {
            PackageElement packageElement = (PackageElement) element;
            var qualifiedPackageName = packageElement.getQualifiedName();
            var path = FileHelper.resolvePathFromPackageName(qualifiedPackageName);
            generator.scan(path);
            String bag = generator.toJSON();
            var template = FileHelper.readResource("wordcloud/template.html");

            WordCloudTemplate.write(processingEnvironment, template, bag);
        }
    }
}
