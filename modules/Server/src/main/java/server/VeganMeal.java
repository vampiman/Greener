package server;

import cn.hutool.json.JSONObject;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public void postIt(VeganMealResource vm) throws ClassNotFoundException, SQLException {
            getDBConnection();

            System.out.println(vm.getTotal());
            Statement st = dbConnection.createStatement();
            st.executeUpdate("UPDATE person SET Vegan_meal = Vegan_meal + " + vm.getTotal() + " WHERE Name = 'Robert'");

            st.close();
            dbConnection.close();


    }

    @GET
    @Path("totalVegan")
    @Produces(MediaType.APPLICATION_JSON)
    public VeganMealResource getAll() throws ClassNotFoundException, SQLException {


            getDBConnection();


            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT Vegan_meal FROM person WHERE Name = 'Robert'");

            rs.next();
            int total = rs.getInt("Vegan_meal");

            VeganMealResource vm = new VeganMealResource();

            vm.setTotal(total);

            st.close();
            dbConnection.close();
            JSONObject jo = new JSONObject();
            jo.put("total", total);
            st.close();
            dbConnection.close();
            return vm;


    }

}
