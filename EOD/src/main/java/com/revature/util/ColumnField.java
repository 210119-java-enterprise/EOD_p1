package com.revature.util;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

/**
 * This class represents the field of a class with the column annotation
 */
public class ColumnField {

    private Field field;

    /**
     * Creates a new column field object
     * @param field the field from a class
     */
    public ColumnField(Field field) {
        if (field.getAnnotation(Column.class) == null && field.getAnnotation(PrimaryKey.class) == null
                && field.getAnnotation(ForeignKey.class) == null) {
            throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
        }
        this.field = field;
    }

    /**
     * Gets the name of a field
     * @return the string representation of the name of a field
     */
    public String getName() {
        return field.getName();
    }

    /**
     * Gets the type of a field
     * @return the class type of a field
     */
    public Class<?> getType() {
        return field.getType();
    }

    /**
     * Gets the name of the column that represents the name of the column
     * within the database
     * @return the name of the column in the database
     */
    public String getColumnName() {
        if(field.getAnnotation(Column.class) != null){
            return field.getAnnotation(Column.class).columnName();
        }else if(field.getAnnotation(PrimaryKey.class) != null){
            return field.getAnnotation(PrimaryKey.class).columnName();
        }else{
            return field.getAnnotation(ForeignKey.class).columnName();
        }
    }
}
