package server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
//import restclient.Biker;

import javax.ws.rs.core.Response;
import java.sql.*;

//import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Bike.class)
public class BikeTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet rs;
    @InjectMocks
    private Bike bike;

    /**
     * Setup method for the test methods that depend on mocks.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockConnection = Mockito.mock(Connection.class);
        mockStatement = Mockito.mock(Statement.class);
        rs = Mockito.mock(ResultSet.class);
    }

    /**
     * Test for the /biker/post endpoints.
     */
    @Test
    public void postData() throws ClassNotFoundException, SQLException{
        bike = new Bike();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager.getConnection("jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false", "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        bike.postData();
    }

    /**
     * Test for the /biker/Bike endpoints.
     */

    @Test
    public void getAll() throws ClassNotFoundException, SQLException{
        bike = new Bike();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager.getConnection("jdbc:mysql://localhost3306/greener?autoReconnect=true&useSSL=false", "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery("SELECT Bike FROM person WHERE Name = 'Robert'")).thenReturn(rs);
        Mockito.when(rs.getInt("Bike")).thenReturn(1);
        Mockito.when(rs.next()).thenReturn(true);
        Response value = bike.getData();

        System.out.println(value.getEntity());
        Assert.assertEquals(value.getEntity().toString(),"{\"total\":1");

        //Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}

    /*
    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects true.
     */
    /*
    @Test
    public void getDataCorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.getData().getEntity(),
                jo);
    }

    /*
    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects false.
     */
    /*
    @Test
    public void getDataIncorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "1000");
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.getData().getEntity(),
                jo);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    /*
    @Test
    public void getDataCorrectStatus() {
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.getData().getStatus(), 200);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
    /*
    @Test
    public void getDataIncorrectStatus() {
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.getData().getStatus(), 404);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects true.
     */
    /*
    @Test
    public void postDataCorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.postData(jo).getEntity(),
                jo);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects false.
     */
    /*
    @Test
    public void postDataIncorrectEntity() {
        JSONObject jo1 = new JSONObject();
        jo1.append("Distance", "10");
        JSONObject jo2 = new JSONObject();
        jo2.append("Distnce", "1011");
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.postData(jo1).getEntity(),
                jo2);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    /*
    @Test
    public void postDataCorrectStatus() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.postData(jo).getStatus(),
                200);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
/*
 * @Test
 *     public void postDataIncorrectStatus() {
 *         JSONObject jo = new JSONObject();
 *         jo.append("Distance", "10");
 *         Bike bikeServer = new Bike();
 *         Assert.assertNotEquals(bikeServer.postData(jo).getStatus(),
 *                 404);
 *     }
 *     }
 */

