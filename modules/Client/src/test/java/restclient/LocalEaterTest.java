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



public class LocalEaterTest {

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
    LocalEater localEater;

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
        localEater = new LocalEater(client);

        when(client.target("http://localhost:8080/serverside/webapi/localproduce/totalProduce")).thenReturn(webTarget);
        when(client.target("http://localhost:8080/serverside/webapi/localproduce/post")).thenReturn(webTarget);
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
    public void sendLocalEater() {
        localEater.sendLocal(1);
    }

    /**
     * Test for the getTotalVeganMeals method.
     */
    @Test
    public void getTotalProduce() {
        int result = localEater.getTotalProduce();
        Assert.assertEquals(1, result);
    }
}


//package restclient;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import cn.hutool.json.JSONObject;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//public class LocalEaterTest {
//
//
//    @Mock
//    WebTarget target;
//
//    @Mock
//    Invocation.Builder builder;
//
//    @Mock
//    Client client;
//
//    @Mock
//    Response res;
//
//    @InjectMocks
//    LocalEater leClient;
//
//
//    /**
//     * Method that initialises all objects needed for testing
//     * and specifies mocks' behaviour.
//     */
//    @Before
//    public void setup() {
//        client = mock(Client.class);
//        leClient = new LocalEater(client);
//
//        JSONObject jo = new JSONObject();
//        jo.append("Weight", "100");
//        res = mock(Response.class);
//
//        when(res.readEntity(JSONObject.class)).thenReturn(jo);
//        when(res.getStatus()).thenReturn(200); //The OK status
//
//        builder = mock(Invocation.Builder.class);
//        when(builder.get(Response.class)).thenReturn(res);
//        when(builder.post(Entity.json(jo))).thenReturn(res);
//
//        target = mock(WebTarget.class);
//        when(target.path(anyString())).thenReturn(target);
//        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
//        when(leClient.client.target("testLocalEater")).thenReturn(target);
//
//    }
//
//    /**
//     * Tests the equality between an arbitrary
//     * JSON value equal to the pre-set
//     * value in GET response body.
//     * Expects true.
//     */
//    @Test
//    public void getRequestCorrect() {
//        JSONObject jo = new JSONObject();
//        jo.append("Weight", "100");
//        Assert.assertEquals(leClient.getActivityInfo("testLocalEater")
//                .toJSONString(10), jo.toJSONString(10));
//    }
//
//    /**
//     * Tests the (in)equality between an arbitrary
//     * JSON value not equal to the pre-set
//     * value in GET response body.
//     * Expects false.
//     */
//    @Test
//    public void getRequestIncorrect() {
//        JSONObject jo = new JSONObject();
//        jo.append("W", "200g");
//        Assert.assertNotEquals(leClient.getActivityInfo("testLocalEater")
//                .toJSONString(10), jo.toJSONString(10));
//    }
//
//    /**
//     * Tests (in)equality between an arbitrary value of distance
//     * equal to the pre-set value in the post method.
//     * Expects true.
//     */
//    @Test
//    public void postRequestCorrect() {
//        JSONObject j1 = new JSONObject();
//        j1.append("Weight", "100");
//        JSONObject j2 = leClient.postActivityInfo("testLocalEater");
//        Assert.assertEquals(j2.toJSONString(10), j1.toJSONString(10));
//    }
//
//    /**
//     * Tests (in)equality between an arbitrary value of distance
//     * not equal to the pre-set value in the POST method.
//     * Expects false.
//     */
//    @Test
//    public void postRequestIncorrect() {
//        JSONObject j1 = new JSONObject();
//        j1.append("Wieght", "2050");
//        JSONObject j2 = leClient.postActivityInfo("testLocalEater");
//        Assert.assertNotEquals(j2.toJSONString(10), j1.toJSONString(10));
//    }
//
//}
