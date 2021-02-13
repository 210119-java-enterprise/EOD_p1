package com.revature.util;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;

    public ColumnField(Field field) {
        if (field.getAnnotation(Column.class) == null && field.getAnnotation(PrimaryKey.class) == null
                && field.getAnnotation(ForeignKey.class) == null) {
            throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
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
        if(field.getAnnotation(Column.class) != null){
            return field.getAnnotation(Column.class).columnName();
        }else if(field.getAnnotation(PrimaryKey.class) != null){
            return field.getAnnotation(PrimaryKey.class).columnName();
        }else{
            return field.getAnnotation(ForeignKey.class).columnName();
        }
    }
}
