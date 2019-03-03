package server;

import cn.hutool.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "publictransport" path).
 */
@Path("publictransport")
public class PublicTransport {

    static int integer = 0;
    /**
     * Handles the GET-requests from the client.
     * @return Response object with a JSON object
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response getData() {

            JSONObject jo = new JSONObject();
            jo.put("Points", integer);

        Response res = Response.status(200).entity(jo).build();
        return res;
    }

    /**
     * Handles the GET-requests from the client.
     * @return Integer which is the distance travelled by public transport sent from the client
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public static Response postData(@FormParam("Distance") int param1) {

        return Response.status(200).entity(param1).build();
    }
}
