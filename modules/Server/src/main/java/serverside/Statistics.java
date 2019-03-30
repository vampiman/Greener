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

}
