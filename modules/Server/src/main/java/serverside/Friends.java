package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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


    /**
     * Method executes request to follow another person, if possible.
     * @param email user's email
     * @param toSearch friend's email
     * @return Resource with follow request status
     * @throws SQLException in case of for example SQL Syntax error
     * @throws ClassNotFoundException in case of class not found
     */
    @POST
    @Path("follow")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionResource followFriend(@HeaderParam("Email") String email,
                                        @QueryParam("user") String toSearch)
                                        throws SQLException, ClassNotFoundException {

        getDbConnection();

        SessionResource sr = new SessionResource();

        PreparedStatement ps = null;

        String sql = "SELECT ID, Email FROM person WHERE Email = ?";

        ps = dbConnection.prepareStatement(sql);

        ps.setString(1, toSearch);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            sr.setStatus("Person not found!");
            return sr;
        }

        int friendId = rs.getInt("ID");
        String friend = rs.getString("Email");

        sql = "SELECT ID FROM person WHERE Email = ?";

        ps = dbConnection.prepareStatement(sql);

        ps.setString(1, email);

        rs = ps.executeQuery();
        rs.next();

        int yourId = rs.getInt("ID");

        if (friendId == yourId) {
            sr.setStatus("You can't follow yourself!");
            return sr;
        }

        sql = "SELECT COUNT(User_email) FROM friends WHERE User_email = ? AND Friend_email = ?";

        ps = dbConnection.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, toSearch);

        rs = ps.executeQuery();

        rs.next();

        if (rs.getInt("COUNT(User_email)") > 0) {
            sr.setStatus("Already following this person!");
            return sr;
        }

        sql = "INSERT INTO friends(ID, User_email,"
                + " Friend_email) VALUES (?, ?, ?)";

        ps = dbConnection.prepareStatement(sql);
        ps.setInt(1, yourId);
        ps.setString(2, email);
        ps.setString(3, friend);

        ps.executeUpdate("INSERT INTO friends(ID, User_email,"
                + " Friend_email) VALUES ('" + yourId + "', "
                + "'" + email + "', '" + friend + "')");

        sr.setStatus("Success");

        dbConnection.close();
        ps.close();
        rs.close();

        return sr;
    }

    /**
     * Returns all user's friends.
     * @param email user's email
     * @return Resource containing all friends
     * @throws SQLException in case of for example SQL syntax error
     * @throws ClassNotFoundException in case class is not found
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionResource getAllFriends(@HeaderParam("Email") String email)
            throws SQLException, ClassNotFoundException {
        getDbConnection();

        Statement st =  dbConnection.createStatement();

        ResultSet rs = st.executeQuery("SELECT COUNT(Friend_email) "
                + "FROM friends WHERE User_email = '" + email + "'");
        rs.next();


        String[][] friends = new String[rs.getInt("COUNT(Friend_email)")][2];

        rs = st.executeQuery("SELECT Friend_email FROM friends WHERE User_email = '" + email + "'");

        Statement st2 = dbConnection.createStatement();

        int counter = 0;
        while (rs.next()) {
            ResultSet rs1 = st2.executeQuery("SELECT Name, CO_2_saved FROM person "
                    + "WHERE Email = '" + rs.getString("Friend_email") + "'");

            rs1.next();

            friends[counter][0] = rs1.getString("Name");
            friends[counter][1] = rs1.getString("CO_2_saved");

            counter++;
        }

        SessionResource sr = new SessionResource();
        sr.setFriends(friends);

        st.close();
        rs.close();

        return sr;
    }

}
