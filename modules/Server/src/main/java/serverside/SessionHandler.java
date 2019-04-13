package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     * Method generating a random lower-case String of 15 characters
     * that will act as an invite code for the user, the chance of guessing it is
     * approximately 1 in 1 sextillion(21 zeros) cases.
     * 1 to 1 sextillion
     * @return The String code
     */
    public String inviteGenerator() {
        Random random = new Random();
        StringBuilder inviteCode = new StringBuilder("");
        for (int i = 0; i < 15; i++) {
            char nextChar = (char)(random.nextInt(25) + 97);
            inviteCode.append(nextChar);
        }

        return inviteCode.toString();
    }

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
                                    SessionResource sr) throws SQLException,
                                    ClassNotFoundException {
        sr.setToken(token);

        getDbConnection();

        String sql = "INSERT INTO person(ID, Email, Password, "
                + "Name, Friend_code, CO_2_saved, " + "Vegan_meal, "
                + "Bike, Solar_panels, Local_produce, "
                + "Lowering_home_temperature, Public_transport) "
                + "VALUES (?, ?, ?, ?, ?, \"0\", \"0\", \"0\", \"0\","
                + " \"0\", \"0\", \"0\")";

        PreparedStatement ps = dbConnection.prepareStatement(sql);

        //        Statement st = dbConnection.createStatement();
        Statement st2 = dbConnection.createStatement();

        ResultSet rs2 = st2.executeQuery("SELECT MAX(ID) FROM person");
        rs2.next();
        int id = rs2.getInt("MAX(ID)");

        ps.setInt(1, id + 1);
        ps.setString(2, sr.getEmail());
        ps.setString(3, sr.getPassword());
        ps.setString(4, sr.getName());
        ps.setString(5, inviteGenerator());

        ps.executeUpdate();


        //        st.close();
        ps.close();
        rs2.close();
        dbConnection.close();
        return sr;
    }

    //        st.executeUpdate("INSERT INTO person(ID, Email, Password, "
    //                + "Name, Friend_code, CO_2_saved, \n" + "Vegan_meal, "
    //                + "Bike, Solar_panels, Local_produce, "
    //                + "Lowering_home_temperature, Public_transport) \n"
    //                + "VALUES (\"" + (id + 1) + "\", \"" + sr.getEmail() + "\", \""
    //                + sr.getPassword()
    //                + "\", \"" + sr.getName()
    //                + "\", \"" + inviteGenerator() + "\", \"0\", \"0\", \"0\", \"0\",\n"
    //                + "\"0\", \"0\", \"0\");");

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


    //    public static void main(String[] args) {
    //        System.out.println(new SessionHandler().inviteGenerator());
    //    }
}
