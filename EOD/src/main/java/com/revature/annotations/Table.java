package com.revature.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is placed upon classes of a POJO to represent the name of the table that
 * the POJO represents within the database. The default value for the annotation is an empty
 * string if the user does not provide the table name that the POJO is in the database.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * The annotation representing the table name of the POJO in the database
     * @return the string name of the table, or a default empty string
     */
    String table() default "";
}
