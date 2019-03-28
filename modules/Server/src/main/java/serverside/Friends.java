package serverside;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.sql.*;

@Path("friends")
public class Friends {

    private Connection dbConnection;

    /**
     * Method used to create an connection with the database.
     * @throws SQLException SQL error
     * @throws ClassNotFoundException Class not found error
     */

    public void getDbConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        //Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, user, pass);
    }

    @POST
    @Path("follow")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionResource followFriend(@HeaderParam("Email") String email,
                                        @QueryParam("user") String toSearch)
                                        throws SQLException, ClassNotFoundException {

        getDbConnection();

        SessionResource sr = new SessionResource();

        Statement st = dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT ID, Email FROM person WHERE Email = '" + toSearch + "'");

        rs.next();

        int friendId = rs.getInt("ID");
        String friend = rs.getString("Email");

        rs = st.executeQuery("SELECT ID FROM person WHERE Email = '" + email + "'");
        rs.next();

        int yourId = rs.getInt("ID");

        if(friendId == yourId) {
            sr.setStatus("You can't follow yourself!");
            return sr;
        }



        rs = st.executeQuery("SELECT COUNT(User_email) FROM friends WHERE User_email = '" + email + "'" +
                " AND Friend_email = '"+ toSearch +"'");

        rs.next();

        if(rs.getInt("COUNT(User_email)") > 0) {
            sr.setStatus("Already following this person!");
            return sr;
        }


        st.executeUpdate("INSERT INTO friends(ID, User_email, Friend_email) VALUES ('" + yourId + "', " +
                "'" + email +"', '" + friend + "')");


        sr.setStatus("Success");

        st.close();
        rs.close();

        return sr;
    }


}
