package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("session")
public class SessionHandler {

    private Connection dbConnection;

    /**
     * 1 to 1 sextillion
     * @return
     */
    public String inviteGenerator() {
        Random random = new Random();
        String inviteCode = "";
        for(int i = 0; i < 15; i++) {
            char a = (char)(random.nextInt(25) + 97);
            inviteCode += a;
        }

        return inviteCode;
    }

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
     * Method adds a new user to the database
     * and returns a SessionResource with a valid token.
     * @param token created by the verifier
     * @param sr a SessionResource
     * @return SessionResource containing a valid token.
     * @throws SQLException in case of syntax errors, etc.
     */
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SessionResource register(@HeaderParam("token") String token,
                                    SessionResource sr) throws SQLException {
        sr.setToken(token);

        getDbConnection();
        Statement st = dbConnection.createStatement();
        st.executeUpdate("INSERT INTO person(Email, Password, "
                + "Name, Score, Friend_code, CO_2_saved, \n" + "Vegan_meal, "
                + "Bike, Solar_panels, Local_produce, "
                + "Lowering_home_temperature, Public_transport) \n"
                + "VALUES (\"" + sr.getEmail() + "\", \"" + sr.getPassword()
                + "\", \"" + sr.getName()
                + "\", \"0\", \"" + inviteGenerator() + "\", \"0\", \"0\", \"0\", \"0\",\n"
                + "\"0\", \"0\", \"0\");");

        st.close();
        dbConnection.close();
        return sr;
    }

    /**
     * Method returns a SessionResource with a valid token
     * to use for other requests.
     * @param token created by the verifier
     * @return SessionResource containing a valid token
     */
    @GET
    @Path("login")
    public SessionResource login(@HeaderParam("token") String token) {
        SessionResource sr = new SessionResource();
        sr.setToken(token);
        return sr;
    }


    public static void main(String[] args) {
        System.out.println(new SessionHandler().inviteGenerator());
    }
}
