package serverside;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
@PrepareForTest(HeatConsumption.class)

public class HeatConsumptionTest {


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
    private HeatConsumption heatConsumption;

    /**
     * The set up for the behaviour of all the mock objects used in the tests.
     * @throws SQLException SQL error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        rs = mock(ResultSet.class);
        ccMock = mock(CarbonCalculator.class);
        mockStatistics = mock(Statistics.class);

        mockStatic(DriverManager.class);
        when(DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                "sammy", "temporary")).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        whenNew(CarbonCalculator.class).withAnyArguments().thenReturn(ccMock);
        Mockito.when(ccMock.homeHeatConsumptionSaved(anyDouble(),
                anyDouble(), anyString())).thenReturn(1);
        whenNew(Statistics.class).withAnyArguments().thenReturn(mockStatistics);
        Mockito.when(mockStatistics.increaseScore(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(mockStatistics.updateLevel(anyDouble(), anyString())).thenReturn(true);

        when(mockStatement.executeQuery(
                anyString())).thenReturn(rs);
        when(rs.getDouble("Lowering_home_temperature")).thenReturn(1.0);
        when(rs.next()).thenReturn(true);
    }

    /**
     * Tests if no errors occur during the
     * execution of the post-method of the server.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postHeatConsumption() throws SQLException, ClassNotFoundException {
        heatConsumption = new HeatConsumption();
        Resource resource = new Resource();
        resource.setCurrentHeatConsumption(1);
        resource.setAverageHeatConsumption(1);

        Assert.assertEquals(1, heatConsumption.postData(resource,
                "token", "email").getCurrentHeatConsumption());
        Assert.assertEquals(1, heatConsumption.postData(resource,
                "token", "email").getAverageHeatConsumption());
    }

    /**
     * Tests to see if an integer received from the get-method of the server
     * is the same as the expected value.
     * Expects equal
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getHeatConsumption() throws SQLException, ClassNotFoundException {
        heatConsumption = new HeatConsumption();
        Resource resource = heatConsumption.getData("token", "email");

        Assert.assertEquals(1, resource.getSavedHeatConsumption().intValue());
    }


    /**
     * Method for testing the passToken function with a
     * non-null token.
     */
    @Test
    public void testPassTokenEqual() {
        HeatConsumption heat = new HeatConsumption();
        Resource res = new Resource();
        heat.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    /**
     * Method for testing the passToken function with a
     * null token.
     */
    @Test
    public void testPassTokenNull() {
        HeatConsumption heat = new HeatConsumption();
        Resource res = new Resource();
        heat.passToken(null, res);
        Assert.assertNull(res.getToken());
    }
}

