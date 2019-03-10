package restclient;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


public class Restclient {

    /**
     * Method that asks for the name of the client.
     *
     * @return String with the username of the client
     */

    public static String getUserName() {
        String username = null;
        System.out.println("Please provide your username in order to connect:");
        Scanner sc = new Scanner(System.in);


        username = sc.nextLine();


        System.out.println("Welcome to Greenie, " + username + "!");

        return username;
    }


    /**
     * Main method that acts as a client.
     *
     * @param args Input for main
     */
    public static void main(String[] args) {

        getUserName();

        String url = "http://localhost:8080/server/webapi/myresource/connect";

        Client client = ClientBuilder.newClient();

        System.out.println(client.target(url).request(MediaType.TEXT_PLAIN).get(String.class));
    }
}
