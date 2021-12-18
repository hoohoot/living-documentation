package org.hoohoot.livingdocumentation.wordcount;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class WordCloudGenerator {
    private static final Logger logger = LoggerFactory.getLogger(WordCloudGenerator.class);
    private final Multiset<String> bag = HashMultiset.create();
    private int max = 0;

    static final Set<String> IGNORED = Set.of(
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

    void scan(Path sourceFolder) throws IOException {
        final var path = Path.of("src/main/java", sourceFolder.toString());
        final var sourceFile = path.toFile();

        final File[] listOfFiles = sourceFile.listFiles();
        if (listOfFiles == null) {
            throw new IOException("File not found " + sourceFile.getAbsolutePath());
        } else {
            logger.info("Got {} files", Arrays.stream(listOfFiles).count());
        }

        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                logger.info("Scanning file {}", file.getName());
                scan(file.toPath());
            }

            final var isJavaSourceFile = file.isFile() && file.getName().endsWith(".java");

            if (isJavaSourceFile) {
                logger.info("Scanning java file {}", file.getName());
                final String content = new String(Files.readAllBytes(file.toPath()));
                filter(content);
            }
        }
    }

    private void filter(final String content) {
        final StringTokenizer tokenizer = new StringTokenizer(content, ";:.,?!<>=+-^&|*/\"\t\r\n {}[]()");
        while (tokenizer.hasMoreElements()) {
            final String token = (String) tokenizer.nextElement();

            if (isMeaningful(token.trim().toLowerCase())) {
                bag.add(token.trim().toLowerCase());
                final int count = bag.count(token);
                max = Math.max(max, count);
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

    public String toJSON() {
        final StringBuilder sb = new StringBuilder();
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
