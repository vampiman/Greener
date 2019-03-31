package serverside;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("statistics")
public class Statistics {

    private Connection dbConnection;

    /**
     * Method for initializing the connection with the database server through jdbc.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    public void getDbConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        //        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    @GET
    @Path("co2")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource totalSaved(@HeaderParam("Email") String email) throws SQLException, ClassNotFoundException {
        Resource re = new Resource();

        getDbConnection();

        Statement st = dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT CO_2_saved FROM person WHERE Email = '" + email + "'");
        rs.next();
        Double saved = rs.getDouble("CO_2_saved");

        re.setCo2Saved(saved);

        return re;
    }

    @GET
    @Path("allstats")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getStats(@HeaderParam("Email") String email) throws SQLException {
        Resource re = new Resource();

        getDbConnection();

        Statement st = dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT Vegan_meal, Bike," +
                " Solar_panels, Local_produce, Lowering_home_temperature, Public_transport" +
                " FROM person WHERE Email = '" + email + "'");
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

    @GET
    @Path("personalinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getPersonalInfo(@HeaderParam("Email") String email) throws SQLException {
        Resource re = new Resource();

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

    @GET
    @Path("achievements")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getAchievements(@HeaderParam("Email") String email) throws SQLException {
        Resource re = new Resource();

        getDbConnection();

        String sql = "SELECT Achievements FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        String achievements = rs.getString("Achievements");

        re.setAchievements(achievements);

        dbConnection.close();
        ps.close();
        rs.close();

        return re;
    }

    @GET
    @Path("level")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getLevel(@HeaderParam("Email") String email) throws SQLException {
        Resource re = new Resource();

        getDbConnection();

        String sql = "SELECT Level FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int level = rs.getInt("Level");

        re.setLevel(level);

        dbConnection.close();
        ps.close();
        rs.close();

        return re;
    }


    public int increaseScore(double co2saved, String email) throws SQLException {

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

        int result = rs.getInt("CO_2_saved");

        ps.close();
        rs.close();
        dbConnection.close();

        return result;
    }

    public boolean updateLevel(double co2saved, String email) throws SQLException {

        getDbConnection();

        String sql = "SELECT Level FROM person WHERE Email = ?";

        PreparedStatement ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int currentLevel = rs.getInt("Level");

        if(co2saved / 150 == currentLevel) {
            return true;
        }

        sql = "UPDATE person SET Level = ? WHERE Email = ?";
        ps = dbConnection.prepareStatement(sql);
        ps.setInt(1, (int)(co2saved / 150) + 1);
        ps.setString(2, email);

        ps.executeUpdate();

        dbConnection.close();
        ps.close();
        rs.close();

        return false;
    }

}
