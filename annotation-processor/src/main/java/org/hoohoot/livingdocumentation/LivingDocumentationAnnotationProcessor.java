package org.hoohoot.livingdocumentation;

import com.google.auto.service.AutoService;
import org.hoohoot.livingdocumentation.exception.ResourceNotFoundException;
import org.hoohoot.livingdocumentation.processing.FileHelper;
import org.hoohoot.livingdocumentation.processing.glossary.GlossaryProcessor;
import org.hoohoot.livingdocumentation.processing.wordcount.WordCloudProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("org.hoohoot.livingdocumentation.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class LivingDocumentationAnnotationProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LivingDocumentationAnnotationProcessor.class);

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        FileHelper.createTargetDirectory();
        try {
            new WordCloudProcessor().process(roundEnvironment, processingEnv);
            new GlossaryProcessor().process(roundEnvironment, processingEnv);
        } catch (IOException | ResourceNotFoundException e) {
            logger.error("Annotation processing failed : {}", e.getMessage());
        }

        return true;
    }
}
