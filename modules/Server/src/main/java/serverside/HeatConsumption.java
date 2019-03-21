package serverside;

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


/**
 * Root resource (exposed at "heatconsumption" path).
 */
@Path("heatconsumption")
@Singleton
public class HeatConsumption {


    private Connection dbConnection;

    /**
     * Method used to create an connection with the database.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */

    public void getDbConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        //Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Endpoint /heatconsumption/get that returns information
     * about the heat consumption of the user.
     * @return integer from database
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getData() throws SQLException, ClassNotFoundException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Lowering_home_temperature "
                + "FROM person WHERE Name = 'Robert'");

        rs.next();
        int total = rs.getInt("Lowering_home_temperature");

        Resource re = new Resource();
        re.setTotal_heatConsumption(total);

        st.close();
        dbConnection.close();

        return re;
    }

    /**
     * Handles the POST-requests from the client.
     * @param re which has the information which needs to be placed in the database.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(Resource re) throws SQLException, ClassNotFoundException {

        getDbConnection();
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Lowering_heat_temperature "
                + "= Lowering_heat_temperature + "
                + re.getTotal_heatConsumption() + " WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();
    }
}
