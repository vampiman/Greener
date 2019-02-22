import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class Client {

    public static void main(String[] args) {
        String REST_URI = "http://localhost:8080/OOP-Project-0.1-SNAPSHOT/webapi/myresource";

        javax.ws.rs.client.Client client = ClientBuilder.newClient();

        System.out.println(client.target(REST_URI).request(MediaType.TEXT_PLAIN).get(String.class));
    }
}
