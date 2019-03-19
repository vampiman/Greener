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
    public void postAmount(Resource vm) throws ClassNotFoundException, SQLException {

        getDbConnection();

        System.out.println(vm.getTotal_Percentage());
        Statement st = dbConnection.createStatement();
        st.executeUpdate("UPDATE person SET Percentage = Percentage + "
                + vm.getTotal_Percentage() + " WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();

    }

    /**
     * Endpoint /solarpanels/totalSolar that returns the total percentage electricity safed.
     *
     * @return Total percentage of electricity safed
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException           SQL-related error
     *
     */
    @GET
    @Path("percentage")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getAmount() throws ClassNotFoundException, SQLException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT Solar_panels FROM person WHERE Name = 'Robert'");

        rs.next();
        int percentage = rs.getInt("Percentage");

        Resource re = new Resource();

        re.setTotal_Percentage(percentage);

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        st.close();
        dbConnection.close();
        return re;

    }
}
