package restclient;

import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



public class VeganMealTest {

    @Mock
    Client client;

    @Mock
    Response resp;

    @Mock
    JSONObject jo;

    @Mock
    WebTarget webTarget;

    @Mock
    Invocation.Builder ib;

    @InjectMocks
    VeganMeal veganMeal;

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
        veganMeal = new VeganMeal(client);

        when(client.target("http://localhost:8080/server/webapi/veganmeal/totalVegan")).thenReturn(webTarget);
        when(client.target("http://localhost:8080/server/webapi/veganmeal/post")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(ib);
        when(ib.get(Response.class)).thenReturn(resp);

        when(resp.readEntity(JSONObject.class)).thenReturn(jo);
        when(jo.getInt("total")).thenReturn(1);

    }
    //NEED TO ADD SOME RETURN MESSAGE

    /**
     * Test for the sendVeganMeal method.
     */
    @Test
    public void sendVeganMeal() {
        veganMeal.sendVeganMeal();
    }

    /**
     * Test for the getTotalVeganMeals method.
     */
    @Test
    public void getTotalVeganMeals() {
        int result = veganMeal.getTotalVeganMeals();
        Assert.assertEquals(1, result);
    }
}