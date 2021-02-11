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

    public void insert(Metamodel<?> model, Object object){
        if(!isValidObject(object)){
            throw new RuntimeException("Invalid user, user is null");
        }
        if(dmlDao.checkIfObjectIsInUse(model, object) != null){
            throw new RuntimeException("Object already in database");
        }
        dmlDao.insert(model, object);
    }

    private boolean isValidObject(Object object){
        return (object != null);
    }
}
