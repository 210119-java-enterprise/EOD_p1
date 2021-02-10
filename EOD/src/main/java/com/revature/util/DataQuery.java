package com.revature.util;

import java.util.List;

/**
 * This class represents one of the 5 sub-languages of SQL, which is DQL. The
 * query operation is just the select operation.
 */
public class DataQuery {

    /**
     * Empty constructor for the DataQuery object
     */
    public DataQuery(){

    }

    /**
     * Represents a basic select statement that will select everything from
     * a given table
     * @param clazz the class representing the table in the database
     * @return a list of all of the elements within the table
     */
    public List<Object> select(Class<?> clazz){
        return null;
    }

    //TODO implement select from where statements
}
