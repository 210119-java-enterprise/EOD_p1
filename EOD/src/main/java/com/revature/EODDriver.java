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
        User updatedUser = new User("Cole", "Space", "test", "12345");
//        manager.save(user);
//        manager.save(updatedUser);
//        System.out.println("Saved user");
//        manager.update(updatedUser, user);
//        System.out.println("Updated user");
//        manager.delete(updatedUser);
//        System.out.println("Deleted updated user");
//        manager.delete(user);
//        System.out.println("Deleted old user if update didn't work");
    }
}
