package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *  The class Biker contains the client-side implementation for GET & POST request
 *  specific to the "Bike instead of driving" feature.
 */
public class Biker {

    protected Client client;

    public Biker(Client client) {
        this.client = client;
    }

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @param uri specifies the URI of the resource
     * @return JSON object contained in the server response.
     */
    public JSONObject getActivityInfo(String uri) {
        WebTarget webTarget = this.client.target(uri);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get(Response.class);

        JSONObject jo = response.readEntity(JSONObject.class);
        System.out.println(jo.toJSONString(10));
        return jo;
    }


    /**
     * The method creates a client and a POST request
     * for upload of the entered data to the server as JSON.
     *
     * @param uri specifies the URI of the resource
     * @return JSON object from server response (the one which was posted)
     */
    public JSONObject postActivityInfo(String uri) {
        JSONObject j1 = new JSONObject().append("Distance", "10");
        JSONObject j2 = this.client.target(uri)
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
        Biker biker = new Biker(ClientBuilder.newClient());

        biker.getActivityInfo("http://localhost:8080/server/webapi/bike/get");
        biker.postActivityInfo("http://localhost:8080/server/webapi/bike/post");
    }
}
