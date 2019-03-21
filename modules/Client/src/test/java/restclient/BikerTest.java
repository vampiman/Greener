package restclient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BikerTest {


    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response res;

    @InjectMocks
    Biker bikeClient;


    /**
     * Method that initialises all objects needed for testing
     * and specifies mocks' behaviour.
     */
    @Before
    public void setup() {
        client = mock(Client.class);
        bikeClient = new Biker(client);

        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        res = mock(Response.class);

        when(res.readEntity(JSONObject.class)).thenReturn(jo);
        when(res.getStatus()).thenReturn(200); //The OK status

        builder = mock(Invocation.Builder.class);
        when(builder.get(Response.class)).thenReturn(res);
        when(builder.post(Entity.json(jo))).thenReturn(res);

        target = mock(WebTarget.class);
        when(target.path(Matchers.anyString())).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(bikeClient.client.target("testBike")).thenReturn(target);

    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects true.
     */
    @Test
    public void getRequestCorrect() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Assert.assertEquals(bikeClient.getActivityInfo("testBike")
                .toJSONString(10), jo.toJSONString(10));
    }

    /**
     * Tests the (in)equality between an arbitrary
     * JSON value not equal to the pre-set
     * value in GET response body.
     * Expects false.
     */
    @Test
    public void getRequestIncorrect() {
        JSONObject jo = new JSONObject();
        jo.append("Dst", "200km");
        Assert.assertNotEquals(bikeClient.getActivityInfo("testBike")
                .toJSONString(10), jo.toJSONString(10));
    }

    /**
     * Tests (in)equality between an arbitrary value of distance
     * equal to the pre-set value in the post method.
     * Expects true.
     */
    @Test
    public void postRequestCorrect() {
        JSONObject j1 = new JSONObject();
        j1.append("Distance", "10");
        JSONObject j2 = bikeClient.postActivityInfo("testBike");
        Assert.assertEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

    /**
     * Tests (in)equality between an arbitrary value of distance
     * not equal to the pre-set value in the POST method.
     * Expects false.
     */
    @Test
    public void postRequestIncorrect() {
        JSONObject j1 = new JSONObject();
        j1.append("Distance", "2050");
        JSONObject j2 = bikeClient.postActivityInfo("testBike");
        Assert.assertNotEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

}
