package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is placed upon the field(s) of a POJO to represent the foreign key(s) within a
 * given table that the POJO represents. Not implemented
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    /**
     * The annotation representing the column name of the foreign key in the database
     * @return the string name of the table
     */
    String columnName();
}
