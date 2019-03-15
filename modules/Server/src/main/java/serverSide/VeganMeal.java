package serverside;

import cn.hutool.json.JSONObject;
import restclient.VeganMealResource;

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


@Path("veganmeal")
@Singleton
public class VeganMeal {

    private Connection dbConnection;

    /**
     * Method for initializing the connection with the database server through jdbc.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    public void getDbConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        //        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }


    /**
     * Endpoint /veganmeal/post that modifies the number of eaten vegan meals in
     * the database.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postIt(VeganMealResource vm) throws ClassNotFoundException, SQLException {
        getDbConnection();

        System.out.println(vm.getTotal());
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Vegan_meal = Vegan_meal + "
                + vm.getTotal() + " WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();


    }


    /**
     * Endpoint /veganmeal/totalVegan that returns the total number of
     * vegan meals consumed.
     * @return Total number of vegan meals consumed
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    @GET
    @Path("totalVegan")
    @Produces(MediaType.APPLICATION_JSON)
    public VeganMealResource getAll() throws ClassNotFoundException, SQLException {


        getDbConnection();


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
