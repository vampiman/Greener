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
     * Test for the /localproduce/totalProduce endpoint.
     *
     * @throws SQLException           SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        localProduce = new LocalProduce();
        mockStatic(DriverManager.class);
        Mockito.when(DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false",
                        "sammy", "temporary")).thenReturn(mockConnection);

        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement
                .executeQuery("SELECT Local_produce FROM person WHERE Name = 'Robert'"))
                .thenReturn(rs);

        Mockito.when(rs.getInt("Local_produce")).thenReturn(1);
        Mockito.when(rs.next()).thenReturn(true);
        Response value = localProduce.getData();


        System.out.println(value.getEntity());
        Assert.assertEquals(value.getEntity().toString(), "{\"total\":1}");
    }
}