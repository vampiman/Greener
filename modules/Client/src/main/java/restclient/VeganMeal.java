package restclient;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class VeganMeal {


    public static void sendVeganMeal(int portions) {
        Client client = ClientBuilder.newClient();

        JSONObject jo = new JSONObject();
        jo.put("total",portions);

        client.target("http://134.209.120.167:8080/server1/webapi/veganmeal/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jo));

    }

    public static int getTotalVeganMeals() {
        Client client =  ClientBuilder.newClient();

        Response resp = client.target("http://localhost:8080/server/webapi/veganmeal/totalVegan")
                        .request(MediaType.APPLICATION_JSON)
                        .get(Response.class);

        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("total");
    }


    public static void main(String[] args) {
        //VeganMeal.sendVeganMeal();
        sendVeganMeal(0);
    }
}
