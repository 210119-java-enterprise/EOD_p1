package com.revature.util;

import com.revature.annotations.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the meta model of object classes passed by the
 * user that is using this library
 * @param <T> the generic object class type
 */
public class Metamodel<T> {

    private Class<T> clazz;

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
     * @param clazz the class type of the metamodel
     */
    private Metamodel(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Gets the class type of the object of the metamodel instance
     * @return the class type of the object
     */
    public Class<T> getModelClass(){
        return clazz;
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
        throw new RuntimeException("Did not find a field annotated with @PrimaryKey in: " + clazz.getName());
    }

    /**
     * Get the columns for the class
     * @return a linked list of column names for the class
     */
    public List<ColumnField> getColumns() {

        List<ColumnField> columnFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            ForeignKey foreign = field.getAnnotation(ForeignKey.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }else if(primary != null){
                columnFields.add(new ColumnField(field));
            }else if(foreign != null){
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }

        return columnFields;
    }

    /**
     * Gets the string representation of the column names
     * @return a list of the string representations of the column names
     */
    public List<String> getColumnNames(){
        List<String> columnNames = new LinkedList<>();
        for(ColumnField c : this.getColumns()){
            columnNames.add(c.getColumnName());
        }
        return columnNames;
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

    /**
     * Finds the class type of a given column
     * @param columnName the name of the column
     * @return the class type of column, null if not found
     */
    public Class<?> findClassOfColumn(String columnName){
        for(ColumnField c : this.getColumns()){
            if(c.getColumnName().equals(columnName)){
                return c.getType();
            }
        }
        if(getPrimaryKey().getColumnName().equals(columnName)){
            return getPrimaryKey().getType();
        }
        for(ForeignKeyField f : getForeignKeys()){
            if(f.getColumnName().equals(columnName)){
                return f.getType();
            }
        }
        return null;
    }

    /**
     * Finds the field name of a given column
     * @param columnName the name of the column
     * @return the field name of the column, null if not found
     */
    public String findFieldNameOfColumn(String columnName){
        for(ColumnField c : this.getColumns()){
            if(c.getColumnName().equals(columnName)){
                return c.getName();
            }
        }
        if(getPrimaryKey().getColumnName().equals(columnName)){
            return getPrimaryKey().getName();
        }
        for(ForeignKeyField f : getForeignKeys()){
            if(f.getColumnName().equals(columnName)){
                return f.getName();
            }
        }
        return null;
    }

    /**
     * Checks to see if the object passed is equivalent to this current instance
     * @param o the object being compared
     * @return true if they are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metamodel<?> metamodel = (Metamodel<?>) o;
        return Objects.equals(clazz, metamodel.clazz);
    }

    /**
     * Will hash the current object
     * @return the hashcode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }
}
