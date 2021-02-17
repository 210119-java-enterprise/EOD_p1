package com.revature.statements;

import com.revature.annotations.*;
import com.revature.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class represents the syntax for the sql insert statement
 */
public class Insert {

    private String insertStatement;

    /**
     * Constructor for creating an insert statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param model the metamodel skeleton providing column names
     * @param object the object being inserted into the database
     */
    public Insert(Metamodel<?> model, Object object){
        insertStatement = "";
        scrapeModel(model, object);
    }

    /**
     * Get the string representation of the insert sql statement
     * @return the string representation of the insert sql statement
     */
    public String getInsertStatement() {
        return insertStatement;
    }

    /**
     * Scrapes the metamodel for the column names
     * @param model the metamodel of the class type
     */
    private void scrapeModel(Metamodel<?> model, Object object){
        String tableName = model.getModelClass().getAnnotation(Table.class).tableName();
        ArrayList<String> tableColumns = new ArrayList<>();
        for(Field f : object.getClass().getDeclaredFields()){
            Serial tag = f.getAnnotation(Serial.class);
            if(tag == null){
                Column column = f.getAnnotation(Column.class);
                PrimaryKey primary = f.getAnnotation(PrimaryKey.class);
                ForeignKey foreign = f.getAnnotation(ForeignKey.class);
                if (column != null) {
                    tableColumns.add(column.columnName());
                }else if(primary != null){
                    tableColumns.add(primary.columnName());
                }else if(foreign != null){
                    tableColumns.add(foreign.columnName());
                }
            }
        }
        statementBuilder(tableName, tableColumns);
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the data inserted into
     * @param tableColumns the arraylist of column names within the table
     */
    private void statementBuilder(String tableName, ArrayList<String> tableColumns) {
        int bound = tableColumns.size();
        StringBuilder insertInto = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        for(int i = 0; i < bound; i++){
            if(i == (bound-1)) {
                insertInto.append(tableColumns.get(i)).append(") ");
                values.append(" ? ").append(") ");
            }else {
                insertInto.append(tableColumns.get(i)).append(", ");
                values.append(" ? ").append(", ");
            }
        }
        insertStatement = insertInto.toString() + values.toString();
    }
}
