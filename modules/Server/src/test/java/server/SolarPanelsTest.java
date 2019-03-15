package server;

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

import javax.ws.rs.core.Response;


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
    private SolarPanels veganMeal;

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
        veganMeal = new SolarPanels();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        veganMeal.postAmount();

    }

    /**
     * Test for the /solarpanels/totalSolar endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getAmount() throws SQLException, ClassNotFoundException {
        veganMeal = new SolarPanels();
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
        Response value = veganMeal.getAmount();

        System.out.println(value.getEntity());
        Assert.assertEquals(value.getEntity().toString(), "{\"total\":1}");
    }
}
