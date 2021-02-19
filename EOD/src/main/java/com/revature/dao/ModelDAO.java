package com.revature.dao;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Serial;
import com.revature.statements.Delete;
import com.revature.statements.Insert;
import com.revature.statements.Select;
import com.revature.statements.Update;
import com.revature.util.ConnectionFactory;
import com.revature.util.Metamodel;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class speaks to the database with a user specified DML statement
 * and will return with whether or not the DML statement was successfully
 * executed or not.
 */
public class ModelDAO {

    /**
     * Creates a new model dao object
     */
    public ModelDAO(){
        super();
    }

    /**
     * Checks to see if the object being passed is already within the
     * database. Not implemented
     * @param model the metamodel of the object class
     * @param object the object being checked
     * @return the object if it is in, null if not
     */
    public Object checkIfObjectIsInUse(Metamodel<?> model, Object object){
        return null;
    }

    /**
     * Inserts some user specified data into a database
     * @param model the model of the class being inserted
     * @param object the data being persisted
     */
    public int insert(Metamodel<?> model, Object object){
        int result = 0;
        Insert insertStatement = new Insert(model ,object);
        ArrayList<String> objectValues = getObjectValues(object);

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertStatement.getInsertStatement());

            for(int i = 0; i < objectValues.size(); i++){
                pstmt.setObject(i + 1, objectValues.get(i));
            }
            result = pstmt.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Updates some data into a database
     * @param model the model of the class being inserted
     * @param newObject the data being updated
     * @param oldObject the data being overwritten
     */
    public int update(Metamodel<?> model, Object newObject, Object oldObject){
        int result = 0;
        Update updateStatement = new Update(model, oldObject);
        ArrayList<String> oldObjectValues = getObjectValues(oldObject);
        ArrayList<String> newObjectValues = getObjectValues(newObject);

        int bound = oldObjectValues.size();

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateStatement.getUpdateStatement());

            for(int i = 0; i < bound; i++){
                pstmt.setObject(i+1, newObjectValues.get(i));
                pstmt.setObject(i+bound+1, oldObjectValues.get(i));
            }
            result = pstmt.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Deletes some user specified data into a database
     * @param model the model of the class being deleted
     * @param object the data being deleted
     */
    public int delete(Metamodel<?> model, Object object){
        int result = 0;
        Delete deleteStatement = new Delete(model, object);
        ArrayList<String> objectValues = getObjectValues(object);

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(deleteStatement.getDeleteStatement());

            for(int i = 0; i < objectValues.size(); i++){
                pstmt.setObject(i + 1, objectValues.get(i));
            }
            result = pstmt.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Selects some user specified data from the database, a select *
     * @param model the model of the class being select
     */
    public List<?> select(Metamodel<?> model, Object object){
        Select selectStatement = new Select(model);
        List<Object> listOfObjects = new LinkedList<>();
        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(selectStatement.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            listOfObjects = mapResultSet(rs, rsmd, object, model);
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfObjects;
    }

    /**
     * Selects some user specified data from the database
     * @param model the table being selected
     * @param object the object being passed by the user
     * @param columnNames the list of column names specified by the user
     */
    public List<?> select(Metamodel<?> model, Object object, String... columnNames){
        Select selectStatement = new Select(model, columnNames);
        List<Object> listOfObjects = new LinkedList<>();
        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(selectStatement.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            listOfObjects = mapResultSet(rs, rsmd, object, model);
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfObjects;
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
            Serial serial = f.getAnnotation(Serial.class);
            PrimaryKey primary = f.getAnnotation(PrimaryKey.class);
            ForeignKey foreign = f.getAnnotation(ForeignKey.class);
            if(column != null | (primary != null && serial == null) | foreign != null) {
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
    private List<Object> mapResultSet(ResultSet rs, ResultSetMetaData rsmd, Object object, Metamodel<?> model){
        List<Object> objectsFromQuery = new LinkedList<>();
        try {
            List<String> columnsInResultSet = new LinkedList<>();
            int bound = rsmd.getColumnCount();
            for(int i = 0; i < bound; i++){
                columnsInResultSet.add(rsmd.getColumnName(i+1));
            }

            while(rs.next()){

                Object newObject = object.getClass().getConstructor().newInstance();

                for(String s : columnsInResultSet){
                    Class<?> type = model.findClassOfColumn(s);
                    Object objectValue = rs.getObject(s);

                    String name = model.findFieldNameOfColumn(s);
                    String methodName = name.substring(0,1).toUpperCase() + name.substring(1);

                    Method method = object.getClass().getMethod("set" + methodName, type);
                    method.invoke(newObject, objectValue);
                }
                objectsFromQuery.add(newObject);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return objectsFromQuery;
    }
}
