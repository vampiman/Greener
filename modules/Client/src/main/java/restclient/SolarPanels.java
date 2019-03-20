package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



public class SolarPanels {

    protected Client client;

    public SolarPanels(Client client) {
        this.client = client;
    }

    /**
     * Method for sending a JSON-based request to the serverside with
     * the total number of solar panels.
     */
    public void sendSolarPanels(int total) {


        JSONObject jo = new JSONObject();
        jo.put("total", total);

        Resource vm = new Resource();
        vm.setTotal_Percentage(total);

        this.client.target("http://localhost:8080/serverside/webapi/solarpanels/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(vm));

    }

    /**
     * Method for sending a JSON-based request to the serverside in order to retrieve
     * the total number of solar panels.
     * @return Total number of solar panels
     */
    public int getTotalSolarPanels() {
        //Client client =  ClientBuilder.newClient();
        Response resp = this.client.target("http://localhost:8080/serverside/webapi/solarpanels/totalPanels")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);


        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("total");
    }

    //      For testing only
    //    public static void main(String[] args) {
    //        //VeganMeal.sendVeganMeal();
    //        System.out.println(getTotalVeganMeals());
    //    }
}
