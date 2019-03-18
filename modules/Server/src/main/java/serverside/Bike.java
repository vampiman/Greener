package serverside;

import cn.hutool.json.JSONObject;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

/**
 * Root resource (exposed at "bike" path).
 */
@Path("bike")
@Singleton
@Resource
public class Bike {

    private Connection dbConnection;

    /**
     * This method initializes the connection with the database server through jdbc.
     */
    public void getDbConneciton() throws ClassNotFoundException, SQLException{
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "JSON" media type.
     *
     * @return JSONObject returned as an OK response.
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() throws ClassNotFoundException, SQLException{

        getDbConneciton();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT  Bike FROM person WHERE Name = 'Robert'");
        rs.next();
        int total = rs.getInt("Bike");
        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("total", total);
        dbConnection.close();
        st.close();
        return Response.status(Response.Status.OK).entity(jo).build();
    }
    //    public Response getData() {
//        JSONObject jo = new JSONObject();
//        jo.append("Weight", "100");
//        return Response.status(Response.Status.OK).entity(jo).build();
//    }

    /**
     * Method handling HTTP POST requests. It accepts the JSON
     * file containing information on riding a bike from the client.
     *
     * @return JSONObject returned as an OK response.
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)

    public void postData() throws ClassNotFoundException, SQLException{
        getDbConneciton();
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Bike = Bike + 1 WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();
    }

    //public Response postData(JSONObject jo) {
        //return Response.status(200).entity(jo).build();
    //}
}