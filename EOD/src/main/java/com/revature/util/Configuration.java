package com.revature.util;

import java.util.LinkedList;
import java.util.List;

/**
 * This class configures the connection to the database as well as creates new
 * entity managers for the user
 */
public class Configuration {

    private ConnectionFactory connectionFactory;
    private List<Metamodel<Class<?>>> metamodelList;

    /**
     * Creates a new configuration with the path name to the properties file that
     * holds the database information
     * @param pathName the string representation of the path name to the properties file
     */
    public Configuration(String pathName){
        connectionFactory = new ConnectionFactory(pathName);
        metamodelList = new LinkedList<>();
    }

    /**
     * Adds an annotated class to the list of annotated classes within the
     * configuration
     * @param clazz the class that is annotated
     * @return the instance of entity manager
     */
    @SuppressWarnings({"unchecked"})
    public Configuration addAnnotatedClass(Class clazz){
        metamodelList.add(Metamodel.of(clazz));
        return this;
    }

    /**
     * Creates a new entity manager object that can give sessions to the database
     * @return a new entity manager
     */
    public EntityManager createEntityManager(){
        return new EntityManager(connectionFactory, metamodelList);
    }
}
