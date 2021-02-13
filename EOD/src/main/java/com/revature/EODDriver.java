package com.revature;

import com.revature.testmodels.User;
import com.revature.util.Configuration;
import com.revature.util.EntityManager;
import com.revature.util.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple class to test the library outside of the JUnit testing that is required
 */
public class EODDriver {

    public static void main(String[] args) {

        Configuration config = new Configuration("src/main/resources/simpleUserTest.properties");
        config.addAnnotatedClass(User.class);

        EntityManager manager = config.createEntityManager();
        Session session = manager.getSession();

        User user1 = new User("Cole", "Space", "username", "password");
        User user2 = new User("John", "John", "test", "12345");
        User user3 = new User("Cole","Space","diff","09876");

        session.save(user1);
        System.out.println("saved user 1");
        session.save(user2);
        System.out.println("saved user2");
        List<User> firstQuery = (List<User>) session.selectAll(user1);
        firstQuery.forEach(System.out::print);
        System.out.println();
        List<User> secondQuery = (List<User>) session.selectFrom(user1, "user_id", "username", "password");
        secondQuery.forEach(System.out::print);
        System.out.println();
        session.update(user3, user1);
        System.out.println("Updated user1 with user3");
        session.delete(user2);
        System.out.println("Deleted user2");
        session.delete(user3);
        System.out.println("Deleted user3 that was user1");
    }
}
