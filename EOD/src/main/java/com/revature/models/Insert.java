package com.revature.models;
//TODO turn for loops into functional programming syntax using streams

import com.revature.annotations.Table;
import com.revature.util.ColumnField;
import com.revature.util.Metamodel;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @param object the object values being inserted into the database
     */
    public Insert(Metamodel<?> model, Object object){
        insertStatement = "";
        scrapeModelAndObject(model, object);
    }

    /**
     * Get the string representation of the insert sql statement
     * @return the string representation of the insert sql statement
     */
    public String getInsertStatement() {
        return insertStatement;
    }

    private void scrapeModelAndObject(Metamodel<?> model, Object object){
        String tableName = object.getClass().getAnnotation(Table.class).tableName();
        Function<ColumnField, String> func = ColumnField::getColumnName;
        ArrayList<String> tableColumns = (ArrayList<String>) model.getColumns()
                                                                    .stream()
                                                                    .map(func)
                                                                    .collect(Collectors.toList());
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
