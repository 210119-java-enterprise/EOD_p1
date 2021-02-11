package com.revature;

import com.revature.testmodels.User;
import com.revature.util.EntityManager;

/**
 * Simple class to test the library outside of the JUnit testing that is required
 */
public class EODDriver {

    public static void main(String[] args) {
        EntityManager manager = new EntityManager("src/main/resources/simpleUserTest.properties");
        manager.addAnnotatedClass(User.class);

        User user = new User("Cole", "Space", "username", "password");
        manager.save(user);
    }
}
