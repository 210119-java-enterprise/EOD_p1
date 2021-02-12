package com.revature.services;

import com.revature.annotations.Table;
import com.revature.dao.DataManipulationDAO;
import com.revature.util.Metamodel;

/**
 * This class acts as an intermediary for the DataManipulation, which will take in
 * user input. This class will make sure that the user input is following the
 * correct implementation of this library before sending the information to the DAO.
 */
public class DataManipulationService {

    private DataManipulationDAO dmlDao;

    public DataManipulationService(DataManipulationDAO dao){
        dmlDao = dao;
    }

    /**
     * Basic implementation of the insert sql statement
     * @param model the metamodel of the class type
     * @param object the instantiation of the class
     */
    public void insert(Metamodel<?> model, Object object){
        if(!isValidObject(object)){
            throw new RuntimeException("Invalid user, user is null");
        }
        if(dmlDao.checkIfObjectIsInUse(model, object) != null){
            throw new RuntimeException("Object already in database");
        }
        dmlDao.insert(model, object);
    }

    /**
     * Basic implementation of the delete sql statement
     * @param model the metamodel of the class type
     * @param object the instantiation of the class
     */
    public void delete(Metamodel<?> model, Object object){
        if(!isValidObject(object)){
            throw new RuntimeException("Invalid user, user is null");
        }
//        if(dmlDao.checkIfObjectIsInUse(model, object) == null){
//            throw new RuntimeException("Object is not in database");
//        }
        dmlDao.delete(model, object);
    }

    /**
     * Basic implementation of the update sql statement
     * @param model the metamodel of the class type
     * @param newObject the instantiation of the new object that will update the old entry
     * @param oldObject the instantiation of the old object already in the DB
     */
    public void update(Metamodel<?> model, Object newObject, Object oldObject){
        if(!isValidObject(newObject) | !isValidObject(oldObject)){
            throw new RuntimeException("Invalid object passed");
        }
//        if(dmlDao.checkIfObjectIsInUse(model, oldObject) == null){
//            throw new RuntimeException("Object is not in database, try inserting before updating");
//        }
        dmlDao.update(model, newObject, oldObject);
    }

    /**
     * Checks to see if the object passed is non null
     * @param object the object being persisted to the DB
     * @return true if valid, false if not
     */
    private boolean isValidObject(Object object){
        return (object != null);
    }
}
