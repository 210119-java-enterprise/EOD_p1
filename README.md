## Emissary of Dissimulation (EOD)

EOD is a very bare bones ORM that asbtracts basic SQL CRUD methods from your java program. Only works with the postgresql driver currently

# Setup

In order for the ORM to be able to connect to your database, you will need to create a .properties file that holds:

  * **url** to the database
  * **admin-usr** the admin username used for logging into the database
  * **admin-pw** the password for the admin user
  
This .properties filepath needs to be passed when creating the Configuration object

This project is stored in my local m2 folder, so if somehow you can add this as a dependency into your own project, I just have one question:

 * **HOW???**

# Usage

There are four main components to the ORM:

  1. Annotating your classes and fields
  2. Creating a Configuration object to pass the .properties filepath to as well as add all of the classes you have annotated
  3. Using the Configuration object to create the EntityManager object that hands out sessions
  4. Creating Session objects to open connections to your database to use the basic CRUD operations
  
### Annotations

There are 4 currently implemented annotations within the ORM:

  1. **@Table** Used to annotate POJO (Plain Old Java Object) classes, must provide the name of the table that the
                POJO represents within the database. ex @Table(tableName = "users")
  2. **@Column** Used to annotate all fields except for the PrimaryKey within the POJO, must provide the name of the
                 column that the field represents wihtin the database. ex @Column(columnName = "username")
  3. **@PrimaryKey** Used to annotate the field within the POJO that represents the primary key within the database,
                     must provide the name of the column in the database. ex @PrimaryKey(columnName = "band_name")
  4. **@Serial** Used to annotate POJO fields that are the primary key within the database as well as a serial value
                 in the database, meaning that the user does not set this value when inserting new entries.
                 ex @PrimaryKey(columnName = "user_id") @Serial
                 
The POJO classes must have a no-args constructor in order for the ORM to function correctly. All getters and setters must be
implemented as well, where the method name follows the convention "get"/"set" + the field name that it is getting or setting.
  
### Configuration Object

In order to create a new Configuration object, the path to the .properties file needs to be passed to the constructor:
  
   * Configuration config = new Configuration("path\to\properties\file");
  
Once the Configuration object has been created, the next step is to add every class that has been annotated. The POJO classes
will need to have a @Table annotation, and all fields being tracked in the database must be annotated with the appropriate
annotations. To add a class to the Configuration object:

  * config.add(POJO.class);
  
You can also chain additions to the Configuration object:

  * config.add(POJO_1.class).add(POJO_2.class).add(POJO_3.class);

### EntityManager Object

The EntityManager object is created from the Configuration object:

  * EntityManager em = config.createEntityManager();

Nothing needs to be passed to create the EntityManager, and the EntityManager will be how you manage your sesions

### Session Object

In order to create a new Session object:

  * Session session = manager.getSession();
  
The Session object is one that is within the ORM package, com.revature.util.Session. The session is used to access the
CRUD methods that can persist the objects created to the database:

  * session.save(Object o) saves o to the database. This object can have either a user defined primary key or a serial
                           primary key, it just needs to be denoted in o's class. It is not a cascading insert
                           
  * session.update(Object new, Object old) updates the old object already in the database with the information within the
                                           new object
                                           
  * session.delete(Object o) deletes o from the database. It is not a cascading delete
  
  * session.selectAll(Object o) selects all columns from the table of the object's class
  
  * session.selectFrom(Object o, String... columnNames) selects only the column names provided from the table that object
                                                        o's class represents in the database

# Wanna help?

Does my lack of features compel you to reach out and help flush out my ORM? Feel free to create a branch and pull down my
ORM to try and implement new ideas. If you are blessed by the gods and I like your additions, I just might steal them and
give you no credit /s.

All notes and additions are appreciated and will definitely be considered to be merged into the master branch
