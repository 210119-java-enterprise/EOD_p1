package com.revature.statements;

import com.revature.annotations.Table;
import com.revature.util.Metamodel;

import java.util.ArrayList;
import java.util.Arrays;

public class Select {

    private String selectStatement;
    private final String select = "SELECT ";
    private final String from = "FROM ";

    /**
     * Creates a new select * statement that grabs all rows and columns
     * @param model the model whose rows and columns will be grabbed
     */
    public Select(Metamodel<?> model){
        selectStatement = "";
        selectStar(model);
    }

    /**
     * Creates a new select statement of the specified columns from certain table
     * @param model the table wanting to be selected
     * @param columnNames the columns wanted to be returned
     */
    public Select(Metamodel<?> model, String... columnNames){
        selectStatement = "";
        selectFrom(model, columnNames);
    }

    /**
     * Gets the select statement
     * @return the string representation of the select statement
     */
    public String getSelectStatement(){
        return selectStatement;
    }

    /**
     * Creates a select from statement with the specified column names
     * @param model the model representing the class of the object
     * @param columnNames the column names being grabbed in the select statement
     */
    private void selectFrom(Metamodel<?> model, String... columnNames){
        String tableName = model.getModelClass().getAnnotation(Table.class).tableName();
        ArrayList<String> listOfColumns = new ArrayList<>();
        for(String s : columnNames){
            listOfColumns.add(s);
        }
        statementBuilder(tableName, listOfColumns);
    }

    /**
     * Scrapes the metamodel for the column names
     * @param model the metamodel of the class type
     */
    private void selectStar(Metamodel<?> model){
        String tableName = model.getModelClass().getAnnotation(Table.class).tableName();
        statementBuilder(tableName);
    }

    /**
     * Creates a select * call with a specified table name
     * @param tableName the table to grab all rows and columns from
     */
    private void statementBuilder(String tableName){
        selectStatement = select + " * " + from + " " + tableName;
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the data inserted into
     * @param tableColumns the array of column names within the table
     */
    private void statementBuilder(String tableName, ArrayList<String> tableColumns){
        int bound = tableColumns.size();
        StringBuilder select = new StringBuilder("SELECT ");
        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                select.append(tableColumns.get(i)).append(" ");
            }else {
                select.append(tableColumns.get(i)).append(", ");
            }
        }
        selectStatement = select.toString() + from + " " + tableName;
    }

}
