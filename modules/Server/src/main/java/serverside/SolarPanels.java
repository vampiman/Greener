package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("solarpanels")
@Singleton
public class SolarPanels {

    private Connection dbConnection;

    /**
     * Method for initializing the connection with the database server through jdbc.
     *
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     */
    public void getDbConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Method used to pass the generated token as a parameter (if there is one).
     * @param token sent from the Authentication service
     * @param res Resource which transports the token
     */
    public void passToken(String token, Resource res) {
        if (token != null) {
            res.setToken(token);
        }
    }

    /**
     * Endpoint /solarpanels/post that modifies the number of solarpanels in the database.
     *
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Resource postAmount(Resource re, @HeaderParam("Token") String token,
                           @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {

        getDbConnection();

        int toAdd = (int)(new CarbonCalculator(2).solarPanel(re.getKwh()));

        passToken(token, re);

        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Solar_panels = Solar_panels + "
                + toAdd + " WHERE Email = '" + email + "'");


        Statistics statistics = new Statistics();

        int co2 = statistics.increaseScore(toAdd, email);
        statistics.updateLevel(co2, email);
        statistics.updateSolarAch(email);

        st.close();
        dbConnection.close();


        return re;
    }

    /**
     * Endpoint /solarpanels/totalSolar that returns the total percentage electricity safed.
     *
     * @return Total percentage of electricity safed
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     *
     */
    @GET
    @Path("percentage")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getAmount(@HeaderParam("Token") String token,
                              @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {

        getDbConnection();


        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT Solar_panels FROM person WHERE Email = '" + email + "'");

        rs.next();
        Double points = rs.getDouble("Solar_panels");

        Resource re = new Resource();
        passToken(token, re);
        re.setSavedSolar(points);

        st.close();
        dbConnection.close();
        st.close();
        dbConnection.close();
        return re;

    }
}
