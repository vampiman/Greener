package serverside;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@RunWith(PowerMockRunner.class)
@PrepareForTest(SessionHandler.class)
public class SessionHandlerTest {
    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockPrepStatement;

    @Mock
    Statement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    SessionHandler sessionHandler;

    /**
     * Method for preparing the mocks.
     */
    @Before
    public void setUp() {
        sessionHandler = new SessionHandler();

        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        PowerMockito.mockStatic(DriverManager.class);

        mockConnection = mock(Connection.class);
        mockPrepStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        try {
            PowerMockito.when(DriverManager.getConnection(url, user, pass))
                    .thenReturn(mockConnection);
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPrepStatement);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockPrepStatement.executeUpdate()).thenReturn(1);
            when(mockResultSet.getInt(any(String.class))).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for invite code generator.
     */
    @Test
    public void inviteGenerator() {
        String ab = new SessionHandler().inviteGenerator();

        Assert.assertEquals(15, ab.length());
    }

    /**
     * Test for the register method.
     */
    @Test
    public void register() throws ClassNotFoundException {
        SessionResource sr = new SessionResource();
        try {
            SessionResource result = sessionHandler.register("token", sr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void login() {
        SessionResource result = sessionHandler.login("token");
        Assert.assertEquals("token", result.getToken());
    }

}