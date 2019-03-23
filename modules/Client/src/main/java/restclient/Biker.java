package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class Biker contains the client-side implementation for GET & POST request
 * specific to the "Bike instead of driving" feature.
 */
public class Biker {

    protected Client client;

    public Biker(Client client) {
        this.client = client;
    }

    /**
     * Method for sending a JSON-based request to the serverside with
     * the total number of cycled distance.
     */
    public void sendBikers(int distance) {


        JSONObject jo = new JSONObject();
        jo.put("distance", distance);

        Resource vm = new Resource();
        vm.setTotal_Distance(distance);

        this.client.target("http://localhost:8080/serverside/webapi/bike/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(vm));

    }

    /**
     * Method for sending a JSON-based request to the serverside in order to retrieve
     * the total number of cycled distance.
     *
     * @return Total number of cycled distance
     */
    public int getTotalBikers() {
        //Client client =  ClientBuilder.newClient();
        Response resp = this.client.target("http://localhost:8080/serverside/webapi/bike/distance")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);


        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("distance");
    }
}
