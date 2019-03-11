package restclient;

import cn.hutool.json.JSONObject;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Class for all client-side necessities to send heat consumption information
 * to the server.
 */

public class HeatConsumptionClient {

    protected Client client;

    public HeatConsumptionClient(Client client) {
        this.client = client;
    }

    /**
     * Acquires JSON file from server via get-request.
     * @return JSON object with information gotten from get-request to server.
     */
    public JSONObject getHeatConsumption(String uri) {

        WebTarget webTarget = this.client.target(uri);

        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        Response res = builder.get(Response.class);

        JSONObject obj = res.readEntity(JSONObject.class);
        System.out.println(obj.toString());

        return obj;
    }

    /**
     * Post a JSON file to the server through a post-request.
     * @param info JSONObject which has to be send to the server
     * @param uri to the URI of the resource of the server which handles the post-request.
     * @return JSONObject send back from the server.
     */
    public JSONObject postHeatConsumption(JSONObject info, String uri) {

        JSONObject j1 = this.client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(info))
                .readEntity(JSONObject.class);

        System.out.println(j1.toString());

        return j1;
    }

    //main method for testing only
    //public static void main(String[] args) {

    //    HeatConsumptionClient client = new HeatConsumptionClient(ClientBuilder.newClient());
    //    JSONObject obj = new JSONObject();

    //    client.postHeatConsumption(obj, "http://localhost:8080/server/webapi/heatconsumption/post");
    //    client.getHeatConsumption("http://localhost:8080/server/webapi/heatconsumption/get");
    //}
}