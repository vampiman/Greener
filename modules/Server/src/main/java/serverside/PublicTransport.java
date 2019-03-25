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

/**
 * Root resource (exposed at "publictransport" path).
 */
@Path("publictransport")
@Singleton
public class PublicTransport {

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
     * Endpoint /publictransport/get that returns the
     * amount of kilometers travelled with public
     * transport.
     * @return kilometers travelled with public transport
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getData(@HeaderParam("Token") String token)
            throws SQLException, ClassNotFoundException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Public_transport FROM person WHERE Name = 'Robert'");

        rs.next();
        int total = rs.getInt("Public_transport");

        Resource re = new Resource();
        passToken(token, re);
        re.setTotal_publicTransport(total);

        st.close();
        dbConnection.close();

        return re;
    }

    /**
     * Endpoint /publictransport/post that
     * handles the POST-requests from the client.
     * @param re which has the information which needs to be placed in the database.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(Resource re, @HeaderParam("Token") String token)
            throws SQLException, ClassNotFoundException {
        getDbConnection();

        passToken(token, re);

        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Public_transport = Public_transport + "
                + re.getTotal_publicTransport() + " WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();
    }
}
