package server;

import cn.hutool.json.JSONObject;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "bike" path).
 */
@Path("bike")
@Singleton
@Resource
public class Bike {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "JSON" media type.
     *
     * @return JSONObject returned as an OK response.
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        return Response.status(Response.Status.OK).entity(jo).build();
    }

    /**
     * Method handling HTTP POST requests. It accepts the JSON
     * file containing information on riding a bike from the client.
     *
     * @return JSONObject returned as an OK response.
     */
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postData(JSONObject jo) {
        return Response.status(200).entity(jo).build();
    }
}