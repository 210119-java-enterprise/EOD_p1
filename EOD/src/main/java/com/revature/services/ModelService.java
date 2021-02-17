package com.revature.services;

import com.revature.dao.ModelDAO;
import com.revature.util.ColumnField;
import com.revature.util.Metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class acts as an intermediary for the DataManipulation, which will take in
 * user input. This class will make sure that the user input is following the
 * correct implementation of this library before sending the information to the DAO.
 */
public class ModelService {

    private ModelDAO dmlDao;

    /**
     * Creates a new model service with a model dao
     * @param dao the dao for the model service
     */
    public ModelService(ModelDAO dao){
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
//        if(dmlDao.checkIfObjectIsInUse(model, object) != null){
//            throw new RuntimeException("Object already in database");
//        }
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
     * Basic implementation of the select sql statement
     * @param model the metamodel of the class type
     * @param object the instantiation of the class
     */
    public List<?> select(Metamodel<?> model, Object object){
        if(!isValidObject(object)){
            throw new RuntimeException("Invalid user, user is null");
        }
        return dmlDao.select(model, object);
    }

    /**
     * Checks to make sure the column names passed are within the metamodel
     * @param object the object whose class the rows will come from
     * @param columnNames the names of the columns the data comes from
     */
    public List<?> selectFrom(Metamodel<?> model, Object object, String... columnNames){
        if(!isValidObject(object)){
            throw new RuntimeException("Invalid user, user is null");
        }
        if(!doColumnNamesExist(model, columnNames)){
            throw new RuntimeException("Column names passed are not in the database");
        }
        return dmlDao.select(model, object, columnNames);
    }

    /**
     * Checks to see if the column names passed by the user are within the column
     * names of the metamodel that the object class represents
     * @param model the metamodel of the object class passed by the user
     * @param columnNames the column names passed by the user
     * @return true if the column names are present, false if not
     */
    private boolean doColumnNamesExist(Metamodel<?> model, String... columnNames){
        ArrayList<String> columnsInModel = (ArrayList<String>) model.getColumns()
                                                                    .stream()
                                                                    .map(ColumnField::getColumnName)
                                                                    .collect(Collectors.toList());
        for (String s : columnNames){
            if(!columnsInModel.contains(s)){
                return false;
            }
        }
        return true;
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
