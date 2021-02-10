package com.revature.util;

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

    /**
     * Creates a new entity manager with the path name to the properties file that
     * holds the database information
     * @param pathName the string representation of the path name to the properties file
     */
    public EntityManager(String pathName){
        connectionFactory = new ConnectionFactory(pathName);
        metamodelList = new LinkedList<>();
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
}
