//package serverside;
//
//import cn.hutool.json.JSONObject;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.IncorrectClaimException;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MissingClaimException;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SignatureException;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Calendar;
//import java.util.List;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//
//@Provider
//public class JwtVerifier implements ContainerRequestFilter {
//
//    static Key KEY = Keys.hmacShaKeyFor(
//            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
//                    .getBytes(StandardCharsets.UTF_8));
//
//    static Key KEY_VALIDATE = Keys.hmacShaKeyFor(
//            "THISISEVENHARDERTHISISTHEKEYTHATONLYSERVERUSESITSSUCHAGREATSECRETITWILLBLOWYOURMIND"
//                    .getBytes(StandardCharsets.UTF_8));
//
//    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
//    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer "; // for JWT
//    private Connection dbConnection;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//        if (requestContext.getUriInfo().getPath().contains("/")) {
//            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
//            if (authHeader != null && authHeader.size() > 0) {
//                String authToken = authHeader.get(0);
//                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
//                String email = verifyJwt(authToken);
//                if (email.equals("ERROR")) {
//                    try {
//                        authToken = issueJwt(authToken);
//                        if (authToken.equals("ERROR")) {
//                            abortMe(requestContext);
//                        } else {
//                            requestContext.getHeaders().add("token", authToken);
//                        }
//                    } catch (SQLException e) {
//                        abortMe(requestContext);
//                    }
//                } else {
//                    requestContext.getHeaders().add("token", authToken);
//                    requestContext.getHeaders().add("email", email);
//                }
//            }
//        }
//    }
//
//    /**
//     * Method aborts filter in case of an error.
//     * @param rc request context
//     */
//    public void abortMe(ContainerRequestContext rc) {
//        JSONObject jo = new JSONObject();
//        jo.append("Error", "Access Denied");
//        Response res = Response.status(Response.Status.UNAUTHORIZED)
//                .entity(jo).build();
//        rc.abortWith(res);
//    }
//
//    /**
//     * Method verifies whether the token is valid (= was issued by the server).
//     * @param token obtained from Authorization header (either token or credentials)
//     * @return name if token is validated, "ERROR" if it's not
//     */
//    public String verifyJwt(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(KEY_VALIDATE)
//                    .parseClaimsJws(token);
//
//            return claims.getBody().getSubject();
//        } catch (SignatureException | IncorrectClaimException
//                | ExpiredJwtException | MissingClaimException se) {
//            return "ERROR";
//        }
//
//    }
//
//    /**
//     * Method issues a valid token if the credentials match the ones in the database.
//     * @param credentials received from the Authorization header
//     * @return Object: JWT as an encoded String if credentials are verified, Exception if not
//     */
//    public String issueJwt(String credentials) throws SQLException {
//        try {
//            Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(KEY)
//                    .parseClaimsJws(credentials);
//            getDbConnection();
//
//            String email = claims.getBody().get("Email").toString();
//            String password = claims.getBody().get("Password").toString();
//
//            Statement st = dbConnection.createStatement();
//            ResultSet rs = st.executeQuery("SELECT Password FROM person WHERE Email = \""
//                    + email + "\"");
//
//            rs.next();
//            String passToCheck = rs.getString("Password");
//
//            Jws<Claims> claims2 = Jwts.parser()
//                    .setSigningKey(KEY)
//                    .require("Password", passToCheck)
//                    .parseClaimsJws(credentials);
//
//            Calendar today = Calendar.getInstance();
//            today.set(Calendar.HOUR, today.get(Calendar.HOUR) + 1);
//
//            return Jwts.builder()
//                    .setSubject(email)
//                    .setExpiration(today.getTime())
//                    .signWith(KEY_VALIDATE)
//                    .compact();
//
//        } catch (SignatureException | ExpiredJwtException
//                | MissingClaimException | IncorrectClaimException se) {
//            return "ERROR";
//        }
//    }
//
//    /**
//     * Method for initializing the connection with the database server through jdbc.
//     * @throws SQLException SQL-related error
//     */
//    private void getDbConnection() throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
//        String user = "sammy";
//        String pass = "temporary";
//
//        //        Class.forName("com.mysql.jdbc.Driver");
//
//        dbConnection = DriverManager.getConnection(url, user, pass);
//    }
//}




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

import java.nio.charset.StandardCharsets;
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

    static Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes(StandardCharsets.UTF_8));

    static Key KEY_VALIDATE = Keys.hmacShaKeyFor(
            "THISISEVENHARDERTHISISTHEKEYTHATONLYSERVERUSESITSSUCHAGREATSECRETITWILLBLOWYOURMIND"
                    .getBytes(StandardCharsets.UTF_8));

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer "; // for JWT
    private Connection dbConnection;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!requestContext.getUriInfo().getPath().contains("register")) {
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
     * @return name if token is validated, "ERROR" if it's not
     */
    public String verifyJwt(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY_VALIDATE)
                    .parseClaimsJws(token);

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

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT Password FROM person WHERE Email = \""
                    + email + "\"");

            rs.next();
            String passToCheck = rs.getString("Password");

            Jws<Claims> claims2 = Jwts.parser()
                    .setSigningKey(KEY)
                    .require("Password", passToCheck)
                    .parseClaimsJws(credentials);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR, today.get(Calendar.HOUR) + 1);
            System.out.println(today.getTime() + "today");

            return Jwts.builder()
                    .setSubject(email)
                    .setExpiration(today.getTime())
                    .signWith(KEY_VALIDATE)
                    .compact();

        } catch (SignatureException
                | ExpiredJwtException | MissingClaimException
                | IncorrectClaimException | ClassNotFoundException se) {
            return "ERROR";
        }
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
}