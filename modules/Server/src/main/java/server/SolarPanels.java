package server;

import cn.hutool.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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

        //Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
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
    public void postAmount() throws ClassNotFoundException, SQLException {
        getDbConnection();
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Solar_panels = Solar_panels + 1 WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();

    }

    /**
     * Endpoint /solarpanels/totalSolar that returns the total number of solar panels installed.
     *
     * @return Total number of vegan meals consumed
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     */
    @GET
    @Path("totalSolar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAmount() throws ClassNotFoundException, SQLException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Solar_panels FROM person WHERE Name = 'Robert'");

        rs.next();
        int total = rs.getInt("Solar_panels");

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("total", total);
        st.close();
        dbConnection.close();
        return Response.status(Response.Status.OK).entity(jo).build();

    }
}
