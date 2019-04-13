package serverside;

import cn.hutool.json.JSONObject;

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

/**
 * Root resource (exposed at "bike" path).
 */
@Path("bike")
@Singleton
public class Bike {

    private Connection dbConnection;

    /**
     * This method initializes the connection with the database server through jdbc.
     */
    public void getDbConneciton() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }


    //    public Response getData() {
    //        JSONObject jo = new JSONObject();
    //        jo.append("Weight", "100");
    //        return Response.status(Response.Status.OK).entity(jo).build(); }

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
     * Method handling HTTP POST requests. It accepts the JSON
     * file containing information on riding a bike from the client.
     * */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Resource postData(Resource re, @HeaderParam("Token") String token,
                         @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {

        getDbConneciton();

        double toAdd = new CarbonCalculator(2).bike(re.getCarType(), re.getTotal_Distance());

        passToken(token, re);

        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Bike = Bike + "
                + toAdd + " WHERE Email = '" + email + "'");

        Statistics statistics = new Statistics();

        int co2 = statistics.increaseScore(toAdd, email);
        statistics.updateLevel(co2, email);
        statistics.updateBikeAch(email);

        st.close();
        dbConnection.close();

        return re;
    }


    /**
     * Endpoint /bike/distance that returns the total cycled distance.
     * @return Total distance of cycled distance
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException
     *
     */
    @GET
    @Path("distance")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getAll(@HeaderParam("Token") String token, @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {

        getDbConneciton();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT Bike FROM person WHERE Email = '" + email + "'");
        rs.next();
        double distance = rs.getDouble("Bike");

        Resource re = new Resource();
        passToken(token, re);
        re.setTotal_Distance(distance);

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        st.close();
        dbConnection.close();
        return re;
    }




    //public Response postData(JSONObject jo) {
    //return Response.status(200).entity(jo).build();
    //}
}