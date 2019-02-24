package restclient;

import java.util.Scanner;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


public class restclient {

    public static String getUserName() {
        String username = null;
        Scanner sc = new Scanner(System.in);

        if (sc.hasNext()) {
            username = sc.nextLine();
        }
        return username;
    }

    public static void main(String[] args) {

        String username = restclient.getUserName();

        System.out.println(username);

        String url = "http://localhost:8080/OOP-Project-0.1-SNAPSHOT/webapi/myresource";

        javax.ws.rs.client.Client client = ClientBuilder.newClient();

        System.out.println(client.target(url).request(MediaType.TEXT_PLAIN).get(String.class));
    }
}
