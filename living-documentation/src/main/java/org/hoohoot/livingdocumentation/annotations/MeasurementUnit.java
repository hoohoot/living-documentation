/**
 * 
 */
package org.hoohoot.livingdocumentation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a measurement unit.
 */
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Documented
@ValueObject
public @interface MeasurementUnit {

	String brief() default "Represents a Measurement Unit";

	String[] link() default {};
}
