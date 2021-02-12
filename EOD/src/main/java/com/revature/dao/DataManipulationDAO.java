package com.revature.dao;

import com.revature.annotations.Column;
import com.revature.models.Delete;
import com.revature.models.Insert;
import com.revature.models.Update;
import com.revature.util.Metamodel;
import static com.revature.util.EntityManager.getConnectionFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class speaks to the database with a user specified DML statement
 * and will return with whether or not the DML statement was successfully
 * executed or not.
 */
public class DataManipulationDAO {

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
        Insert insertStatement = new Insert(model, object);
        ArrayList<String> objectValues = getObjectValues(object);

        //System.out.println(insertStatement.getInsertStatement());
        try(Connection conn = getConnectionFactory().getConnection()){
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
        Update updateStatement = new Update(model, oldObject);
        ArrayList<String> oldObjectValues = getObjectValues(oldObject);
        ArrayList<String> newObjectValues = getObjectValues(newObject);
        int bound = oldObjectValues.size();

        System.out.println(updateStatement.getUpdateStatement());
        try(Connection conn = getConnectionFactory().getConnection()){
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
        Delete deleteStatement = new Delete(model, object);
        ArrayList<String> objectValues = getObjectValues(object);

        try(Connection conn = getConnectionFactory().getConnection()){
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
}
