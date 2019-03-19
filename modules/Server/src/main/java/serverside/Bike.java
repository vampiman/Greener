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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Root resource (exposed at "bike" path).
 */
@Path("bike")
@Singleton
@javax.annotation.Resource
public class Bike {

    private Connection dbConnection;

    /**
     * This method initializes the connection with the database server through jdbc.
     */
    public void getDbConneciton() throws ClassNotFoundException, SQLException {
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
    public Response getData() throws ClassNotFoundException, SQLException {

        getDbConneciton();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Bike FROM person WHERE Name = 'Robert'");
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
    //        return Response.status(Response.Status.OK).entity(jo).build(); }

    /**
     * Method handling HTTP POST requests. It accepts the JSON
     * file containing information on riding a bike from the client.
     * */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)

    public void postData(Resource vm) throws ClassNotFoundException, SQLException {

        getDbConneciton();

        System.out.println(vm.getTotal_Distance());
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Distance = Distance + "
                + vm.getTotal_Distance() + " WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();
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
    public Resource getAll() throws ClassNotFoundException, SQLException {

        getDbConneciton();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT Bike FROM person WHERE Name = 'Robert'");
        rs.next();
        int distance = rs.getInt("Distance");

        Resource re = new Resource();

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