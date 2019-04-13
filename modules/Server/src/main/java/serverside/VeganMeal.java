package serverside;

import cn.hutool.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Singleton;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    public void getDbConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        Class.forName("com.mysql.cj.jdbc.Driver");
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
     * Endpoint /veganmeal/post that modifies the number of eaten vegan meals in
     * the database.
     * @throws ClassNotFoundException Class not found error
     * @throws SQLException SQL-related error
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Resource postIt(Resource re, @HeaderParam("Token") String token,
                           @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {
        getDbConnection();

        PreparedStatement preparedStatement = null;

        String sql = "UPDATE person SET Vegan_meal = Vegan_meal + ? WHERE Email = ?";

        preparedStatement = dbConnection.prepareStatement(sql);

        passToken(token, re);

        CarbonCalculator cc = new CarbonCalculator(2);

        Double insteadOf = cc.veganmeal_Calculator(re.getTotal_Meals(), re.getMealType());

        Double ihad = cc.veganmeal_Calculator(re.getTotal_Meals(), re.getMealType2());


        preparedStatement.setDouble(1, insteadOf - ihad);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();


        Statistics statistics = new Statistics();

        int co2 = statistics.increaseScore(insteadOf - ihad, email);
        statistics.updateLevel(co2, email);
        statistics.updateVeganAch(email);



        preparedStatement.close();
        dbConnection.close();

        return re;
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
    public Resource getAll(@HeaderParam("Token") String token, @HeaderParam("Email") String email)
            throws ClassNotFoundException, SQLException {
        getDbConnection();

        String sql = "SELECT Vegan_meal FROM person WHERE Email = ?";

        PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

        preparedStatement.setString(1, email);

        ResultSet rs = preparedStatement.executeQuery();

        rs.next();
        Double total = rs.getDouble("Vegan_meal");

        Resource re = new Resource();
        passToken(token, re);
        re.setTotal_Meals(total);

        preparedStatement.close();
        dbConnection.close();
        JSONObject jo = new JSONObject();
        jo.put("total", total);
        preparedStatement.close();
        dbConnection.close();
        return re;


    }

}
