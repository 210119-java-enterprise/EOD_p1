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

        User user1 = new User("Cole", "Space", "username", "password");
        User user2 = new User("John", "John", "test", "12345");
        User user3 = new User("Cole","Space","diff","09876");
        manager.save(user1);
        System.out.println("saved user 1");
        manager.save(user2);
        System.out.println("saved user2");
        manager.update(user3, user1);
        System.out.println("Updated user1 with user3");
        manager.delete(user2);
        System.out.println("Deleted user2");
        manager.delete(user3);
        System.out.println("Deleted user3 that was user1");
    }
}
