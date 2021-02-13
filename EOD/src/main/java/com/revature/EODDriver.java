package com.revature;

import com.revature.testmodels.Band;
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
        config.addAnnotatedClass(User.class)
                .addAnnotatedClass(Band.class);

        EntityManager manager = config.createEntityManager();
        Session session1 = manager.getSession();
        Session session2 = manager.getSession();

        User user1 = new User("Cole", "Space", "username", "password");
        User user2 = new User("John", "John", "test", "12345");
        User user3 = new User("Cole","Space","diff","09876");

//        session1.save(user1);
//        System.out.println("saved user 1");
//        session1.save(user2);
//        System.out.println("saved user2");
//        List<User> firstQuery = (List<User>) session1.selectAll(user1);
//        firstQuery.forEach(System.out::print);
//        System.out.println();
//        List<User> secondQuery = (List<User>) session1.selectFrom(user1, "user_id", "username", "password");
//        secondQuery.forEach(System.out::print);
//        System.out.println();
//        session1.update(user3, user1);
//        System.out.println("Updated user1 with user3");
//        session1.delete(user2);
//        System.out.println("Deleted user2");
//        session1.delete(user3);
//        System.out.println("Deleted user3 that was user1");

        Band trivium = new Band("Trivium", "Heafy", "Corey", "Heafy", "Paulo");
        session2.save(trivium);
        System.out.println("Saved trivium");
        List<Band> bands = (List<Band>) session2.selectAll(trivium);
        bands.forEach(System.out::print);
        System.out.println();
        session2.delete(trivium);
        System.out.println("Deleted trivium");

    }
}
