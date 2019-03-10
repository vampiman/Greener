package server;

import cn.hutool.json.JSONObject;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("veganmeal")
@Singleton
public class VeganMeal {

    private Connection dbConnection;

    public void getDBConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

//        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postIt() throws ClassNotFoundException, SQLException {
        getDBConnection();
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Vegan_meal = Vegan_meal + 1 WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();


    }

    @GET
    @Path("totalVegan")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws ClassNotFoundException, SQLException {


        getDBConnection();


        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Vegan_meal FROM person WHERE Name = 'Robert'");

        rs.next();
        int total = rs.getInt("Vegan_meal");

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("total", total);
        st.close();
        dbConnection.close();
        return Response.status(Response.Status.OK).entity(jo).build();


    }

}
