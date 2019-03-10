package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



public class VeganMeal {

    protected Client client;

    public VeganMeal(Client client) {
        this.client = client;
    }

    /**
     * Method for sending a JSON-based request to the server with
     * the total number of eaten vegan meals.
     */
    public void sendVeganMeal() {


        JSONObject jo = new JSONObject();
        jo.put("type","dairy");

        this.client.target("http://localhost:8080/server/webapi/veganmeal/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jo));

    }

    /**
     * Method for sending a JSON-based request to the server in order to retrieve
     * the total number of eaten vegan meals.
     * @return Total number of eaten vegan meals
     */
    public int getTotalVeganMeals() {
        //Client client =  ClientBuilder.newClient();

        Response resp = this.client.target("http://localhost:8080/server/webapi/veganmeal/totalVegan")
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
