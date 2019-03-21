package restclient;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
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
     * Acquires JSON file from serverside via get-request.
     * @return integer with distance travelled by public transport in kilometers
     */
    public int getPublicTransport() {

        Response resp = this.client.target("http://localhost:8080/serverside/webapi/publictransport/get")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        JSONObject jo = resp.readEntity(JSONObject.class);

        return jo.getInt("publicTransport");
    }

    /**
     * Post a JSON file to the serverside through a post-request with
     * the distance travelled by public transport in kilometers.
     * @param distance distance travelled by public transport
     */
    public void postPublicTransport(int distance) {

        Resource pt = new Resource();
        pt.setTotal_publicTransport(distance);

        this.client.target("http://localhost:8080/serverside/webapi/publictransport/post")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pt));
    }


    // main method for testing only
    //public static void main(String[] args) {

    //    PublicTransportClient client = new PublicTransportClient(ClientBuilder.newClient());

    //    System.out.println(client.getPublicTransport());
    //    client.postPublicTransport(500);
    //    System.out.println(client.getPublicTransport());
    //}
}
