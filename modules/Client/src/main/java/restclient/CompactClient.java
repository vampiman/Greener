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
                .claim("email", "nstruharova@tudelft.nl")
                .claim("password", "123" )
                .signWith(KeyGenClient.KEY)
                .compact();
    }


    /**
     * Method forms the 'Authorization' header content.
     * @return the 'Authorization' header content
     */
    public String formAuthHeader() {
        //HASH CREDENTIALS
        String auth = "Bearer ";
        if (token == null) {
            auth = auth + credentials;
        } else {
            credentials = null;
            auth = auth + token;
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

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @return JSON object contained in the server response.
     */
    public JSONObject getActivityInfo(String uri) {
        WebTarget webTarget = this.client.target(uri);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", formAuthHeader());
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        return jo;

    }

    /**
     * The method creates a client and a POST request
     * for upload of the entered data to the server as JSON.
     *
     * @return the JSON object from server response (the one which was posted)
     */
    public JSONObject postActivityInfo(String uri) {
        String auth = formAuthHeader();

        JSONObject j1 = new JSONObject().append("Weight", "100");
        JSONObject j2 = client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(j1))
                .readEntity(JSONObject.class);

        adjustToken(j2);

        return j2;
    }

    //            FOR TESTING ONLY
    //            /**
    //             * Main method that simulates the client.
    //             *
    //             * @param args Input for main
    //             */
    //            public static void main(String[] args) {
    //                CompactClient cc = new CompactClient(ClientBuilder.newClient());
    //
    //                cc.getActivityInfo("http://localhost:8080/serverside/webapi/localproduce/get");
    //            }

}