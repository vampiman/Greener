package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("statistics")
public class Statistics {

    private Connection dbConnection;

    private StringBuilder bits = new StringBuilder("000000000000000000000000");

    /**
     * Method for initializing the connection with the database server through jdbc.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    public void getDbConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Returns total CO2 saved by the user.
     * @param email user's email
     * @return resource (JSON) with total CO2 saved
     * @throws SQLException in case of e.g. wrong SQL syntax
     * @throws ClassNotFoundException in case of class not found
     */
    @GET
    @Path("co2")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource totalSaved(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        Resource re = new Resource();

        getDbConnection();

        Statement st = dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT CO_2_saved "
                + "FROM person WHERE Email = '" + email + "'");
        rs.next();
        Double saved = rs.getDouble("CO_2_saved");

        re.setCo2Saved(saved);

        return re;
    }

    /**
     * Returns statistics of CO2 saved by all activities.
     * @param email user's email
     * @return JSON with CO2 saved by individual activities
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    @GET
    @Path("allstats")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getStats(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        Resource re = new Resource();

        getDbConnection();

        Statement st = dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT Vegan_meal, Bike,"
                + " Solar_panels, Local_produce, Lowering_home_temperature, Public_transport"
                + " FROM person WHERE Email = '" + email + "'");
        rs.next();
        Double veganSaved = rs.getDouble("Vegan_meal");
        Double bikeSaved = rs.getDouble("Bike");
        Double solarSaved = rs.getDouble("Solar_panels");
        Double localSaved = rs.getDouble("Local_produce");
        Double loweringSaved = rs.getDouble("Lowering_home_temperature");
        Double publicSaved = rs.getDouble("Public_transport");

        re.setTotal_Meals(veganSaved);
        re.setBikeSaved(bikeSaved);
        re.setSavedSolar(solarSaved);
        re.setLocalSaved(localSaved);
        re.setSavedHeatConsumption(loweringSaved);
        re.setSavedPublicTransport(publicSaved);

        return re;
    }

    /**
     * Returns user's personal information.
     * @param email user's email
     * @return JSONObject with personal information
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    @GET
    @Path("personalinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getPersonalInfo(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        getDbConnection();

        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;

        String sql = "SELECT Name, CO_2_saved FROM person WHERE Email = '" + email + "'";
        String sql1 = "SELECT COUNT(User_email) FROM friends WHERE User_email = '" + email + "'";


        preparedStatement = dbConnection.prepareStatement(sql);
        preparedStatement1 = dbConnection.prepareStatement(sql1);

        ResultSet rs = preparedStatement.executeQuery();
        ResultSet rs1 = preparedStatement1.executeQuery();
        rs.next();
        rs1.next();

        String username = rs.getString("Name");
        Double co2Saved = rs.getDouble("CO_2_saved");
        int friends = rs1.getInt("COUNT(User_email)");

        Resource re = new Resource();
        re.setUserName(username);
        re.setCo2Saved(co2Saved);
        re.setFriendsNo(friends);
        re.setEmail(email);

        preparedStatement.close();
        preparedStatement1.close();
        dbConnection.close();
        rs1.close();
        rs.close();

        return re;
    }

    /**
     * Returns user's achievements status.
     * @param email user's email
     * @return JSONObject with achievements (string of bits)
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    @GET
    @Path("achievements")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getAchievements(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        getDbConnection();

        String sql = "SELECT Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        String achievements = rs.getString("Achievements");

        Resource re = new Resource();
        re.setAchievements(achievements);

        updateScoreAch(email);
        updateFriendsAch(email);

        dbConnection.close();
        ps.close();
        rs.close();

        return re;
    }

    /**
     * Returns user's current level.
     * @param email user's email
     * @return Resource with user's current level
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    @GET
    @Path("level")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getLevel(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        getDbConnection();

        String sql = "SELECT Level FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int level = rs.getInt("Level");

        Resource re = new Resource();
        re.setLevel(level);

        dbConnection.close();
        ps.close();
        rs.close();

        return re;
    }


    /**
     * Adds latest quantity of CO2 saved to produce current total.
     * @param co2saved CO2 in kg saved by latest activity
     * @param email user's email
     * @return new amount of CO2 saved
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    public int increaseScore(double co2saved, String email)
            throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "UPDATE person SET CO_2_saved = CO_2_saved + ? WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);

        ps.setDouble(1, co2saved);
        ps.setString(2, email);

        ps.executeUpdate();

        sql = "SELECT CO_2_saved FROM person WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        final int result = rs.getInt("CO_2_saved");
        dbConnection.close();
        ps.close();
        rs.close();
        return result;
    }

    /**
     * Returns true if current level does not have to be changed, false otherwise.
     * @param co2saved latest CO2 saving
     * @param email user's email
     * @return true if level was correct, false otherwise
     * @throws SQLException in case of e.g. wrong SQL syntax
     */
    public boolean updateLevel(double co2saved, String email)
            throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Level FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int currentLevel = rs.getInt("Level");

        if ((co2saved / 150) + 1 == currentLevel) {
            return true;
        }

        sql = "UPDATE person SET Level = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setInt(1, (int)((co2saved / 150) + 1));
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return false;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email users email
     * @return a boolean
     * @throws SQLException when sql syntax is incorrect
     */
    public boolean updateFriendsAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        PreparedStatement ps = null;

        String sql = "SELECT COUNT(User_email) FROM friends WHERE User_email = ?";

        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);


        ResultSet rs = ps.executeQuery();
        rs.next();

        String sql1 = "SELECT Achievements FROM person WHERE email = ?";

        ps = dbConnection.prepareStatement(sql1);
        ps.setString(1, email);
        ResultSet rs1 = ps.executeQuery();
        rs1.next();

        double currentFriends = rs.getDouble("COUNT(User_email)");
        String initial = rs1.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentFriends > 25) {
            bits.setCharAt(0, '1');
            bits.setCharAt(1, '1');
            bits.setCharAt(2, '1');
        } else if (currentFriends > 10) {
            bits.setCharAt(0, '1');
            bits.setCharAt(1, '1');
        } else if (currentFriends > 3) {
            bits.setCharAt(0, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateScoreAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT CO_2_saved, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("CO_2_saved");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 1500) {
            bits.setCharAt(3, '1');
            bits.setCharAt(4, '1');
            bits.setCharAt(5, '1');
        } else if (currentAch > 500) {
            bits.setCharAt(3, '1');
            bits.setCharAt(4, '1');
        } else if (currentAch > 100) {
            bits.setCharAt(3, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateBikeAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Bike, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Bike");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 150) {
            bits.setCharAt(9, '1');
            bits.setCharAt(10, '1');
            bits.setCharAt(11, '1');
        } else if (currentAch > 50) {
            bits.setCharAt(9, '1');
            bits.setCharAt(10, '1');
        } else if (currentAch > 10) {
            bits.setCharAt(9, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateTransportAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Public_transport, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Public_transport");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 150) {
            bits.setCharAt(12, '1');
            bits.setCharAt(13, '1');
            bits.setCharAt(14, '1');
        } else if (currentAch > 75) {
            bits.setCharAt(12, '1');
            bits.setCharAt(13, '1');
        } else if (currentAch > 10) {
            bits.setCharAt(12, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateSolarAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        //        bits = new StringBuilder()

        String sql = "SELECT Solar_panels, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Solar_panels");


        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 1000) {
            bits.setCharAt(15, '1');
            bits.setCharAt(16, '1');
            bits.setCharAt(17, '1');
        } else if (currentAch > 250) {
            bits.setCharAt(15, '1');
            bits.setCharAt(16, '1');
        } else if (currentAch > 100) {
            bits.setCharAt(15, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();


        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateHeatAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Lowering_home_temperature, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Lowering_home_temperature");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 300) {
            bits.setCharAt(18, '1');
            bits.setCharAt(19, '1');
            bits.setCharAt(20, '1');
        } else if (currentAch > 150) {
            bits.setCharAt(18, '1');
            bits.setCharAt(19, '1');
        } else if (currentAch > 50) {
            bits.setCharAt(18, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateVeganAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Vegan_meal, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Vegan_meal");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);


        if (currentAch > 500) {
            bits.setCharAt(21, '1');
            bits.setCharAt(22, '1');
            bits.setCharAt(23, '1');
        } else if (currentAch > 250) {
            bits.setCharAt(21, '1');
            bits.setCharAt(22, '1');
        } else if (currentAch > 75) {
            bits.setCharAt(21, '1');
        } else {
            return false;
        }


        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

    /**
     * Returns true if the current achievements has to be changed, false otherwise.
     *
     * @param email email of the current user
     * @return boolean
     * @throws SQLException in case of incorrect syntax
     */
    public boolean updateLocalAch(String email) throws SQLException, ClassNotFoundException {

        getDbConnection();

        String sql = "SELECT Local_produce, Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        double currentAch = rs.getDouble("Local_produce");
        final String initial = rs.getString("Achievements");

        bits = new StringBuilder(initial);

        if (currentAch > 250) {
            bits.setCharAt(6, '1');
            bits.setCharAt(7, '1');
            bits.setCharAt(8, '1');
        } else if (currentAch > 100) {
            bits.setCharAt(6, '1');
            bits.setCharAt(7, '1');
        } else if (currentAch > 10) {
            bits.setCharAt(6, '1');
        } else {
            return false;
        }

        String string = new String(bits);

        sql = "UPDATE person SET Achievements = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, string);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return true;
    }

}
