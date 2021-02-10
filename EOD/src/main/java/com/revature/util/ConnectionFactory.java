package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class for setting up the connections to the user specified postgresql database
 */
public class ConnectionFactory {

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
    public ConnectionFactory(String pathName){
        try{
            props.load(new FileReader(pathName));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the database being updated
     * @return the connection to the database
     */
    public Connection getConnection(){
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(props.getProperty("url"),
                    props.getProperty("admin-usr"),
                    props.getProperty("admin-pw"));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return conn;
    }
}
