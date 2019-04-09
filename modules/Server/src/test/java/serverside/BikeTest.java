package serverside;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bike.class)
public class BikeTest {


    @Mock
    private CarbonCalculator ccMock;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statistics mockStatistics;
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
        mockStatistics = Mockito.mock(Statistics.class);
        ccMock = Mockito.mock(CarbonCalculator.class);
    }

    /**
     * Test for the /biker/post endpoints.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    @Test
    public void postData() throws Exception {
        bike = new Bike();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                "sammy",
                "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(anyString())).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getDouble(anyString())).thenReturn(1.0);
        whenNew(CarbonCalculator.class).withAnyArguments().thenReturn(ccMock);
        Mockito.when(ccMock.bike(anyString(), anyDouble())).thenReturn(1.0);
        whenNew(Statistics.class).withAnyArguments().thenReturn(mockStatistics);
        Mockito.when(mockStatistics.increaseScore(0.0, "email")).thenReturn(1);
        Mockito.when(mockStatistics.updateLevel(anyDouble(), anyString())).thenReturn(true);

        Resource re = new Resource();
        re.setTotal_Distance(1.0);
        re.setCarType("Hybrid");


        Assert.assertEquals(1, bike.postData(re, "token", "email").getTotal_Distance().intValue());
    }

    /**
     * Test for the /biker/Bike endpoints.
     *
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */

    @Test
    public void getAll() throws ClassNotFoundException, SQLException {
        bike = new Bike();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy","temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(
                anyString())).thenReturn(rs);
        Mockito.when(rs.getDouble("Bike")).thenReturn(1.0);
        Mockito.when(rs.next()).thenReturn(true);

        Resource rs = bike.getAll("token", "someEmail");

        Assert.assertEquals(rs.getTotal_Distance().intValue(), 1);

        //Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }


    /**
     * Method for testing the passToken function with a
     * non-null token.
     */
    @Test
    public void testPassTokenEqual() {
        Bike bike = new Bike();
        Resource res = new Resource();
        bike.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    /**
     * Method for testing the passToken function with a
     * null token.
     */
    @Test
    public void testPassTokenNull() {
        Bike bike = new Bike();
        Resource res = new Resource();
        bike.passToken(null, res);
        Assert.assertNull(res.getToken());
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

