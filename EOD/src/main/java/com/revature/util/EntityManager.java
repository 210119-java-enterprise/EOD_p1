package com.revature.util;

import java.sql.SQLException;
import java.util.*;

/**
 * Class responsible for holding all of the metamodels and
 * the connection factory that opens connections to the database
 */
public class EntityManager {

    private List<Metamodel<Class<?>>> metamodelList;

    /**
     * Creates a new entity manager with the path name to the properties file that
     * holds the database information
     * @param metamodelList the list of metamodels taken in by the configuration
     */
    EntityManager(List<Metamodel<Class<?>>> metamodelList){
        this.metamodelList = metamodelList;
    }

    /**
     * Gets a new session to the database
     * @return a new session to the database
     */
    public Session getSession() {
        Session session = null;
        try {
            session = new Session(ConnectionFactory.getConnection(), this);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return session;
    }

    /**
     * Gets all of the metamodel class representations within a list
     * @return the list of metamodels within the entity manager
     */
    List<Metamodel<Class<?>>> getMetamodels() {
        return (metamodelList == null) ? Collections.emptyList() : metamodelList;
    }
}
