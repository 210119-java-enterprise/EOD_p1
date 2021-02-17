package com.revature.util;

import com.revature.annotations.ForeignKey;

import java.lang.reflect.Field;

/**
 * This class represents the field within a class that is the foreign
 * key within a database. Not implemented
 */
public class ForeignKeyField {

    private Field field;

    /**
     * Creates a new forien key field object
     * @param field
     */
    public ForeignKeyField(Field field) {
        if (field.getAnnotation(ForeignKey.class) == null) {
            throw new IllegalStateException("Cannot create ForeignKeyField object! Provided field, " + getName() + "is not annotated with @ForeignKey");
        }
        this.field = field;
    }

    /**
     * Gets the name of the field that represents the foreign key within
     * the database
     * @return the string representation of the field name
     */
    public String getName() {
        return field.getName();
    }

    /**
     * Gets the class type of the field
     * @return the class of the field
     */
    public Class<?> getType() {
        return field.getType();
    }

    /**
     * Gets the column name in the database of the field
     * @return the string representation of the column name
     */
    public String getColumnName() {
        return field.getAnnotation(ForeignKey.class).columnName();
    }
}
