package com.revature.util;

import com.revature.dao.DataManipulationDAO;
import com.revature.services.DataManipulationService;
import com.revature.services.DataQueryService;
import com.revature.services.TransactionControlService;

import java.sql.Connection;

/**
 * This class represents a connection to the database that allows for the user
 * to send SQL statements to their database
 */
public class Session {

    private final EntityManager entityManager;

    private final DataManipulationService dml;
    private final DataQueryService dql;
    private final TransactionControlService tcl;

    /**
     * Creates a new session object
     * @param connection the connection to the database
     * @param entityManager the entity manager managing the current sessions
     */
    Session(Connection connection, EntityManager entityManager){
        this.entityManager = entityManager;
        final DataManipulationDAO dao = new DataManipulationDAO(connection);

        dml = new DataManipulationService(dao);
        dql = new DataQueryService();
        tcl = new TransactionControlService();
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
     * Checks to see if there is a metamodel representation of the object's class
     * @param object the object being checked
     * @return true if there is a metamodel, false if not
     */
    private Metamodel<?> isThereAMetamodel(Object object){
        for(Metamodel<?> m : entityManager.getMetamodels()){
            if(object.getClass().getName().equals(m.getClassName())){
                return m;
            }
        }
        return null;
    }
}
