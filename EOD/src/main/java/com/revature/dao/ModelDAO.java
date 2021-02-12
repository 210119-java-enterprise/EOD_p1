package com.revature.dao;

import com.revature.annotations.Column;
import com.revature.statements.Delete;
import com.revature.statements.Insert;
import com.revature.statements.Select;
import com.revature.statements.Update;
import com.revature.util.Metamodel;
import sun.management.VMOptionCompositeData;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class speaks to the database with a user specified DML statement
 * and will return with whether or not the DML statement was successfully
 * executed or not.
 */
public class ModelDAO {

    private Connection conn;

    public ModelDAO(Connection conn){
        this.conn = conn;
    }

    public Object checkIfObjectIsInUse(Metamodel<?> model, Object object){
        return null;
    }

    /**
     * Inserts some user specified data into a database
     * @param model the model of the class being inserted
     * @param object the data being persisted
     */
    public void insert(Metamodel<?> model, Object object){
        //TODO for prepared statements, you need to assign the values to ? in the statement string
        Insert insertStatement = new Insert(model);
        ArrayList<String> objectValues = getObjectValues(object);

        //System.out.println(insertStatement.getInsertStatement());
        try{
            PreparedStatement pstmt = conn.prepareStatement(insertStatement.getInsertStatement());

            for(int i = 0; i < objectValues.size(); i++){
                pstmt.setObject(i + 1, objectValues.get(i));
            }

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Updates some data into a database
     * @param model the model of the class being inserted
     * @param newObject the data being updated
     * @param oldObject the data being overwritten
     */
    public void update(Metamodel<?> model, Object newObject, Object oldObject){
        Update updateStatement = new Update(model);
        ArrayList<String> oldObjectValues = getObjectValues(oldObject);
        ArrayList<String> newObjectValues = getObjectValues(newObject);
        int bound = oldObjectValues.size();

        //System.out.println(updateStatement.getUpdateStatement());
        try{
            PreparedStatement pstmt = conn.prepareStatement(updateStatement.getUpdateStatement());

            for(int i = 0; i < bound; i++){
                pstmt.setObject(i+1, newObjectValues.get(i));
                pstmt.setObject(i+5, oldObjectValues.get(i));
            }

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes some user specified data into a database
     * @param model the model of the class being deleted
     * @param object the data being deleted
     */
    public void delete(Metamodel<?> model, Object object){
        Delete deleteStatement = new Delete(model);
        ArrayList<String> objectValues = getObjectValues(object);

        try{
            PreparedStatement pstmt = conn.prepareStatement(deleteStatement.getDeleteStatement());

            for(int i = 0; i < objectValues.size(); i++){
                pstmt.setObject(i + 1, objectValues.get(i));
            }

            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Selects some user specified data from the database, a select *
     * @param model the model of the class being select
     */
    public void select(Metamodel<?> model, Object object){
        Select selectStatement = new Select(model);
        List<Object> listOfObjects;
        try{
            PreparedStatement pstmt = conn.prepareStatement(selectStatement.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            listOfObjects = mapResultSet(rs, rsmd, object);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Selects some user specified data from the database
     * @param model the table being selected
     * @param object the object being passed by the user
     * @param columnNames the list of column names specified by the user
     */
    public void select(Metamodel<?> model, Object object, String... columnNames){
        Select selectStatement = new Select(model, columnNames);
        List<Object> listOfObject;
        try{
            PreparedStatement pstmt = conn.prepareStatement(selectStatement.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            listOfObject = mapResultSet(rs, rsmd, object);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Scrapes the object passed by the user and grabs all of the values within
     * the object
     * @param object the object being scrapped
     * @return an array list of all of the values within the object
     */
    private ArrayList<String> getObjectValues(Object object){
        ArrayList<String> objectValues = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            Column column = f.getAnnotation(Column.class);
            if(column != null) {
                try {
                    Object value = f.get(object);
                    objectValues.add(value.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectValues;
    }

    /**
     * Maps the data returned from the database to a list of object
     * @param rs the result set returned from the database
     * @param rsmd the result set meta data
     * @param object the object composing the list
     * @return the list of objects returned from the database
     */
    private List<Object> mapResultSet(ResultSet rs, ResultSetMetaData rsmd, Object object){
        //get column name from meta data
        //map it to the object field using annotations
        //create new instance of object
        //use object getters to set the data retrieved from data set using column name
        //add object to the list
        //return the list
        return null;
    }
}
