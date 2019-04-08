package serverside;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Statistics.class)
public class StatisticsTest {

    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockPrepStatement;

    @Mock
    Statement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    Statistics stats;

    @Before
    public void setUp() {
        stats = new Statistics();

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
            when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPrepStatement);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockPrepStatement.executeUpdate()).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void totalSaved() {
        Resource re = new Resource();
        re.setCo2Saved(1.1);
        try {
            when(mockResultSet.getDouble(any(String.class))).thenReturn(1.1);
            Assert.assertEquals(re.getCo2Saved(),
                    stats.totalSaved("nat@gmail.com").getCo2Saved());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStats() {
        Resource re = new Resource();
        re.setTotal_Meals(1.1);
        re.setBikeSaved(2.2);
        re.setSavedSolar(3.3);
        try {
            when(mockResultSet.getDouble(eq("Vegan_meal"))).thenReturn(1.1);
            when(mockResultSet.getDouble(eq("Bike"))).thenReturn(2.2);
            when(mockResultSet.getDouble(eq("Solar_panels"))).thenReturn(3.3);
            Assert.assertEquals(re.getTotal_Meals(),
                    stats.getStats("nat@gmail.com").getTotal_Meals());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPersonalInfo() {
        try {
            when(mockResultSet.getString(eq("Name"))).thenReturn("Nat");
            when(mockResultSet.getDouble(eq("CO_2_saved"))).thenReturn(1.2);
            when(mockResultSet.getInt(eq("COUNT(User_email)"))).thenReturn(3);
            Assert.assertEquals("Nat",
                    stats.getPersonalInfo("nat@gmail.com").getUserName());
            Assert.assertEquals(3,
                    stats.getPersonalInfo("nat@gmail.com").getFriendsNo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAchievements() {
        try {
            when(mockResultSet.getString("Achievements")).thenReturn("111111111111111111111111111");
            Assert.assertEquals("111111111111111111111111111", stats.getAchievements("nat@gmail.com")
                    .getAchievements());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLevel() {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(1);
            Assert.assertEquals(1, stats.getLevel("nat@gmail.com").getLevel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void increaseScore() {
        try {
            when(mockResultSet.getInt(("CO_2_saved"))).thenReturn(1);
            Assert.assertEquals(1, stats.increaseScore(1.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateLevelTrue() {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(1);
            Assert.assertTrue(stats.updateLevel(150.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateLevelFalse() {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(2);
            Assert.assertFalse(stats.updateLevel(150.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}