package com.revature.util;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Metamodel<T> {

    private Class<T> clazz;
    private PrimaryKeyField primaryKeyField;
    private List<ForeignKeyField> foreignKeyFields;
    private List<ColumnField> columnFields;

    /**
     * Creates a metamodel of class clazz, throws an exception if the class does not have
     * a table annotation
     * @param clazz the class of the new metamodel
     * @param <T> generic class type
     * @return the new metamodel created
     */
    public static <T> Metamodel<T> of(Class<T> clazz) {
        if (clazz.getAnnotation(Table.class) == null) {
            throw new IllegalStateException("Cannot create Metamodel object! Provided class, " + clazz.getName() + "is not annotated with @Entity");
        }
        return new Metamodel<T>(clazz);
    }

    /**
     * Constructor to create a new metamodel object, instantiates the column fields and
     * foreign key fields as empty linked lists
     * @param clazz
     */
    public Metamodel(Class<T> clazz) {
        this.clazz = clazz;
        this.columnFields = new LinkedList<>();
        this.foreignKeyFields = new LinkedList<>();
    }

    /**
     * Get the full class name for the metamodel
     * @return the string representation of the full class name
     */
    public String getClassName() {
        return clazz.getName();
    }

    /**
     * Get the simple class name for the metamodel, no package names
     * @return the string representation of the simple class name
     */
    public String getSimpleClassName() {
        return clazz.getSimpleName();
    }

    /**
     * Get the primary key of a class, throws a runtime exception if no primary key is present
     * @return the primary key of a class
     */
    public PrimaryKeyField getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                return new PrimaryKeyField(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }

    /**
     * Get the columns for the class
     * @return a linked list of column names for the class
     */
    public List<ColumnField> getColumns() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }

        return columnFields;
    }

    /**
     * Gets the list of foreign keys for the class
     * @return the list of foreign keys, empty array list if no foreign keys
     */
    public List<ForeignKeyField> getForeignKeys() {

        List<ForeignKeyField> foreignKeyFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ForeignKey column = field.getAnnotation(ForeignKey.class);
            if (column != null) {
                foreignKeyFields.add(new ForeignKeyField(field));
            }
        }

        return foreignKeyFields;

    }


}
