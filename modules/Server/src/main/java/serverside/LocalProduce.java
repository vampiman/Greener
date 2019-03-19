package serverside;

import cn.hutool.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

        dbConnection = DriverManager.getConnection(url, user, pass);
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

    public Resource getData() throws ClassNotFoundException, SQLException {

        getDbConnection();

        Statement st = dbConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Local_produce FROM person WHERE Name = 'Robert'");

        rs.next();
        int product = rs.getInt("Local_produce");

        Resource lp = new Resource();
        lp.setTotal_Produce(product);

        st.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("product", product);
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
    public void postData(Resource lp) throws ClassNotFoundException, SQLException {
        getDbConnection();

        System.out.println(lp.getTotal_Produce());
        Statement st = dbConnection.createStatement();
        st.executeUpdate(
                "UPDATE person SET product = product" + lp.getTotal_Produce() + "WHERE Name = 'Robert'");

        st.close();
        dbConnection.close();
    }

    //    public Response postData(JSONObject jo) {
    //        return Response.status(200).entity(jo).build();
    //    }

}