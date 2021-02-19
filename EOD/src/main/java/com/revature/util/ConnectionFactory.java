package com.revature.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class for setting up the connections to the user specified postgresql database
 */
public class ConnectionFactory {

    private static BasicDataSource bds = new BasicDataSource();
    private Properties props = new Properties();

    /*
     * Tries to connect to the postgresql class driver to open a connection
     * to the database
     */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Need to add postgresql as a dependency to your project");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new connection factory by specifying the path to your properties file
     * @param pathName the string representation of the path to your properties file
     */
    ConnectionFactory(String pathName){
        try{
            props.load(new FileReader(pathName));
            bds.setUrl(props.getProperty("url"));
            bds.setUsername(props.getProperty("admin-usr"));
            bds.setPassword(props.getProperty("admin-pw"));
            bds.setMinIdle(3);
            bds.setMaxIdle(6);
            bds.setMaxOpenPreparedStatements(50);
            bds.setMaxWaitMillis(1000);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the database being updated
     * @return the connection to the database
     */
    public static Connection getConnection() throws SQLException{
        return bds.getConnection();
    }
}
