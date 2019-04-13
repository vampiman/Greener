package serverside;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyString;
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

    /**
     * Method for preparing the mocks.
     */
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
            PowerMockito.when(DriverManager.getConnection(url, user, pass))
                    .thenReturn(mockConnection);
            when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPrepStatement);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString(anyString())).thenReturn("000000000000000000000000");
            when(mockPrepStatement.executeUpdate()).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for testing the request for total CO2Saved.
     */
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

    /**
     * Method for testing the request for user stats.
     */
    @Test
    public void getStats() throws ClassNotFoundException {
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

    /**
     * Method for testing the request for user personal info.
     */
    @Test
    public void getPersonalInfo() throws ClassNotFoundException {
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

    /**
     * Method for testing the request for user achievements.
     */
    @Test
    public void getAchievements() throws ClassNotFoundException {
        try {
            when(mockResultSet.getString("Achievements")).thenReturn("111111111111111111111111111");
            Assert.assertEquals("111111111111111111111111111",
                    stats.getAchievements("nat@gmail.com")
                    .getAchievements());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for testing the request for user level.
     */
    @Test
    public void getLevel() throws ClassNotFoundException {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(1);
            Assert.assertEquals(1, stats.getLevel("nat@gmail.com").getLevel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method for testing the request for increasing co2saved.
     */
    @Test
    public void increaseScore() throws ClassNotFoundException {
        try {
            when(mockResultSet.getInt("CO_2_saved")).thenReturn(1);
            Assert.assertEquals(1, stats.increaseScore(1.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for testing the update level request, followed by a successful update.
     */
    @Test
    public void updateLevelFalse() throws ClassNotFoundException {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(1);
            Assert.assertFalse(stats.updateLevel(150.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for testing the update level request, and not needing any update.
     */
    @Test
    public void updateLevelTrue() throws ClassNotFoundException {
        try {
            when(mockResultSet.getInt("Level")).thenReturn(2);
            Assert.assertTrue(stats.updateLevel(150.0, "nat@gmail.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Level.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateScoreAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("CO_2_saved")).thenReturn(10.0);
            Assert.assertFalse(stats.updateScoreAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("CO_2_saved")).thenReturn(250.0);
            Assert.assertTrue(stats.updateScoreAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("CO_2_saved")).thenReturn(600.0);
            Assert.assertTrue(stats.updateScoreAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("CO_2_saved")).thenReturn(2000.0);
            Assert.assertTrue(stats.updateScoreAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Bike.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateBikeAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Bike")).thenReturn(10.0);
            Assert.assertFalse(stats.updateBikeAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Bike")).thenReturn(11.0);
            Assert.assertTrue(stats.updateBikeAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Bike")).thenReturn(51.0);
            Assert.assertTrue(stats.updateBikeAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Bike")).thenReturn(151.0);
            Assert.assertTrue(stats.updateBikeAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Public Transport.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateTransportAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Public_transport")).thenReturn(10.0);
            Assert.assertFalse(stats.updateTransportAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Public_transport")).thenReturn(11.0);
            Assert.assertTrue(stats.updateTransportAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Public_transport")).thenReturn(80.0);
            Assert.assertTrue(stats.updateTransportAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Public_transport")).thenReturn(151.0);

            Assert.assertTrue(stats.updateTransportAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Solar Panels.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateSolarAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Solar_panels")).thenReturn(10.0);
            Assert.assertFalse(stats.updateSolarAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Solar_panels")).thenReturn(101.0);
            Assert.assertTrue(stats.updateSolarAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Solar_panels")).thenReturn(300.0);
            Assert.assertTrue(stats.updateSolarAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Solar_panels")).thenReturn(1400.0);
            Assert.assertTrue(stats.updateSolarAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Heat Consumption.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateHeatAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Lowering_home_temperature")).thenReturn(5.0);
            Assert.assertFalse(stats.updateHeatAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Lowering_home_temperature")).thenReturn(51.0);
            Assert.assertTrue(stats.updateHeatAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Lowering_home_temperature")).thenReturn(400.0);
            Assert.assertTrue(stats.updateHeatAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Lowering_home_temperature")).thenReturn(1000.0);
            Assert.assertTrue(stats.updateHeatAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Vegan meal.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateVeganAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Vegan_meal")).thenReturn(10.0);
            Assert.assertFalse(stats.updateVeganAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Vegan_meal")).thenReturn(76.0);
            Assert.assertTrue(stats.updateVeganAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Vegan_meal")).thenReturn(300.0);
            Assert.assertTrue(stats.updateVeganAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Vegan_meal")).thenReturn(1000.0);
            Assert.assertTrue(stats.updateVeganAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Local produce.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateLocalAch() throws ClassNotFoundException {
        try {
            when(mockResultSet.getDouble("Local_produce")).thenReturn(10.0);
            Assert.assertFalse(stats.updateLocalAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Local_produce")).thenReturn(11.0);
            Assert.assertTrue(stats.updateLocalAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Local_produce")).thenReturn(51.0);
            Assert.assertTrue(stats.updateLocalAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble("Local_produce")).thenReturn(101.0);
            Assert.assertTrue(stats.updateLocalAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the achievements for Local produce.
     * Should return True when achievements is changed, false otherwise
     */
    @Test
    public void updateFriendsAch() throws Exception {
        try {
            when(mockResultSet.getDouble(anyString())).thenReturn(1.0);
            Assert.assertFalse(stats.updateFriendsAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble(anyString())).thenReturn(6.0);
            Assert.assertTrue(stats.updateFriendsAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble(anyString())).thenReturn(15.0);
            Assert.assertTrue(stats.updateFriendsAch("jaron@yahoo.nl"));
            when(mockResultSet.getDouble(anyString())).thenReturn(50.0);
            Assert.assertTrue(stats.updateFriendsAch("jaron@yahoo.nl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}