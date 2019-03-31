package serverside;

import cn.hutool.json.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JwtVerifier implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer "; // for JWT
    private Connection dbConnection;
    static final Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes());

    static final Key KEY_VALIDATE = Keys.hmacShaKeyFor(
            "THISISEVENHARDERTHISISTHEKEYTHATONLYSERVERUSESITSSUCHAGREATSECRETITWILLBLOWYOURMIND"
                    .getBytes());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        System.out.println(requestContext.getUriInfo().getPath());
        if (requestContext.getUriInfo().getPath().contains("/")) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String email = verifyJwt(authToken);
                if (email.equals("ERROR")) {
                    try {
                        authToken = issueJwt(authToken);
                        if (authToken.equals("ERROR")) {
                            JSONObject jo = new JSONObject();
                            jo.append("Error", "Access Denied");
                            Response res = Response.status(Response.Status.UNAUTHORIZED)
                                    .entity(jo).build();
                            requestContext.abortWith(res);
                        } else {
                            System.out.println(authToken + " just the token");
                            requestContext.getHeaders().add("token", authToken);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(authToken + email + " token and email");
                    requestContext.getHeaders().add("token", authToken);
                    requestContext.getHeaders().add("email", email);
                }
            }
        }
    }

    /**
     * Method verifies whether the token is valid (= was issued by the server).
     * @param token obtained from Authorization header (either token or credentials)
     * @return "OK" if token is validated, Exception if it's not
     */
    public String verifyJwt(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY_VALIDATE)
                    .parseClaimsJws(token);

            System.out.println(claims.getBody().getExpiration() + " expiration");
            System.out.println(Calendar.getInstance() + " calendar");

            return claims.getBody().getSubject();
        } catch (SignatureException | IncorrectClaimException
                | ExpiredJwtException | MissingClaimException se) {
            System.out.println(se.getClass());
            return "ERROR";
        }

    }

    /**
     * Method issues a valid token if the credentials match the ones in the database.
     * @param credentials received from the Authorization header
     * @return Object: JWT as an encoded String if credentials are verified, Exception if not
     */
    public String issueJwt(String credentials) throws SQLException {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(credentials);
            getDbConnection();

            String email = claims.getBody().get("Email").toString();
            String password = claims.getBody().get("Password").toString();

            System.out.print(email);
            System.out.println(" here is the email");

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT Password FROM person WHERE Email = \""
                    + email + "\"");

            rs.next();
            String passToCheck = rs.getString("Password");
            System.out.println(passToCheck + " password");

            Jws<Claims> claims2 = Jwts.parser()
                    .setSigningKey(KEY)
                    .require("Password", passToCheck)
                    .parseClaimsJws(credentials);

            System.out.println(claims2.toString() + " claims");


            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR, today.get(Calendar.HOUR) + 1);
            System.out.println(today.getTime() + "today");

            return Jwts.builder()
                    .setSubject(email)
                    .setExpiration(today.getTime())
                    .signWith(KEY_VALIDATE)
                    .compact();

        } catch (SignatureException | ExpiredJwtException
                | MissingClaimException | IncorrectClaimException se) {
            return "ERROR";
        }
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
}
