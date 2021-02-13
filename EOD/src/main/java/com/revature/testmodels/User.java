package com.revature.testmodels;

import com.revature.annotations.*;

/**
 * POJO for a user class, will be used for testing purposes
 */
@Table(tableName = "app_users")
public class User {

    @PrimaryKey(columnName = "user_id") @Serial
    private int id;
    @Column(columnName = "first_name") @NotNull
    private String firstName;
    @Column(columnName = "last_name") @NotNull
    private String lastName;
    @Column(columnName = "username") @NotNull
    private String username;
    @Column(columnName = "password") @NotNull
    private String password;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    @Setter(columnName = "user_id")
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    @Setter(columnName = "first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Setter(columnName = "last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    @Setter(columnName = "username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Setter(columnName = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
