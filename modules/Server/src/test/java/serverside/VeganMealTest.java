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
@PrepareForTest(VeganMeal.class)
public class VeganMealTest {



    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
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

    }

    /**
     * Test for the /veganmeal/post endpoint.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void postIt() throws SQLException, ClassNotFoundException {
        veganMeal = new VeganMeal();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                "sammy","temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Resource re = new Resource();
        re.setTotal_Meals(1);
        veganMeal.postIt(re);

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
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(
                "SELECT Vegan_meal FROM person WHERE Name = 'Robert'")).thenReturn(rs);
        Mockito.when(rs.getInt("Vegan_meal")).thenReturn(1);
        Mockito.when(rs.next()).thenReturn(true);
        Resource resource = veganMeal.getAll();


        Assert.assertEquals(resource.getTotal_Meals(), 1);
    }
}