package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Class for all client-side necessities to send heat consumption information
 * to the serverside.
 */

public class HeatConsumptionClient {

    protected Client client;

    public HeatConsumptionClient(Client client) {
        this.client = client;
    }

    /**
     * Acquires JSON file from serverside via get-request.
     * @return int with information gotten from get-request to serverside.
     */
    public int getHeatConsumption() {

        Response resp = this.client.target("http://localhost:8080/serverside/webapi/heatconsumption/get")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("heatConsumption");
    }

    /**
     * Post an integer to the serverside through a post-request.
     * @param temperature is the temperature in the home of the user.
     */
    public void postHeatConsumption(int temperature) {

        Resource hc = new Resource();
        hc.setTotal_heatConsumption(temperature);

        this.client.target("http://localhost:8080/serverside/webapi/heatconsumption/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(hc));
    }
}
