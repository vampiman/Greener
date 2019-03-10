package restclient;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class VeganMeal {

    protected Client client;

    public VeganMeal(Client client) {
        this.client = client;
    }


    public void sendVeganMeal() {


        JSONObject jo = new JSONObject();
        jo.put("type","dairy");

        this.client.target("http://localhost:8080/server/webapi/veganmeal/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jo));

    }

    public int getTotalVeganMeals() {
//        Client client =  ClientBuilder.newClient();

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
