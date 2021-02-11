package com.revature.dao;

import com.revature.models.Insert;
import com.revature.util.Metamodel;

/**
 * This class speaks to the database with a user specified DML statement
 * and will return with whether or not the DML statement was successfully
 * executed or not.
 */
public class DataManipulationDAO {

    public Object checkIfObjectIsInUse(Metamodel<?> model, Object object){
        return null;
    }

    public void insert(Metamodel<?> model, Object object){
        //TODO for prepared statements, you need to assign the values to ? in the statement string
        Insert insertStatement = new Insert(model, object);
    }
}
