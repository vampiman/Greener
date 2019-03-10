package restclient;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PublicTransportClientTest {

    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response res;

    @InjectMocks
    PublicTransportClient PTClient;


    /**
     * Method that before the tests sets up the behaviour of
     * the mock objects.
     */
    @Before
    public void setup() {
        client = mock(Client.class);
        PTClient = new PublicTransportClient(client);

        JSONObject jo = new JSONObject();
        jo.append("Points", 100);
        res = mock(Response.class);

        when(res.readEntity(JSONObject.class)).thenReturn(jo);
        when(res.getStatus()).thenReturn(200);

        builder = mock(Invocation.Builder.class);
        when(builder.get(Response.class)).thenReturn(res);
        when(builder.post(Entity.json(jo))).thenReturn(res);

        target = mock(WebTarget.class);
        when(target.path(anyString())).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(PTClient.client.target("Test")).thenReturn(target);
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
        Assert.assertEquals(PTClient.getPublicTransport("Test")
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
        Assert.assertNotEquals(PTClient.getPublicTransport("Test")
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

        JSONObject j2 = PTClient.postPublicTransport(j1, "Test");
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
        j1.append("Points", 100);

        JSONObject j2 = PTClient.postPublicTransport(j1, "Test");
        Assert.assertEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

    /**
     * Tests the equality between an arbitrary JSON object and
     * and the JSON object made when asked how many kilometers the
     * user has travelled with public transport.
     * Expects equal.
     */
    @Test
    public void createJSONObject() {

        InputStream in2 = new ByteArrayInputStream("100".getBytes());
        System.setIn(in2);

        JSONObject obj = new JSONObject().put("Distance", 100);

        Assert.assertEquals(obj.toJSONString(10), PublicTransportClient.NotCarButPublicTransport().toJSONString(10));
    }

}
