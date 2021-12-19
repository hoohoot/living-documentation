package org.hoohoot.livingdocumentation.processing.wordcount;

import org.hoohoot.livingdocumentation.processing.FileHelper;
import org.hoohoot.livingdocumentation.processing.LivingDocumentationProcessor;
import org.hoohoot.livingdocumentation.annotations.DomainLayer;
import org.hoohoot.livingdocumentation.processing.wordcount.model.WordCloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.StringTokenizer;

public class WordCloudProcessor implements LivingDocumentationProcessor<WordCloudWriter, WordCloud> {
    private static final Logger logger = LoggerFactory.getLogger(WordCloudProcessor.class);
    private final WordCloud wordCloud = new WordCloud();

    @Override
    public WordCloudWriter writer() {
        return new WordCloudWriter();
    }

    @Override
    public WordCloud output(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) throws IOException {

        Set<? extends Element> domainLayer = roundEnvironment.getElementsAnnotatedWith(DomainLayer.class);
        for (Element element : domainLayer) {
            PackageElement packageElement = (PackageElement) element;
            var qualifiedPackageName = packageElement.getQualifiedName();
            var path = FileHelper.resolvePathFromPackageName(qualifiedPackageName);
            this.populateWordCloud(path);
        }

        return wordCloud;
    }

    private static final Set<String> IGNORED = Set.of(
            // Stop Words
            "the", "it", "is", "to", "with", "what's", "by", "or", "and", "both", "be", "of",
            "in", "obj", "string", "hashcode", "equals", "other", "tostring", "false", "true", "object", "annotations",

            // Java Keywords
            "abstract", "continue", "for", "new", "switch", "assert", "default",
            "if", "package", "synchronized", "boolean", "do", "goto", "private", "this", "break", "double",
            "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum",
            "instanceof", "return", "transient", "catch", "extends", "int", "", "short", "try", "char", "final",
            "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float",
            "native", "super", "while", "record"
    );

    void populateWordCloud(Path sourceFolder) throws IOException {
        var path = Path.of("src/main/java", sourceFolder.toString());
        var sourceFile = path.toFile();

        File[] listOfFiles = sourceFile.listFiles();
        if (listOfFiles == null) {
            throw new IOException("File not found " + sourceFile.getAbsolutePath());
        } else {
            logger.info("Got {} files", Arrays.stream(listOfFiles).count());
        }

        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                logger.info("Scanning file {}", file.getName());
                populateWordCloud(file.toPath());
            }

            var isJavaSourceFile = file.isFile() && file.getName().endsWith(".java");

            if (isJavaSourceFile) {
                logger.info("Scanning java file {}", file.getName());
                String content = new String(Files.readAllBytes(file.toPath()));
                filter(content);
            }
        }
    }

    private void filter(final String content) {
        final StringTokenizer tokenizer = new StringTokenizer(content, ";:.,?!<>=+-^&|*/\"\t\r\n {}[]()");
        while (tokenizer.hasMoreElements()) {
            final String token = (String) tokenizer.nextElement();

            if (isMeaningful(token.trim().toLowerCase())) {
                this.wordCloud.add(token);
            }
        }
    }

    private static boolean isMeaningful(String token) {
        if (token.length() <= 1) {
            return false;
        }

        if (token.startsWith("@")) {
            return false;
        }

        if (Character.isDigit(token.charAt(0))) {
            return false;
        }

        return !IGNORED.contains(token);
    }
}
