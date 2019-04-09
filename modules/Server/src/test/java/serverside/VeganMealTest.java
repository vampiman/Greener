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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@RunWith(PowerMockRunner.class)
@PrepareForTest(VeganMeal.class)
public class VeganMealTest {

    @Mock private CarbonCalculator ccMock;
    @Mock private Statistics mockStatistics;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Mock private PreparedStatement mockPrepared;
    @Mock private ResultSet rs;

    @InjectMocks private VeganMeal veganMeal;

    /**
     * Setup method for the test methods that depend on mocks.
     */
    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        mockConnection = Mockito.mock(Connection.class);
        mockStatement = Mockito.mock(Statement.class);
        rs = Mockito.mock(ResultSet.class);
        mockStatistics = Mockito.mock(Statistics.class);
        mockPrepared = Mockito.mock(PreparedStatement.class);
        ccMock = Mockito.mock(CarbonCalculator.class);

    }

    /**
     * Test for the /veganmeal/post endpoint.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postIt() throws Exception {
        veganMeal = new VeganMeal();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy","temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepared);
        Mockito.when(mockPrepared.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getDouble(anyString())).thenReturn(1.0);
        whenNew(CarbonCalculator.class).withAnyArguments().thenReturn(ccMock);
        Mockito.when(ccMock.veganmeal_Calculator(anyDouble(), anyString())).thenReturn(1.0);
        whenNew(Statistics.class).withAnyArguments().thenReturn(mockStatistics);
        Mockito.when(mockStatistics.increaseScore(anyDouble(), anyString())).thenReturn(1);
        Mockito.when(mockStatistics.updateLevel(anyDouble(), anyString())).thenReturn(true);
        Mockito.when(mockStatistics.updateVeganAch(anyString())).thenReturn(true);

        Resource re = new Resource();
        re.setMealType("Meat");
        re.setMealType2("Dairy");
        re.setTotal_Meals(1.0);


        Assert.assertEquals(1, veganMeal.postIt(re,"token", "email").getTotal_Meals().intValue());

    }

    /**
     * Test for the /veganmeal/totalVegan endpoint.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        veganMeal = new VeganMeal();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy","temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepared);
        Mockito.when(mockPrepared.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getDouble("Vegan_meal")).thenReturn(1.0);

        Resource resource = veganMeal.getAll("token", null);


        Assert.assertEquals(resource.getTotal_Meals().intValue(), 1);
    }

    /**
     * Method for testing the passToken function with a
     * non-null token.
     */
    @Test
    public void testPassTokenEqual() {
        VeganMeal vegan = new VeganMeal();
        Resource res = new Resource();
        vegan.passToken("token", res);
        Assert.assertEquals("token", res.getToken());
    }

    /**
     * Method for testing the passToken function with a
     * null token.
     */
    @Test
    public void testPassTokenNull() {
        VeganMeal vegan = new VeganMeal();
        Resource res = new Resource();
        vegan.passToken(null, res);
        Assert.assertNull(res.getToken());
    }
}