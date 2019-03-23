package restclient;

import cn.hutool.json.JSONObject;

//import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LocalEater {

    protected Client client;

    public LocalEater(Client client) {
        this.client = client;
    }

    //     /**
    //      * The method creates a client and a GET request
    //      * for retrieval of data on the serverside in a JSON file.
    //      *
    //      * @return JSON object contained in the serverside response.
    //      */
    //     public JSONObject getActivityInfo(String uri) {
    //         WebTarget webTarget = this.client.target(uri);

    //         Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    //         Response response = invocationBuilder.get(Response.class);

    //         JSONObject jo = response.readEntity(JSONObject.class);
    // //        System.out.println(jo.toJSONString(10));
    //         return jo;

    //     }

    /**
     * The method creates a client and a POST request.
     *
     * @param total returns total
     */
    public void sendLocal(int total) {
        JSONObject jo = new JSONObject();
        jo.put("total", total);

        Resource vm = new Resource();
        vm.setTotal_Produce(total);

        this.client.target("http://localhost:8080/serverside/webapi/localproduce/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(vm));

    }
    //    public JSONObject postActivityInfo(String uri) {
    //        JSONObject j1 = new JSONObject().append("Weight", "100");
    //        JSONObject j2 = client.target(uri)
    //                .request(MediaType.APPLICATION_JSON)
    //                .post(Entity.json(j1))
    //                .readEntity(JSONObject.class);
    ////        System.out.println(j2.toJSONString(10));
    //        return j2;
    //    }

    /**
     * Method for sending a JSON-based request to the serverside in order to retrieve
     * the total number of bought local products.
     *
     * @return Total number of local produce
     */
    public int getTotalProduce() {
        //Client client =  ClientBuilder.newClient();
        Response resp = this.client.target("http://localhost:8080/serverside/webapi/localproduce/totalProduce")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);


        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("total");
    }
    //      Used for testing only
    //    /**
    //     * Main method that simulates the client.
    //     *
    //     * @param args Input for main
    //     */
    //    public static void main(String[] args) {
    //        LocalEater le = new LocalEater(ClientBuilder.newClient());
    //
    //        le.getActivityInfo("http://localhost:8080/server/webapi/localproduce/get");
    //        le.postActivityInfo("http://localhost:8080/server/webapi/localproduce/post");
    //    }

}
