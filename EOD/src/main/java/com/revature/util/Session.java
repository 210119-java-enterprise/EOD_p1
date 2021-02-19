package com.revature.util;

import com.revature.dao.ModelDAO;
import com.revature.services.ModelService;

import java.util.List;

/**
 * This class represents a connection to the database that allows for the user
 * to send SQL statements to their database
 */
public class Session {

    private List<Metamodel<Class<?>>> metamodelList;

    private ModelService dml;

    /**
     * Creates a new session object
     * @param metamodelList the list of metamodels the entity manager has
     */
    Session(List<Metamodel<Class<?>>> metamodelList){
        this.metamodelList = metamodelList;
        ModelDAO dao = new ModelDAO();

        dml = new ModelService(dao);
    }

    /**
     * Will try and persist the object passed to the database
     * @param object the object being persisted to the database
     */
    public void save(Object object){
        Metamodel<?> model = isThereAMetamodel(object);
        if(model == null){
            throw new RuntimeException("No metamodel was found of class " + object.getClass().getName());
        }
        dml.insert(model, object);
    }

    /**
     * Will try and update an object already persisted in the database with a new
     * object
     * @param newObject the object with the updated information
     * @param oldObject the object with the old information
     */
    public void update(Object newObject, Object oldObject){
        if(!newObject.getClass().getName().equals(oldObject.getClass().getName())){
            throw new RuntimeException("Classes of the passed objects are not equal");
        }
        Metamodel<?> model = isThereAMetamodel(oldObject);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        dml.update(model, newObject, oldObject);
    }

    /**
     * Will try and delete the object passed from the database
     * @param object the object being deleted from the database
     */
    public void delete(Object object){
        Metamodel<?> model = isThereAMetamodel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        dml.delete(model, object);
    }

    /**
     * Selects all rows from all columns from a specified object class
     * @param object the object of a class the user wants all info from
     */
    public List<?> selectAll(Object object){
        Metamodel<?> model = isThereAMetamodel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        return dml.select(model, object);
    }

    /**
     * Selects all rows from certain columns of a specified object class
     * @param object the object whose class the rows will come from
     * @param columnNames the names of the columns the data comes from
     */
    public List<?> selectFrom(Object object, String... columnNames){
        Metamodel<?> model = isThereAMetamodel(object);
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        return dml.selectFrom(model, object, columnNames);
    }

    /**
     * Checks to see if there is a metamodel representation of the object's class
     * @param object the object being checked
     * @return true if there is a metamodel, false if not
     */
    private Metamodel<?> isThereAMetamodel(Object object){
        for(Metamodel<?> m : metamodelList){
            if(object.getClass().getName().equals(m.getClassName())){
                return m;
            }
        }
        return null;
    }
}
