package serverside;

import cn.hutool.db.Session;

import javax.print.attribute.standard.Media;
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

        rs = st.executeQuery("SELECT COUNT(User_email) FROM friends " +
                "WHERE User_email = '" + email + "'" +
                " AND Friend_email = '"+ toSearch +"'");

        rs.next();

        if(rs.getInt("COUNT(User_email)") > 0) {
            sr.setStatus("Already following this person!");
            return sr;
        }


        st.executeUpdate("INSERT INTO friends(ID, User_email," +
                " Friend_email) VALUES ('" + yourId + "', " +
                "'" + email +"', '" + friend + "')");


        sr.setStatus("Success");

        st.close();
        rs.close();

        return sr;
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionResource getAllFriends(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        SessionResource sr = new SessionResource();

        getDbConnection();

        Statement st =  dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT COUNT(Friend_email) FROM friends WHERE User_email = '" + email + "'");
        rs.next();


        String[][] friends = new String[rs.getInt("COUNT(Friend_email)")][2];

        rs = st.executeQuery("SELECT Friend_email FROM friends WHERE User_email = '" + email + "'");

        Statement st2 = dbConnection.createStatement();

        int i = 0;
        while (rs.next()) {
            ResultSet rs1 = st2.executeQuery("SELECT Name, CO_2_saved FROM person " +
                    "WHERE Email = '" + rs.getString("Friend_email") + "'");

            rs1.next();

            friends[i][0] = rs1.getString("Name");
            friends[i][1] = rs1.getString("CO_2_saved");

            i++;
        }

        sr.setFriends(friends);

        st.close();
        rs.close();

        return sr;
    }

}
