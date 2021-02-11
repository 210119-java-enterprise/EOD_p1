package com.revature.dao;

import com.revature.annotations.Column;
import com.revature.models.Insert;
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

    public void insert(Metamodel<?> model, Object object){
        //TODO for prepared statements, you need to assign the values to ? in the statement string
        Insert insertStatement = new Insert(model, object);
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
        System.out.println(insertStatement.getInsertStatement());
        //open a connection
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
}
