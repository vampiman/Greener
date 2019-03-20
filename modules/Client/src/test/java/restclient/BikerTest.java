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
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BikerTest {


    @Mock
    JSONObject jo;

    @Mock
    WebTarget webTarget;

    @Mock
    Invocation.Builder ib;

    @Mock
    Client client;

    @Mock
    Response resp;

    @InjectMocks
    Biker biker;


    /**
     * Setup for the following mock-dependant tests.
     */
    @Before
    public void setup() {
        client = Mockito.mock(Client.class);
        resp = Mockito.mock(Response.class);
        jo = Mockito.mock(JSONObject.class);
        webTarget = Mockito.mock(WebTarget.class);
        ib = Mockito.mock(Invocation.Builder.class);
        biker = new Biker(client);

        when(client.target("http://localhost:8080/serverside/webapi/bike/distance")).thenReturn(webTarget);
        when(client.target("http://localhost:8080/serverside/webapi/bike/post")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(ib);
        when(ib.get(Response.class)).thenReturn(resp);

        when(resp.readEntity(JSONObject.class)).thenReturn(jo);
        when(jo.getInt("distance")).thenReturn(1);

    }
    //NEED TO ADD SOME RETURN MESSAGE

    /**
     * Test for the sendBiker method.
     */
    @Test
    public void sendBiker() {
        biker.sendBikers(1);
    }

    /**
     * Test for the getTotalDistance method.
     */
    @Test
    public void getTotal_Distance() {
        int result = biker.getTotalBikers();
        Assert.assertEquals(1, result);
    }
}