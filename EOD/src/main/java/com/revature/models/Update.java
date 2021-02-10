package com.revature.models;
//TODO turn for loops into functional programming syntax using streams
/**
 * This class represents the syntax for the sql update statement
 */
public class Update {

    private String updateStatement;

    /**
     * Constructor for creating an update statement in SQL, takes in the information
     * needed in order to create the full statement. The index of the table columns array
     * needs to be the same index to the given value within the object values array.
     * @param tableName the name of the table having the entry deleted
     * @param tableColumns the array of columns within the table
     * @param oldObjectValues the old values of the object currently in the database
     * @param newObjectValues the new values to update the object in the database
     */
    public Update(String tableName, String[] tableColumns,
                  String[] oldObjectValues, String[] newObjectValues){
        updateStatement = "";
        stringBuilder(tableName, tableColumns, oldObjectValues, newObjectValues);
    }

    /**
     * Gets the string representation of the update statement
     * @return the string representation of the sql update statement
     */
    public String getUpdateStatement(){
        return updateStatement;
    }

    /**
     * Builds the SQL statement, sits in a private method to lessen the length of
     * the constructor
     * @param tableName the name of the table having the entry deleted
     * @param tableColumns the array of columns within the table
     * @param oldObjectValues the old values of the object currently in the database
     * @param newObjectValues the new values to update the object in the database
     */
    private void stringBuilder(String tableName, String[] tableColumns,
                               String[] oldObjectValues, String[] newObjectValues){
        int bound = tableColumns.length;
        StringBuilder set = new StringBuilder("SET ");
        StringBuilder where = new StringBuilder("WHERE ");

        for(int i = 0; i < bound; i++){
            if(i == (bound-1)){
                set.append(tableColumns[i]).append(" = ").append(newObjectValues[i]).append(" ");
                where.append(tableColumns[i]).append(" = ").append(oldObjectValues[i]).append(" ");
            }else {
                set.append(tableColumns[i]).append(" = ").append(newObjectValues[i]).append(", ");
                where.append(tableColumns[i]).append(" = ").append(oldObjectValues[i]).append(" and ");
            }
        }

        updateStatement = "UPDATE " + tableName + " " + set.toString() + where.toString();
    }
}
