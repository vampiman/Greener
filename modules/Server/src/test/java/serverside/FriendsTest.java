package serverside;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Friends.class)
public class FriendsTest {
    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockPrepStatement;

    @Mock
    Statement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    Friends friends;

    @Before
    public void setUp() {
        friends = new Friends();

        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        PowerMockito.mockStatic(DriverManager.class);

        mockConnection = mock(Connection.class);
        mockPrepStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        try {
            PowerMockito.when(DriverManager.getConnection(url, user, pass)).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPrepStatement);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockPrepStatement.executeUpdate()).thenReturn(1);
            when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.getInt(any(String.class))).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void followFriend() {
        try {
            friends.followFriend("nat@gmail.com", "robert@yahoo.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void followFriendRSNextSameIDs() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(1);
            friends.followFriend("nat@gmail.com", "robert@yahoo.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void followFriendRSNext() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(1);
            friends.followFriend("nat@gmail.com", "robert@yahoo.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void followFriendRSNextCount0() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(0);
            friends.followFriend("nat@gmail.com", "robert@yahoo.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllFriends() {
        try {
            friends.getAllFriends("nat@gmail.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllFriendsRSNext() {
        try {
            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            friends.getAllFriends("nat@gmail.com");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}