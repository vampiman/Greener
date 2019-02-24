package Server;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path).
 */
@Path("myresource")
@Singleton
public class MyResource {

    private int count = 0;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the restclient as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("connect")
    @Produces(MediaType.TEXT_PLAIN)
    public String connection(){
        count++;
        return "Server was visited " + count + " time(s) today!";
    }

}
