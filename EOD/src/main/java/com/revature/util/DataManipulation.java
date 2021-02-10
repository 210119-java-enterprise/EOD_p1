package com.revature.util;

/**
 * This class represents one of the 5 sub-languages of SQL, which is DML. The
 * CRUD operations are implemented, except for querying data with select.
 */
public class DataManipulation {

    /**
     * Empty constructor for the DataManipulation object
     */
    public DataManipulation(){

    }

    /**
     * Represents the insert command within SQL, this will save a new element
     * to a table.
     * @param object the object to be saved to the database
     * @return true if success, false if not
     */
    public boolean insert(Object object){
        return false;
    }

    /**
     * Represents the delete command within SQL, this will delete the
     * element who represents the passed object. Not a cascade delete
     * @param object the object to be deleted from the table
     * @return true if success, false if not
     */
    public boolean delete(Object object){
        return false;
    }

    /**
     * Represents the update command within SQL, this will update an
     * element's row
     * @param oldObject the updated object to replace old entry
     * @param newObject the old object in the table
     * @return true if success, false if not
     */
    public boolean update(Object oldObject, Object newObject){
        return false;
    }
}
