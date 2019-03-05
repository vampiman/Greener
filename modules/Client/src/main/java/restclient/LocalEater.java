package restclient;

import cn.hutool.json.JSONObject;

//import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LocalEater {

    //    /**
    //     * Method that asks the client for information on buying local produce.
    //     *
    //     * @return JSONObject with the data about local produce bought: date of expiry and weight.
    //     *          If no local produce was bought, method returns null.
    //     */
    //
    //    public static JSONObject boughtLocalProduce() {
    //        String choice = null;
    //        String expDate = "";
    //        String weight = "";
    //        System.out.println("Did you buy local produce?");
    //        Scanner sc = new Scanner(System.in);
    //
    //        if (sc.hasNext()) {
    //            choice = sc.nextLine();
    //        }
    //
    //        if (choice.equals("yes")) {
    //            System.out.println("What is it's expiry date?");
    //            if (sc.hasNext()) {
    //                expDate = sc.nextLine();
    //            }
    //            System.out.println("What's the weight of the food in grams?");
    //            if (sc.hasNext()) {
    //                weight = sc.nextLine();
    //            }
    //            JSONObject jo = new JSONObject();
    //            jo.append("Expiry Date", expDate);
    //            jo.append("Weight", weight);
    //
    //            return jo;
    //        } else {
    //            return null;
    //        }
    //
    //    }

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @return JSON object contained in the server response.
     */
    public static JSONObject getActivityInfo() {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/server/webapi");
        WebTarget target = webTarget.path("localproduce/get");

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get(Response.class);

        JSONObject jo = response.readEntity(JSONObject.class);
        System.out.println(jo.toJSONString(10));
        return jo;

    }

    /**
     * The method creates a client and a POST request
     * for upload of the entered data to the server as JSON.
     *
     * @return the JSON object from server response (the one which was posted)
     */
    public static JSONObject postActivityInfo() {
        Client client = ClientBuilder.newClient();
        JSONObject j1 = new JSONObject().append("Weight", "100");
        JSONObject j2 = client.target("http://localhost:8080/server/webapi/localproduce/post")
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
    }
}
