package restclient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class PublicTransportClientTest {

    JSONObject jo = new JSONObject().append("Points", 100);

    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response res;

    @InjectMocks
    PublicTransportClient publicTransportClient;


    /**
     * Method that before the tests sets up the behaviour of
     * the mock objects.
     */
    @Before
    public void setup() {
        client = mock(Client.class);
        publicTransportClient = new PublicTransportClient(client);

        res = mock(Response.class);

        when(res.readEntity(JSONObject.class)).thenReturn(jo);
        when(res.getStatus()).thenReturn(200);

        builder = mock(Invocation.Builder.class);
        when(builder.get(Response.class)).thenReturn(res);
        when(builder.post(Entity.json(jo))).thenReturn(res);

        target = mock(WebTarget.class);
        when(target.path(anyString())).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(publicTransportClient.client.target("Test")).thenReturn(target);
    }

    /**
     * Tests the equality between an JSON object
     * and the JSON object received from the mock
     * server through a get-request.
     * Expects equal.
     */
    @Test
    public void getRequestCorrect() {
        JSONObject jo = new JSONObject();
        jo.append("Points", 100);
        Assert.assertEquals(publicTransportClient.getPublicTransport("Test")
                .toJSONString(100), jo.toJSONString(100));
    }

    /**
     * Tests the equality between an JSON object
     * and the JSON object received from the mock
     * server through a get-request.
     * Expects unequal.
     */
    @Test
    public void getRequestIncorrect() {
        JSONObject jo = new JSONObject();
        jo.append("Points", 600);
        Assert.assertNotEquals(publicTransportClient.getPublicTransport("Test")
                .toJSONString(10), jo.toJSONString(10));
    }

    /**
     * Tests equality between an JSON object and
     * the JSON object received through the post method.
     * Expects equal.
     */
    @Test
    public void postRequestCorrect() {
        JSONObject j1 = new JSONObject();
        j1.append("Points", 100);

        JSONObject j2 = publicTransportClient.postPublicTransport(j1, "Test");
        Assert.assertEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

    /**
     * Tests equality between an JSON object and
     * the JSON object received through the post method.
     * Expects unequal.
     */
    @Test
    public void postRequestIncorrect() {
        JSONObject j1 = new JSONObject();
        j1.append("Points", 300);

        JSONObject j2 = publicTransportClient.postPublicTransport(jo, "Test");
        Assert.assertNotEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

    /**
     * Tests the equality between an arbitrary JSON object and
     * and the JSON object made when asked how many kilometers the
     * user has travelled with public transport.
     * Expects equal.
     */
    @Test
    public void createJsonObject() {

        InputStream in2 = new ByteArrayInputStream("100".getBytes());
        System.setIn(in2);

        JSONObject obj = new JSONObject().put("Distance", 100);

        Assert.assertEquals(obj.toJSONString(10),
                PublicTransportClient.notCarButPublicTransport().toJSONString(10));
    }

}
