package server;

import cn.hutool.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "heatconsumption" path).
 */
@Path("heatconsumption")
public class HeatConsumption {

    /**
     * Handles the GET-requests from the client.
     * @return Response object with a JSON object.
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {

        JSONObject jo = new JSONObject();
        jo.put("Points", 100);

        Response res = Response.status(200).entity(jo).build();
        return res;
    }

    /**
     * Handles the GET-requests from the client.
     * @return Integer which is the distance travelled by public transport sent from the client.
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postData(JSONObject obj) {

        return Response.status(200).entity(obj).build();
    }
}