package org.hoohoot.livingdocumentation;

import org.hoohoot.livingdocumentation.processing.FileHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ClassHelperTest {

    @Test
    void should_convert_package_name_to_path() {
        // Arrange
        final var qualifiedPackageName = new PackageNameFixture("org.hoohoot.example");

        //Act
        final var path = FileHelper.resolvePathFromPackageName(qualifiedPackageName);

        // Assert
        assertThat(path.toString()).isEqualTo("org/hoohoot/example");
    }
}