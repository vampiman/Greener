package serverside;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
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
@PrepareForTest(SolarPanels.class)
public class SolarPanelsTest {

    @Mock
    private CarbonCalculator ccMock;
    @Mock
    private Statistics mockStatistics;
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
        mockStatistics = Mockito.mock(Statistics.class);
        ccMock = Mockito.mock(CarbonCalculator.class);

    }

    /**
     * Test for the /solarpanels/post endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postAmount() throws Exception {
        solarPanels = new SolarPanels();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(anyString())).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getDouble(anyString())).thenReturn(1.0);
        whenNew(CarbonCalculator.class).withAnyArguments().thenReturn(ccMock);
        Mockito.when(ccMock.solarPanel(anyDouble())).thenReturn(1.0);
        whenNew(Statistics.class).withAnyArguments().thenReturn(mockStatistics);
        Mockito.when(mockStatistics.increaseScore(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(mockStatistics.updateLevel(anyDouble(), anyString())).thenReturn(true);
        Resource re = new Resource();
        re.setTotal_Percentage(1);


        Assert.assertEquals(1, solarPanels.postAmount(re,"token", "email").getTotal_Percentage());

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
                .executeQuery(anyString()))
                .thenReturn(rs);

        Mockito.when(rs.getDouble("Solar_panels")).thenReturn(1.0);
        Mockito.when(rs.next()).thenReturn(true);
        Resource rs = solarPanels.getAmount("token", "email");

        Assert.assertEquals(1, rs.getSavedSolar().intValue());
    }

    /**
     * Method for testing the passToken function with a
     * non-null token.
     */
    @Test
    public void testPassTokenEqual() {
        SolarPanels solar = new SolarPanels();
        Resource res = new Resource();
        solar.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    /**
     * Method for testing the passToken function with a
     * null token.
     */
    @Test
    public void testPassTokenNull() {
        SolarPanels solar = new SolarPanels();
        Resource res = new Resource();
        solar.passToken(null, res);
        Assert.assertNull(res.getToken());
    }
}
