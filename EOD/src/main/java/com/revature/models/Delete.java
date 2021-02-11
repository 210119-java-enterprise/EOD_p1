package com.revature.models;
//TODO turn for loops into functional programming syntax using streams
/**
 * This class represents the syntax for the sql delete statement
 */
public class Delete {

    private String deleteStatement;

    /**
     * Constructor for creating a delete statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param tableName the name of the table having the entry deleted
     * @param tableColumns the array of columns within the table
     */
    public Delete(String tableName, String[] tableColumns){
        deleteStatement = "";
        statementBuilder(tableName, tableColumns);
    }

    /**
     * Gets the string representation of the delete statement
     * @return the string representation of the sql delete statement
     */
    public String getDeleteStatement() {
        return deleteStatement;
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the data inserted into
     * @param tableColumns the array of column names within the table
     */
    private void statementBuilder(String tableName, String[] tableColumns){
        int bound = tableColumns.length;
        StringBuilder where = new StringBuilder("WHERE ");
        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                where.append(tableColumns[i]).append(" = ").append(" ? ").append(" ");
            }else {
                where.append(tableColumns[i]).append(" = ").append(" ? ").append(" and ");
            }
        }
        deleteStatement = "DELETE FROM " + tableName + " " + where.toString();
    }
}
