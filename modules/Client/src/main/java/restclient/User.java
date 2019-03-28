package restclient;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class User {

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
                .signWith(KeyGenClient.KEY)
                .compact();
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

        if (token != null) {
            return true;
        }

        return false;
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
    public String register(String name, String email, String password) {
        String hashedPassword = DigestUtils.sha256Hex(password);

        SessionResource resource = new SessionResource();
        resource.setName(name);
        resource.setEmail(email);
        resource.setPassword(hashedPassword);

        Response res = client.target("http://localhost:8080/serverside/webapi/session/register")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + formAuthHeader())
                .post(Entity.json(resource));

        return res.readEntity(JSONObject.class).toJSONString(10);
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

        System.out.println("Token is " + token);
        System.out.println("Credentials is " + credentials);
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

    //    public static void main(String[] args) {
    //
    //        User user = new User("irem@yahoo.com", "1234");
    //        System.out.println(user.register("Irem", "irem@yahoo.com", "1234"));
    //        //user.login();
    //    }
}