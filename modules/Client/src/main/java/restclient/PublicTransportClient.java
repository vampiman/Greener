package restclient;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;

import javax.ws.rs.FormParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Scanner;

/**
 * Class for all client-side necessities to ask for public transport information and sending this to the server
 *
 */

public class PublicTransportClient {

    /**
     * Asks for information about travelling with public transport and converts this data into JSON format
     * @return JSON object with the distance travelled with public transport.
     */
    public static JSONObject NotCarButPublicTransport() {
        boolean rodePublicTransport = false;
        int distanceInKilometer = 0;

        System.out.println("Did you take public transport instead of the car?");
        Scanner scanner = new Scanner(System.in);

        if (scanner.nextLine().equals("yes")) {
            rodePublicTransport = true;
            System.out.println(rodePublicTransport);

            System.out.println("How many kilometers did you travel?");
            distanceInKilometer = scanner.nextInt();

            JSONObject obj = new JSONObject().put("Distance", distanceInKilometer);
            System.out.println(obj);
            return obj;
        }

        else {
            System.out.println("No changes have been made");
            return null;
        }
    }

    /**
     * Acquires JSON file from server via get-request
     * @return JSON object with information gotten from get-request to server
     */
    public static JSONObject getPublicTransport() {

        Client client = ClientBuilder.newClient();
        String url = "http://localhost:8080/server/webapi/publictransport/get";

        JSONObject obj = client.target(url).request(MediaType.APPLICATION_JSON).get(JSONObject.class);
        System.out.println(obj);

        int points = (int) obj.get("Points");
        System.out.println(points);

        return obj;
    }

    /**
     * Post a JSON file to the server through a post-request
     * @return JSON file with the information posted to the server by this method
     */
    public static void postPublicTransport(JSONObject info) {

        Client client = ClientBuilder.newClient();

        client.target("http://localhost:8080/server/webapi/publictransport/post")
                                        .request(MediaType.APPLICATION_JSON)
                                        .post(Entity.json(info));
        System.out.println(info.toString());
    }
}
