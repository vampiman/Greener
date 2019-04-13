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
 * Root resource (exposed at "localproduce" path).
 */
@Path("localproduce")
@Singleton
public class LocalProduce {


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
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "JSON" media type.
     *
     * @return JSONObject returned as an OK response.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getData(@HeaderParam("Token") String token, @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Local_produce "
                + "FROM person WHERE Email = '" + email + "'");

        rs.next();
        Double produce = rs.getDouble("Local_produce");

        Resource lp = new Resource();
        passToken(token, lp);
        lp.setLocalSaved(produce);

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("product", produce);
        st.close();
        dbConnection.close();
        return lp;


    }

    // public Response getData() {
    //   JSONObject jo = new JSONObject();
    // jo.append("Weight", "100");
    // return Response.status(Response.Status.OK).entity(jo).build(); }

    /**
     * Method handling HTTP POST requests. It accepts the JSON
     * file containing information on buying local produce.
     *
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Resource postData(Resource lp, @HeaderParam("Token") String token,
                             @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {
        getDbConnection();

        double toAdd = new CarbonCalculator(2).localproduce_Calculator(lp.getTotal_Produce(),
                                                                                lp.getMealType());

        passToken(token, lp);

        Statement st = dbConnection.createStatement();
        st.executeUpdate(
                "UPDATE person SET Local_produce = Local_produce + "
                        + toAdd + " WHERE Email = '" + email + "'");


        Statistics statistics = new Statistics();

        int co2 = statistics.increaseScore(toAdd, email);
        statistics.updateLevel(co2, email);
        statistics.updateLocalAch(email);

        st.close();
        dbConnection.close();

        return lp;
    }

    //    public Response postData(JSONObject jo) {
    //        return Response.status(200).entity(jo).build();
    //    }

}