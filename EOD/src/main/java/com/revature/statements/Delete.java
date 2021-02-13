package com.revature.statements;
//TODO turn for loops into functional programming syntax using streams

import com.revature.annotations.*;
import com.revature.util.ColumnField;
import com.revature.util.Metamodel;

import java.lang.reflect.Field;
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
    public Delete(Metamodel<?> model, Object object){
        deleteStatement = "";
        scrapeModel(model, object);
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
