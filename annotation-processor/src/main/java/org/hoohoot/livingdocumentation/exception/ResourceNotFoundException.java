package org.hoohoot.livingdocumentation.exception;

/*
 * This unreachable is there to ensure we never access a
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String resourceName) {
        super(String.format("Entered unreachable state : resource %s not found", resourceName));
    }
}
