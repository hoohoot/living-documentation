package org.hoohoot.livingdocumentation.processing;

import org.hoohoot.livingdocumentation.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Name;
import java.io.*;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    private FileHelper() {
    }

    /** The package separator character: {@code '.'}. */
    private static final char PACKAGE_SEPARATOR = '.';

    /** The path separator character: {@code '/'}. */
    private static final char PATH_SEPARATOR = '/';

    /** The generated source target path: {@code './target/living-documentation'}. */
    private static final String LIVING_DOCUMENTATION_TARGET = "target/living-documentation";

    public static void createTargetDirectory() {
        var file = new File(LIVING_DOCUMENTATION_TARGET);
        final var mkdir = file.mkdir();
        if (mkdir) {
            logger.info("Living documentation target directory created under ./target/living-documentation");
        } else {
            logger.info("Could not create target directory ./target/living-documentation");
        }
    }

    public static void  write(String content, String path) throws IOException {
        final var targetFile = Path.of(LIVING_DOCUMENTATION_TARGET, path);
        final var newFile = targetFile.toFile().createNewFile();
        if (newFile) {
            try (FileWriter writer = new FileWriter(targetFile.toFile())) {
                writer.write(content);
            }
        } else {
            throw new IOException("Failed to create new file");
        }

    }
    public static Path resolvePathFromPackageName(Name qualifiedPackageName) {
        var name = qualifiedPackageName.toString();
        var path = name.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
        return Path.of(path);
    }

    public static String readResource(String resourcePath) throws ResourceNotFoundException, IOException {
        String lineSep = String.format("%n");
        final var systemResource = FileHelper.class.getClassLoader().getResourceAsStream(resourcePath);
        if (systemResource != null) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(systemResource))) {
                return in.lines()
                        .collect(Collectors.joining(lineSep));
            }
        } else {
            throw new ResourceNotFoundException(resourcePath);
        }
    }
}
