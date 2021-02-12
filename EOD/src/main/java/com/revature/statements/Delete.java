package com.revature.statements;
//TODO turn for loops into functional programming syntax using streams

import com.revature.annotations.Table;
import com.revature.util.ColumnField;
import com.revature.util.Metamodel;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the syntax for the sql delete statement
 */
public class Delete {

    private String deleteStatement;

    /**
     * Constructor for creating a delete statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param model the metamodel skeleton providing column names
     */
    public Delete(Metamodel<?> model){
        deleteStatement = "";
        scrapeModel(model);
    }

    /**
     * Gets the string representation of the delete statement
     * @return the string representation of the sql delete statement
     */
    public String getDeleteStatement() {
        return deleteStatement;
    }

    /**
     * Scrapes the metamodel for the column names
     * @param model the metamodel of the class type
     */
    private void scrapeModel(Metamodel<?> model){
        String tableName = model.getModelClass().getAnnotation(Table.class).tableName();
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
     * @param tableColumns the array of column names within the table
     */
    private void statementBuilder(String tableName, ArrayList<String> tableColumns){
        int bound = tableColumns.size();
        StringBuilder where = new StringBuilder("WHERE ");
        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
            }else {
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }
        deleteStatement = "DELETE FROM " + tableName + " " + where.toString();
    }
}
