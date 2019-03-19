package restclient;

import cn.hutool.json.JSONObject;

import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Class for all client-side necessities to ask for public transport information
 * and sending this to the serverside.
 */

public class PublicTransportClient {

    protected Client client;

    public PublicTransportClient(Client client) {
        this.client = client;
    }

    /**
     * Asks for information about travelling with public transport
     * and converts this data into JSON format.
     * @return JSON object with the distance travelled with public transport.
     */
    public static JSONObject notCarButPublicTransport() {

        int distanceInKilometer = 0;

        Scanner scanner = new Scanner(System.in);

//        System.out.println("How many kilometers did you travel?");
        distanceInKilometer = scanner.nextInt();

        JSONObject obj = new JSONObject().put("Distance", distanceInKilometer);
//        System.out.println(obj);
        return obj;
    }

    /**
     * Acquires JSON file from serverside via get-request.
     * @param uri to the URI of the resource of the serverside which handles the get-request.
     * @return JSON object with information gotten from get-request to serverside.
     */
    public JSONObject getPublicTransport(String uri) {

        WebTarget webTarget = this.client.target(uri);

        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        Response res = builder.get(Response.class);

        JSONObject obj = res.readEntity(JSONObject.class);
//        System.out.println(obj.toString());

        return obj;
    }

    /**
     * Post a JSON file to the serverside through a post-request.
     * @param info JSONObject which has to be send to the serverside
     * @param uri to the URI of the resource of the serverside which handles the post-request.
     * @return JSONObject send back from the serverside.
     */
    public JSONObject postPublicTransport(JSONObject info, String uri) {

        JSONObject j1 = this.client.target(uri)
                              .request(MediaType.APPLICATION_JSON)
                              .post(Entity.json(info))
                              .readEntity(JSONObject.class);

//        System.out.println(j1.toString());

        return j1;
    }


    // main method for testing only
    //public static void main(String[] args) {

    //    PublicTransportClient client = new PublicTransportClient(ClientBuilder.newClient());
    //    JSONObject obj = PublicTransportClient.notCarButPublicTransport();

    //    if (obj != null) {
    //        client.postPublicTransport(obj, "http://localhost:8080/server/webapi/publictransport/post");
    //}

    //    client.getPublicTransport("http://localhost:8080/server/webapi/publictransport/get");
    //}
}
