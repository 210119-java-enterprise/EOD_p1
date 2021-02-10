package com.revature.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is placed upon classes of a POJO to represent the name of the table that
 * the POJO represents within the database.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * The annotation representing the table name of the POJO in the database
     * @return the string name of the table
     */
    String tableName();
}
