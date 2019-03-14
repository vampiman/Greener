package server;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import cn.hutool.json.JSONObject;
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

import javax.ws.rs.*;
import javax.ws.rs.core.Response;



@RunWith(PowerMockRunner.class)
@PrepareForTest(LocalProduce.class)
public class LocalProduceTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet rs;

    @InjectMocks
    private LocalProduce localProduce;

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
     * Test for the /localproduce/post endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */

    @Test
    public void postData() throws SQLException, ClassNotFoundException {
        localProduce = new LocalProduce();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        localProduce.postData();
    }

    /**
     * Test for the /localproduce/totalVegan endpoint.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getData() throws SQLException, ClassNotFoundException {
        localProduce = new LocalProduce();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy","temporary")).thenReturn(mockConnection);

        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement
                .executeQuery("SELECT Local_produce FROM person WHERE Name = 'Robert'"))
                .thenReturn(rs);

        Mockito.when(rs.getInt("Local_produce")).thenReturn(1);
        Mockito.when(rs.next()).thenReturn(true);
        Response value = localProduce.getData();


        System.out.println(value.getEntity());
        Assert.assertEquals(value.getEntity().toString(), "{\"total\":1}");
        //Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }

}


//    private LocalProduce lp;
//    private JSONObject joCor; //correct JSON object for response testing
//    private JSONObject joIncor; //incorrect JSON object for response testing
//
//
//    /**
//     * Sets up all the variables
//     * needed for testing before
//     * the tests are executed.
//     */
//    @Before
//    public void setup() {
//        lp = new LocalProduce();
//
//        joCor = new JSONObject();
//        joCor.append("Weight", "100");
//        joIncor = new JSONObject();
//        joIncor.append("Weight", "200");
//    }
//
//    /**
//     * Tests the equality between an arbitrary
//     * JSON value equal to the pre-set
//     * value in GET response body.
//     * Expects true.
//     */
//    @Test
//    public void getDataCorrectEntity() {
//        Assert.assertEquals(lp.getData().getEntity(),
//                joCor);
//    }
//
//    /**
//     * Tests the equality between an arbitrary
//     * JSON value equal to the pre-set
//     * value in GET response body.
//     * Expects false.
//     */
//    @Test
//    public void getDataIncorrectEntity() {
//        Assert.assertNotEquals(lp.getData().getEntity(),
//                joIncor);
//    }
//
//    /**
//     * Tests whether the GET method
//     * returns a response with an
//     * OK Status (200).
//     * Expects true.
//     */
//    @Test
//    public void getDataCorrectStatus() {
//        Assert.assertEquals(lp.getData().getStatus(), 200);
//    }
//
//    /**
//     * Tests whether the GET method
//     * returns a response with an
//     * OK Status (200).
//     * Expects false.
//     */
//    @Test
//    public void getDataIncorrectStatus() {
//        Assert.assertNotEquals(lp.getData().getStatus(), 404);
//    }
//
//    /**
//     * Tests the equality between an arbitrary
//     * JSON value equal to the pre-set
//     * value in POST response body.
//     * Expects true.
//     */
//    @Test
//    public void postDataCorrectEntity() {
//        Assert.assertEquals(lp.postData(joCor).getEntity(),
//                joCor);
//    }
//
//    /**
//     * Tests the equality between an arbitrary
//     * JSON value equal to the pre-set
//     * value in POST response body.
//     * Expects false.
//     */
//    @Test
//    public void postDataIncorrectEntity() {
//        Assert.assertNotEquals(lp.postData(joCor).getEntity(),
//                joIncor);
//    }
//
//    /**
//     * Tests whether the POST method
//     * returns a response with an
//     * OK Status (200).
//     * Expects true.
//     */
//    @Test
//    public void postDataCorrectStatus() {
//        Assert.assertEquals(lp.postData(joCor).getStatus(),
//                200);
//    }
//
//    /**
//     * Tests whether the POST method
//     * returns a response with an
//     * OK Status (200).
//     * Expects false.
//     */
//    @Test
//    public void postDataIncorrectStatus() {
//        Assert.assertNotEquals(lp.postData(joCor).getStatus(),
//                404);
//    }
//}