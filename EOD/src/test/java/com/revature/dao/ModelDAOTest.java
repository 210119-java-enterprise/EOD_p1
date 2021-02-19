package com.revature.dao;

import com.revature.testmodels.TestBand;
import com.revature.testmodels.TestUser;
import com.revature.util.Configuration;
import com.revature.util.ConnectionFactory;
import com.revature.util.Metamodel;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

public class ModelDAOTest {

    TestBand band;
    TestUser user;
    Metamodel<?> bandModel;
    Metamodel<?> userModel;
    ModelDAO modelDaoDut;


    @Before
    public void setUpTest() throws SQLException {

        Configuration config = new Configuration("src/main/resources/simpleUserTest.properties");

        modelDaoDut = new ModelDAO();

        band = new TestBand("Trivium", "Heafy", "Corey", "Heafy", "Paulo", "Alex");
        user = new TestUser("Cole", "Space", "cspace", "password");

        bandModel = Metamodel.of(TestBand.class);
        userModel = Metamodel.of(TestUser.class);
    }

    @After
    public void tearDownTest(){
        band = null;
        user = null;
        bandModel = null;
        userModel = null;
        modelDaoDut = null;
    }

    @Test
    public void test_insert_withClassWithSerialPrimaryKey(){
        int rowsAffected = modelDaoDut.insert(userModel, user);

        Assert.assertEquals(1, rowsAffected);

        modelDaoDut.delete(userModel, user);
    }

    @Test
    public void test_insert_withClassWithNonSerialPrimaryKey(){
        int rowsAffected = modelDaoDut.insert(bandModel, band);

        Assert.assertEquals(1, rowsAffected);

        modelDaoDut.delete(bandModel, band);
    }

    @Test
    public void test_delete(){
        int rowsAffected = 0;
        modelDaoDut.insert(userModel, user);
        modelDaoDut.insert(bandModel, band);

        rowsAffected += modelDaoDut.delete(userModel, user);
        rowsAffected += modelDaoDut.delete(bandModel, band);

        Assert.assertEquals(2, rowsAffected);
    }

    @Test
    public void test_update(){
        int rowsAffected = 0;

        modelDaoDut.insert(userModel, user);
        modelDaoDut.insert(bandModel, band);

        TestBand newBand = new TestBand("Trivium", "Corey", "Heafy", "Corey", "Paulo", "Alex");
        TestUser newUser = new TestUser("Cole", "Space", "cspace", "12345");

        rowsAffected += modelDaoDut.update(bandModel, newBand, band);
        rowsAffected += modelDaoDut.update(userModel, newUser, user);

        Assert.assertEquals(2, rowsAffected);

        modelDaoDut.delete(bandModel, newBand);
        modelDaoDut.delete(userModel, newUser);
    }

    @Test
    public void test_selectAll_withAnInputNotInDB(){
        List<?> bands = modelDaoDut.select(bandModel, band);

        Assert.assertEquals(0, bands.size());
    }

    @Test
    public void test_selectAll_withAnInputInDB(){
        modelDaoDut.insert(bandModel, band);
        List<?> bands = modelDaoDut.select(bandModel, band);

        Assert.assertEquals(1, bands.size());
        modelDaoDut.delete(bandModel, band);
    }

    @Test
    public void test_selectSome_withAnInputNotInDB(){
        List<?> bands = modelDaoDut.select(bandModel, band, "vocalist", "drummer");

        Assert.assertEquals(0, bands.size());
    }

    @Test
    public void test_selectSome_withAnInputInDB(){
        modelDaoDut.insert(bandModel, band);
        List<?> bands = modelDaoDut.select(bandModel, band, "vocalist", "drummer");

        Assert.assertEquals(1, bands.size());

        modelDaoDut.delete(bandModel, band);
    }
}
