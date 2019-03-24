package restclient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CompactClientTest {

    @Mock
    WebTarget targetToken;
    WebTarget targetNoToken;

    @Mock
    Invocation.Builder builderNoToken;
    Invocation.Builder builderToken;

    @Mock
    Client client;

    @Mock
    Response resToken;
    Response resNoToken;

    @InjectMocks
    CompactClient ccClient;

    /**
     * Method that initialises all objects needed for testing
     * and specifies mocks' behaviour.
     */
    @Before
    public void setup() {
        client = mock(Client.class);
        ccClient = new CompactClient(client);
        ccClient.token = null;
        ccClient.credentials = "";

        JSONObject jo1 = new JSONObject();
        jo1.append("Weight", "100");
        resNoToken = mock(Response.class);
        when(resNoToken.readEntity(JSONObject.class)).thenReturn(jo1);
        when(resNoToken.getStatus()).thenReturn(200); //The OK status

        JSONObject jo2 = new JSONObject();
        jo2.append("Weight", "100");
        jo2.append("token", "tok");
        resToken = mock(Response.class);
        when(resToken.readEntity(JSONObject.class)).thenReturn(jo2);
        when(resToken.getStatus()).thenReturn(200); //The OK status

        builderNoToken = mock(Invocation.Builder.class);
        when(builderNoToken.header(eq("Authorization"), eq("Bearer "))).thenReturn(builderNoToken);
        when(builderNoToken.get(Response.class)).thenReturn(resNoToken);
        when(builderNoToken.post(Entity.json(jo1))).thenReturn(resNoToken);

        targetNoToken = mock(WebTarget.class);
        when(targetNoToken.path(anyString())).thenReturn(targetNoToken);
        when(targetNoToken.request(MediaType.APPLICATION_JSON))
                .thenReturn(builderNoToken);
        when(ccClient.client.target("testCompactClient")).thenReturn(targetNoToken);

        builderToken = mock(Invocation.Builder.class);
        when(builderToken.header(eq("Authorization"), eq("Bearer "))).thenReturn(builderToken);
        when(builderToken.get(Response.class)).thenReturn(resToken);
        when(builderToken.post(Entity.json(jo1))).thenReturn(resToken);

        targetToken = mock(WebTarget.class);
        when(targetToken.path(anyString())).thenReturn(targetToken);
        when(targetToken.request(MediaType.APPLICATION_JSON))
                .thenReturn(builderToken);
        when(ccClient.client.target("testCompactClientToken")).thenReturn(targetToken);

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
        jo.append("Weight", "100");
        Assert.assertEquals(ccClient.getActivityInfo("testCompactClient")
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
        jo.append("W", "200g");
        Assert.assertNotEquals(ccClient.getActivityInfo("testCompactClient")
                .toJSONString(10), jo.toJSONString(10));
    }

    /**
     * Tests the (in)equality between an arbitrary
     * JSON value not equal to the pre-set
     * value in GET response body.
     * Expects true.
     */
    @Test
    public void getRequestGetToken() {
        JSONObject jo = new JSONObject();
        jo.append("Weight", "100");
        jo.append("token", "tok");
        Assert.assertEquals(jo.toJSONString(10), ccClient.getActivityInfo("testCompactClientToken")
                .toJSONString(10));
        Mockito.verify(resToken).readEntity(JSONObject.class);
    }

    /**
     * Tests the (in)equality between an arbitrary
     * JSON value not equal to the pre-set
     * value in GET response body.
     * Expects false.
     */
    @Test
    public void getRequestIncorrectTokenNonNull() {
        ccClient.token = "";
        JSONObject jo = new JSONObject();
        jo.append("W", "200g");
        Assert.assertNotEquals(ccClient.getActivityInfo("testCompactClient")
                .toJSONString(10), jo.toJSONString(10));
        ccClient.token = null;
    }

    /**
     * Tests (in)equality between an arbitrary value of distance
     * equal to the pre-set value in the post method.
     * Expects true.
     */
    @Test
    public void postRequestCorrect() {
        JSONObject j1 = new JSONObject();
        j1.append("Weight", "100");
        JSONObject j2 = ccClient.postActivityInfo("testCompactClient");
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
        j1.append("Wieght", "2050");
        JSONObject j2 = ccClient.postActivityInfo("testCompactClient");
        Assert.assertNotEquals(j2.toJSONString(10), j1.toJSONString(10));
    }

}
