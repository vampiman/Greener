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

    /**
     * Method that is preparing the mocks.
     */
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
            PowerMockito.when(DriverManager.getConnection(url, user, pass))
                    .thenReturn(mockConnection);
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPrepStatement);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockPrepStatement.executeUpdate()).thenReturn(1);
            when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.getInt(any(String.class))).thenReturn(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend.
     */
    @Test
    public void followFriend() {
        try {
            SessionResource re = friends.followFriend("nat@gmail.com", "robert@yahoo.com");
            Assert.assertEquals("Person not found!", re.getStatus());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend requests with the same ID.
     */
    @Test
    public void followFriendRsNextSameIds() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(1);
            SessionResource re = friends.followFriend("nat@gmail.com", "robert@yahoo.com");
            Assert.assertEquals("You can't follow yourself!", re.getStatus());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend requests and trying to follow
     * a person again.
     */
    @Test
    public void followFriendRsNext() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(1);
            SessionResource re = friends.followFriend("nat@gmail.com", "robert@yahoo.com");
            Assert.assertEquals("Already following this person!", re.getStatus());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend request with a successful result.
     */
    @Test
    public void followFriendRsNextCount0() {
        try {
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);
            when(mockResultSet.getInt("COUNT(User_email)")).thenReturn(0);
            SessionResource re = friends.followFriend("nat@gmail.com", "robert@yahoo.com");
            Assert.assertEquals("Success", re.getStatus());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend request returning a list of friends.
     */
    @Test
    public void getAllFriends() throws Exception {
        try {
            SessionResource re = friends.getAllFriends("nat@gmail.com");
            when(mockResultSet.getString("CO_2_saved")).thenReturn("1");
            when(mockResultSet.getInt(any(String.class))).thenReturn(1);
            Assert.assertEquals(1 ,re.getFriends().length);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method testing the follow friend request returning a list with 1 friend.
     */
    @Test
    public void getAllFriendsRsNext() {
        try {

            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            SessionResource re = friends.getAllFriends("nat@gmail.com");
            Assert.assertEquals(1 ,re.getFriends().length);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for the quicksort method reversed duplicate inputs.
     */
    @Test
    public void sortReversedWithDuplicates() {
        String[][] toSort = new String[4][2];

        toSort[0][1] = "1";
        toSort[1][1] = "1";
        toSort[2][1] = "5";
        toSort[3][1] = "4";

        new Friends().quickSort(toSort, 0, toSort.length - 1);

        Assert.assertEquals("5", toSort[0][1]);
        Assert.assertEquals("4", toSort[1][1]);
        Assert.assertEquals("1", toSort[2][1]);
        Assert.assertEquals("1", toSort[3][1]);
    }

    /**
     * Test for the quicksort method with one input.
     */
    @Test
    public void sortOne() {
        String[][] toSort = new String[1][2];

        toSort[0][1] = "3";


        new Friends().quickSort(toSort, 0, toSort.length - 1);

        Assert.assertEquals("3", toSort[0][1]);

    }
}