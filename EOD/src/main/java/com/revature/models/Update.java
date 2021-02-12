package com.revature.models;
//TODO turn for loops into functional programming syntax using streams

import com.revature.annotations.Table;
import com.revature.util.ColumnField;
import com.revature.util.Metamodel;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents the syntax for the sql update statement
 */
public class Update {

    private String updateStatement;

    /**
     * Constructor for creating an update statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param model the metamodel skeleton providing column names
     * @param object the object values being updated into the database
     */
    public Update(Metamodel<?> model, Object object){
        updateStatement = "";
        scrapeModelAndObject(model, object);
    }

    /**
     * Gets the string representation of the update statement
     * @return the string representation of the sql update statement
     */
    public String getUpdateStatement(){
        return updateStatement;
    }

    /**
     * Scrapes the metamodel for the column names
     * @param model the metamodel of the class type
     * @param object the object the same type as the metamodel
     */
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
     * @param tableName the name of the table in the database
     * @param tableColumns the names of the columns of the table in the database
     */
    private void statementBuilder(String tableName, ArrayList<String> tableColumns){
        int bound = tableColumns.size();
        StringBuilder set = new StringBuilder("SET ");
        StringBuilder where = new StringBuilder("WHERE ");

        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                set.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" ");
            }else {
                set.append(tableColumns.get(i)).append(" = ").append(" ? ").append(", ");
                where.append(tableColumns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }

        updateStatement = "UPDATE " + tableName + " " + set.toString() + where.toString();
    }
}
