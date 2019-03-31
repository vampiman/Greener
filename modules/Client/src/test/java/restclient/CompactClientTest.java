package restclient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(File.class)
public class CompactClientTest {

    JSONObject toReturn;

    @Mock
    BufferedReader br;

    @Mock
    File file;

    @Mock
    WebTarget targetToken;
    WebTarget targetNoToken;
    WebTarget mockTarget;

    @Mock
    Invocation.Builder builderNoToken;
    Invocation.Builder builderToken;
    Invocation.Builder mockBuilder;

    @Mock
    Client client;

    @Mock
    Response resToken;
    Response resNoToken;
    Response mockResponse;

    @InjectMocks
    CompactClient ccClient;

    /**
     * Method that initialises all objects needed for testing
     * and specifies mocks' behaviour.
     */
    @Before
    public void setup() throws Exception {
        client = mock(Client.class);
        file = mock(File.class);
        br = mock(BufferedReader.class);



        ccClient = new CompactClient();
        ccClient.client = client;
        ccClient.token = null;
        ccClient.credentials = "";
        mockTarget = mock(WebTarget.class);
        mockBuilder = mock(Invocation.Builder.class);
        mockResponse = mock(Response.class);


        //JSONObject returned by server
        toReturn = new JSONObject();
        toReturn.put("savedHeatConsumption", 100.0);
        toReturn.put("bikeSaved", 100.0);
        toReturn.put("localSaved", 100.0);
        toReturn.put("savedPublicTransport", 100.0);
        toReturn.put("savedSolar", 100.0);
        toReturn.put("total_Meals", 100.0);
        toReturn.put("achievements", "000");
        toReturn.put("level", 5);

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

    @Test
    public void getHeatConsumptionTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getHeatConsumption());
    }

    @Test
    public void getBikeTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getBiker());
    }

    @Test
    public void getMealsTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getMealCarbon().intValue());
    }

    @Test
    public void getLocalTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getLocalProduce().intValue());
    }

    @Test
    public void getSolarTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getSolar());
    }

    @Test
    public void getTransportTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getPublicTransport());
    }

    @Test
    public void postVeganTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();


        re.setMealType("Dairy");
        re.setMealType2("Meat");
        re.setTotal_Meals(1.0);


        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postMeal(1.0,"Dairy","Meat").equals(toReturn));
    }

    @Test
    public void postLocalTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();


        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postLocalProduce(5.0,"Meat").equals(toReturn));
    }

    @Test
    public void postSolarTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();

        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postSolar(100).equals(toReturn.toJSONString(10)));
    }

    @Test
    public void postTransportTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();

        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postPublicTransport("Car",
                "Public", 5.0).equals(toReturn.toJSONString(10)));
    }

    @Test
    public void postHeatTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();

        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postHeatConsumption(100,
                50, "Electric").equals(toReturn.toJSONString(10)));
    }

    @Test
    public void postBikeTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();

        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postBiker("Electric",
                100).equals(toReturn.toJSONString(10)));
    }

    @Test
    public void followUserTest() throws Exception {
        ccClient = new CompactClient();
        ccClient.client = client;
        Resource re = new Resource();

        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.followUser("email").equals(toReturn));
    }

    @Test
    public void getStatsTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getStats().equals(toReturn));
    }

    @Test
    public void getPersonalTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getPersonalInfo().equals(toReturn));
    }

    @Test
    public void getAchievementsTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getAchievements().equals("000"));
    }

    @Test
    public void getLevelTest() throws IOException {
        ccClient = new CompactClient();
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getLevel() == 5);
    }
}
