package com.revature.util;

import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

/**
 * This class represents the field in the class that corresponds to the
 * primary key of the database table
 */
public class PrimaryKeyField {

    private Field field;

    /**
     * Create a new primary key field object
     * @param field the field in the class that is the primary key
     */
    public PrimaryKeyField(Field field) {
        if (field.getAnnotation(PrimaryKey.class) == null) {
            throw new IllegalStateException("Cannot create PrimaryKeyField object! Provided field, " + getName() + "is not annotated with @PrimaryKey");
        }
        this.field = field;
    }

    /**
     * Gets the name of the field that is the primary key
     * @return the string representation of the field
     */
    public String getName() {
        return field.getName();
    }

    /**
     * Gets the class type of the field that is the primary key
     * @return the class of the field
     */
    public Class<?> getType() {
        return field.getType();
    }

    /**
     * Gets the name of the column in the database that this field
     * represents
     * @return the string representation of the column name
     */
    public String getColumnName() {
        return field.getAnnotation(PrimaryKey.class).columnName();
    }
}
