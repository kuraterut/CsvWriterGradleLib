package org.kuraterut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation which marking object fields to make it csv writable.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvColumn {
    /**
     *
     * @return      column name in the CSV file.
     */
    String name() default "";

    /**
     *
     * @return      order in the CSV file.
     */
    int order() default 0;
}
