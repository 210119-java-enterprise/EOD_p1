package com.revature.util;

import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class PrimaryKeyField {

    private Field field;

    public PrimaryKeyField(Field field) {
        if (field.getAnnotation(PrimaryKey.class) == null) {
            throw new IllegalStateException("Cannot create PrimaryKeyField object! Provided field, " + getName() + "is not annotated with @PrimaryKey");
        }
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getColumnName() {
        return field.getAnnotation(PrimaryKey.class).columnName();
    }
}
