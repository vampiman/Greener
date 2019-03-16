package restclient;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;
import server.KeyGen;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LocalEater {

    private Client client;
    private String token;
    private String credentials;


    /**
     * Constructor for LocalEater.
     * @param client to use as the client for requests to the service
     */
    public LocalEater(Client client) {
        this.client = client;
        this.token = null;
        this.credentials = Jwts.builder()
                .claim("username", "Nat")
                .claim("password", "123" )
                .signWith(KeyGen.KEY)
                .compact();
    }

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @return JSON object contained in the server response.
     */
    public JSONObject getActivityInfo(String uri) {
        System.out.println("Token is " + token);
        System.out.println("Credentials are " + credentials);
        WebTarget webTarget = this.client.target(uri);

        String auth = "Bearer ";
        if (token == null) {
            auth = auth + credentials;
        } else {
            auth = auth + token;
        }

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth);
        Response response = invocationBuilder.get(Response.class);
        System.out.println(response.getStatusInfo());

        JSONObject jo = response.readEntity(JSONObject.class);

        if (token == null) {
            token = jo.get("Token").toString();
            token = token.replaceAll("\\[", "");
            token = token.replaceAll("\\]", "");
            token = token.replaceAll("\"", "");
        }

        System.out.println(jo.toJSONString(10));
        return jo;

    }

    /**
     * The method creates a client and a POST request
     * for upload of the entered data to the server as JSON.
     *
     * @return the JSON object from server response (the one which was posted)
     */
    public JSONObject postActivityInfo(String uri) {
        JSONObject j1 = new JSONObject().append("Weight", "100");
        JSONObject j2 = client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(j1))
                .readEntity(JSONObject.class);
        System.out.println(j2.toJSONString(10));
        return j2;
    }

    /**
     * Main method that simulates the client.
     *
     * @param args Input for main
     */
    public static void main(String[] args) {
        LocalEater le = new LocalEater(ClientBuilder.newClient());

        le.getActivityInfo("http://localhost:8080/server/webapi/localproduce/get");
        le.getActivityInfo("http://localhost:8080/server/webapi/localproduce/get");
    }

}
