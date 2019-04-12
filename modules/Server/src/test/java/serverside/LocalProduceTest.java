package serverside;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
@PrepareForTest(LocalProduce.class)
public class LocalProduceTest {

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
        ccMock = mock(CarbonCalculator.class);
        mockStatistics = mock(Statistics.class);
    }

    /**
     * Test for the /localproduce/post endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */

    @Test
    public void postData() throws Exception {
        localProduce = new LocalProduce();
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
        Mockito.when(ccMock.localproduce_Calculator(anyDouble(), anyString())).thenReturn(1.0);
        whenNew(Statistics.class).withAnyArguments().thenReturn(mockStatistics);
        Mockito.when(mockStatistics.increaseScore(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(mockStatistics.updateLevel(anyDouble(), anyString())).thenReturn(true);

        Resource lp = new Resource();
        lp.setTotal_Produce(1.0);


        Assert.assertEquals(1, localProduce.postData(lp,
                "token", "email").getTotal_Produce().intValue());
    }

    /**
     * Test for the /localproduce/totalProduce endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getData() throws SQLException, ClassNotFoundException {
        localProduce = new LocalProduce();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);

        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement
                .executeQuery(anyString()))
                .thenReturn(rs);

        Mockito.when(rs.getDouble("Local_produce")).thenReturn(1.0);
        Mockito.when(rs.next()).thenReturn(true);


        Assert.assertEquals(1, localProduce.getData("token", "email").getLocalSaved().intValue());
    }

    /**
     * Method for testing the passToken function with a
     * non-null token.
     */
    @Test
    public void testPassTokenEqual() {
        LocalProduce local = new LocalProduce();
        Resource res = new Resource();
        local.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    /**
     * Method for testing the passToken function with a
     * null token.
     */
    @Test
    public void testPassTokenNull() {
        LocalProduce local = new LocalProduce();
        Resource res = new Resource();
        local.passToken(null, res);
        Assert.assertEquals(null, res.getToken());
    }
}