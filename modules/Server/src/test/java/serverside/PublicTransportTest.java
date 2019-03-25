package serverside;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PublicTransport.class)
public class PublicTransportTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private PublicTransport publicTransport;

    /**
     * The set up for the behaviour of all the mock objects used in the tests.
     * @throws SQLException SQL error
     */
    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        rs = mock(ResultSet.class);

        mockStatic(DriverManager.class);
        when(DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                "sammy", "temporary")).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        when(mockStatement.executeQuery(
                "SELECT Public_transport FROM person WHERE Name = 'Robert'")).thenReturn(rs);
        when(rs.getInt("Public_transport")).thenReturn(100);
        when(rs.next()).thenReturn(true);
    }

    /**
     * Tests if no errors occur during the
     * execution of the post-method of the server.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postPublicTransport() throws SQLException, ClassNotFoundException {
        publicTransport = new PublicTransport();
        Resource resource = new Resource();
        resource.setTotal_publicTransport(100);
        publicTransport.postData(resource, "token");
    }

    /**
     * Tests to see if an integer received from the get-method of the server
     * is the same as the expected value.
     * Expects equal
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getPublicTransport() throws SQLException, ClassNotFoundException {
        publicTransport = new PublicTransport();
        Resource resource = publicTransport.getData("token");

        Assert.assertEquals(resource.getTotal_publicTransport(), 100);
    }

    @Test
    public void testPassTokenEqual() {
        Bike b = new Bike();
        Resource res = new Resource();
        b.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    @Test
    public void testPassTokenNull() {
        Bike b = new Bike();
        Resource res = new Resource();
        b.passToken(null, res);
        Assert.assertNull(res.getToken());
    }
}
