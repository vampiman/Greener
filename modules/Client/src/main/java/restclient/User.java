package restclient;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class User {

    static final Key KEY = Keys.hmacShaKeyFor(
            "ITSASECRETKEYTOOURLITTLEGREENERAPPANDYOULLNEVERFINDWHATITISBECAUSEITSAWESOME"
                    .getBytes());

    private Client client;
    private String credentials;
    private String token;

    /**
     * Constructor for user.
     * @param email user's email
     * @param password user's password (not hashed)
     */
    public User(String email, String password) {
        this.client = ClientBuilder.newClient();
        this.token = null;
        String hashedPassword = DigestUtils.sha256Hex(password);
        this.credentials = Jwts.builder()
                .claim("Email", email)
                .claim("Password", hashedPassword)
                .signWith(KEY)
                .compact();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    /**
     * Method carries out the get request to
     * SessionHandler and returns a token
     * if credentials are valid.
     */
    public boolean login(String auth) {
        if (auth == null) {
            auth = formAuthHeader();
        }
        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/session/login");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", "Bearer " + auth);
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        return token != null;

    }

    /**
     * Method sends a POST request to SessionHandler
     * and if credentials are valid, posts the user
     * to the database.
     * @param name username
     * @param email email
     * @param password password (not hashed yet)
     * @return valid token
     */
    public String register(String name, String email, String password)
            throws IllegalArgumentException {
        SessionResource resource = new SessionResource();
        resource.setName(name);

        //EMAIL VALIDATION
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)"
                + "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Please insert a proper email adress!");
        }

        resource.setEmail(email);
        String hashedPassword = DigestUtils.sha256Hex(password);
        resource.setPassword(hashedPassword);

        Response res = client.target("http://localhost:8080/serverside/webapi/session/register")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + formAuthHeader())
                .post(Entity.json(resource));

        if (res.getStatus() != 200) {
            return null;
        }
        JSONObject jo = res.readEntity(JSONObject.class);
        String joString = jo.toJSONString(10);
        return joString;
    }

    /**
     * Method forms the 'Authorization' header content.
     * @return the 'Authorization' header content
     */
    public String formAuthHeader() {
        String auth;
        if (token == null) {
            auth = credentials;
        } else {
            credentials = null;
            auth = token;
        }

        return auth;
    }

    /**
     * Method skims the token of unnecessary characters.
     * @param jo JSONObject to get the header from
     */
    public void adjustToken(JSONObject jo) {
        if (this.token == null && jo.containsKey("token")) {
            this.token = jo.get("token").toString();
            this.token = this.token.replaceAll("\\[", "");
            this.token = this.token.replaceAll("\\]", "");
            this.token = this.token.replaceAll("\"", "");
        }
    }

    //        public static void main(String[] args) {
    //
    //            User user = new User("irem@yahoo.com", "1234");
    //            System.out.println(user.register("Irem", "irem@sass.com", "1234"));
    //            //user.login();
    //        }
}
