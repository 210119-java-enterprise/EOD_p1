package com.revature.models;
//TODO turn for loops into functional programming syntax using streams
/**
 * This class represents the syntax for the sql insert statement
 */
public class Insert {

    private String insertStatement;

    /**
     * Constructor for creating an insert statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param tableName the name of the table being inserted into
     * @param tableColumns the array of columns within the table
     * @param objectValues the array of values that relate to each column
     */
    public Insert(String tableName, String[] tableColumns, String[] objectValues){
        insertStatement = "";
        statementBuilder(tableName,tableColumns,objectValues);
    }

    /**
     * Get the string representation of the insert sql statement
     * @return the string representation of the insert sql statement
     */
    public String getInsertStatement() {
        return insertStatement;
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the data inserted into
     * @param tableColumns the array of column names within the table
     * @param objectValues the array of values corresponding to the columns
     */
    private void statementBuilder(String tableName, String[] tableColumns, String[] objectValues) {
        int bound = tableColumns.length;
        StringBuilder insertInto = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        for(int i = 0; i < bound; i++){
            if(i == (bound-1)) {
                insertInto.append(tableColumns[i]).append(") ");
                values.append(objectValues[i]).append(") ");
            }else {
                insertInto.append(tableColumns[i]).append(", ");
                values.append(objectValues[i]).append(", ");
            }
        }
        insertStatement = insertInto.toString() + values.toString();
    }
}
