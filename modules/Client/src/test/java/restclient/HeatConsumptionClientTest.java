package restclient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class HeatConsumptionClientTest {

    @Mock
    JSONObject jo;

    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response res;

    @InjectMocks
    HeatConsumptionClient heatConsumptionClient;


    /**
     * Method that before the tests sets up the behaviour of
     * the mock objects.
     */
    @Before
    public void setup() {
        client = mock(Client.class);
        res = mock(Response.class);
        jo = mock(JSONObject.class);
        target = mock(WebTarget.class);
        builder = mock(Invocation.Builder.class);
        heatConsumptionClient = new HeatConsumptionClient(client);

        when(client.target("http://localhost:8080/serverside/webapi/heatconsumption/get")).thenReturn(target);
        when(client.target("http://localhost:8080/serverside/webapi/heatconsumption/post")).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get(Response.class)).thenReturn(res);

        when(res.readEntity(JSONObject.class)).thenReturn(jo);
        when(jo.getInt("heatConsumption")).thenReturn(100);
    }

    /**
     * Tests the get-method of the HeatConsumptionClient. This test
     * compares an arbitrary integer to the integer send back from the
     * (mock)server.
     * Expects equal
     */
    @Test
    public void getHeatConsumption() {
        int get = heatConsumptionClient.getHeatConsumption();
        Assert.assertEquals(100, get);
    }

    /**
     * Tests the get-method of the HeatConsumptionClient. This test
     * compares an arbitrary integer to the integer send back from the
     * (mock)server.
     * Expects unequal
     */
    @Test
    public void getHeatConsumptionIncorrect() {
        int get = heatConsumptionClient.getHeatConsumption();
        Assert.assertNotEquals(500, get);
    }

    /**
     * Tests the post-method of the HeatConsumptionClient. This test
     * checks if no errors occur when the post-method is called.
     */
    @Test
    public void postHeatConsumption() {
        heatConsumptionClient.postHeatConsumption(500);
    }
}

