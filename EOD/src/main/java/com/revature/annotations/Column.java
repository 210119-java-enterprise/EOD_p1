package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is placed upon the fields of a POJO to represent the columns within a
 * given table that the POJO represents. The column name will be set to an empty string as
 * a default value if the user implementing this library does not provide a column name
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * The annotation representing the column name for the field
     * @return the string name of the column, an empty string for default
     */
    String column() default "";
}
