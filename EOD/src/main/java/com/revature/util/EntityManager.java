package com.revature.util;

import com.revature.dao.DataManipulationDAO;
import com.revature.services.DataManipulationService;
import com.revature.services.DataQueryService;
import com.revature.services.TransactionControlService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class responsible for holding all of the metamodels and
 * the connection factory that opens connections to the database
 */
public class EntityManager {

    private ConnectionFactory connectionFactory;
    private List<Metamodel<Class<?>>> metamodelList;

    private DataManipulationService dml;
    private DataQueryService dql;
    private TransactionControlService tcl;

    /**
     * Creates a new entity manager with the path name to the properties file that
     * holds the database information
     * @param pathName the string representation of the path name to the properties file
     */
    public EntityManager(String pathName){
        connectionFactory = new ConnectionFactory(pathName);
        metamodelList = new LinkedList<>();

        final DataManipulationDAO dmlDao = new DataManipulationDAO();

        dml = new DataManipulationService(dmlDao);
        dql = new DataQueryService();
        tcl = new TransactionControlService();
    }

    /**
     * Adds an annotated class to the list of annotated classes within the
     * entity manager
     * @param clazz the class that is annotated
     * @return the instance of entity manager
     */
    @SuppressWarnings({"unchecked"})
    public EntityManager addAnnotatedClass(Class clazz){
        metamodelList.add(Metamodel.of(clazz));
        return this;
    }

    /**
     * Gets all of the metamodel class representations within a list
     * @return the list of metamodels within the entity manager
     */
    public List<Metamodel<Class<?>>> getMetamodels() {
        return (metamodelList == null) ? Collections.emptyList() : metamodelList;
    }

    public void save(Object object){
        Metamodel<?> model = null;
        for(Metamodel<?> m : metamodelList){
            if(m.getClassName().equals(object.getClass().getName())){
                model = m;
                break;
            }
        }
        if(model == null){
            throw new RuntimeException("Could not find class name for object within metamodel list!");
        }
        dml.insert(model, object);
    }
}
