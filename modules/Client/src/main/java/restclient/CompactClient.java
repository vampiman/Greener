package restclient;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CompactClient {

    protected Client client;
    protected String token;
    protected String credentials;


    /**
     * Constructor for LocalEater.
     * @param client to use as the client for requests to the service
     */
    public CompactClient(Client client) {
        this.client = client;
        this.token = null;
        this.credentials = Jwts.builder()
                .claim("username", "Nat")
                .claim("password", "123" )
                .signWith(KeyGenClient.KEY)
                .compact();
    }

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @return JSON object contained in the server response.
     */
    public JSONObject getActivityInfo(String uri) {
        String auth = "Bearer ";
        if (token == null) {
            auth = auth + credentials;
        } else {
            credentials = null;
            auth = auth + token;
        }

        System.out.println("Token is " + token);
        System.out.println("Credentials is " + credentials);

        WebTarget webTarget = this.client.target(uri);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", auth);
        Response response = invocationBuilder.get(Response.class);

        JSONObject jo = response.readEntity(JSONObject.class);
        System.out.println(jo.toJSONString(10));

        if (token == null) {
            token = jo.get("token").toString();
            token = token.replaceAll("\\[", "");
            token = token.replaceAll("\\]", "");
            token = token.replaceAll("\"", "");
        }

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
        return j2;
    }

    //    /**
    //     * Main method that simulates the client.
    //     *
    //     * @param args Input for main
    //     */
    //    public static void main(String[] args) {
    //        CompactClient cc = new CompactClient(ClientBuilder.newClient());
    //
    //        cc.getActivityInfo("http://localhost:8080/serverside/webapi/localproduce/get");
    //        cc.getActivityInfo("http://localhost:8080/serverside/webapi/localproduce/get");
    //    }

}