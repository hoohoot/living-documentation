package org.hoohoot.livingdocumentation.processing.glossary;

import org.hoohoot.livingdocumentation.processing.LivingDocumentationProcessor;
import org.hoohoot.livingdocumentation.annotations.BoundedContext;
import org.hoohoot.livingdocumentation.annotations.DomainEntity;
import org.hoohoot.livingdocumentation.processing.glossary.model.BoundedContextEntry;
import org.hoohoot.livingdocumentation.processing.glossary.model.BoundedContexts;
import org.hoohoot.livingdocumentation.processing.glossary.model.DomainEntityEntry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GlossaryProcessor implements LivingDocumentationProcessor<GlossaryWriter, BoundedContexts> {

    @Override
    public BoundedContexts output(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) throws IOException {
        Set<? extends Element> boundedContextPackages = roundEnvironment.getElementsAnnotatedWith(BoundedContext.class);
        Set<? extends Element> domainEntities = roundEnvironment.getElementsAnnotatedWith(DomainEntity.class);
        return mapToBoundedContextEntries(boundedContextPackages, domainEntities);
    }

    @Override
    public GlossaryWriter writer() {
        return new GlossaryWriter();
    }

    private BoundedContexts mapToBoundedContextEntries(
            Set<? extends Element> boundedContextPackages,
            Set<? extends Element> domainEntities) {

        var boundedContexts = boundedContextPackages.stream()
                .map(PackageElement.class::cast)
                .map(boundedContextPackageElement -> {
                    List<DomainEntityEntry> domainEntityEntries = mapToDomainEntityEntries(domainEntities, boundedContextPackageElement);
                    var boundedContextData = boundedContextPackageElement.getAnnotation(BoundedContext.class);
                    var links = Arrays.asList(boundedContextData.links());
                    return new BoundedContextEntry(boundedContextData.name(), links, domainEntityEntries);
                }).toList();

        return new BoundedContexts(boundedContexts);
    }

    private List<DomainEntityEntry> mapToDomainEntityEntries(
            Set<? extends Element> domainEntities,
            PackageElement boundedContextPackageElement) {

        return domainEntities.stream()
                .filter(element -> {
                    var enclosingPackage = (PackageElement) element.getEnclosingElement();
                    var strEnclosingPackage = enclosingPackage.getQualifiedName().toString();
                    var strBoundedContextPackage = boundedContextPackageElement.getQualifiedName().toString();
                    return strEnclosingPackage.contains(strBoundedContextPackage);
                })
                .map(element -> {
                    var annotation = element.getAnnotation(DomainEntity.class);
                    var name = element.getSimpleName().toString();
                    var links = Arrays.asList(annotation.link());
                    return new DomainEntityEntry(name, annotation.description(), links);
                })
                .toList();
    }
}
