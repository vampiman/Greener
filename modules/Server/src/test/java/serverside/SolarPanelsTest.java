package serverside;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

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
@PrepareForTest(SolarPanels.class)
public class SolarPanelsTest {


    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet rs;
    @InjectMocks
    private SolarPanels solarPanels;

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
     * Test for the /solarpanels/post endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postAmount() throws SQLException, ClassNotFoundException {
        solarPanels = new SolarPanels();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Resource re = new Resource();
        re.setTotal_Percentage(1);
        solarPanels.postAmount(re,"token");

    }

    /**
     * Test for the /solarpanels/percentage endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getAmount() throws SQLException, ClassNotFoundException {
        solarPanels = new SolarPanels();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);

        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement
                .executeQuery("SELECT Solar_panels FROM person WHERE Name = 'Robert'"))
                .thenReturn(rs);

        Mockito.when(rs.getInt("Solar_panels")).thenReturn(1);
        Mockito.when(rs.next()).thenReturn(true);
        Resource rs = solarPanels.getAmount("token");

        Assert.assertEquals(rs.getTotal_Percentage(), 1);
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
