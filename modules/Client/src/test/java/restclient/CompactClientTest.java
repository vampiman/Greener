package restclient;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//@RunWith(PowerMockRunner.class)
//@PrepareForTest({File.class, BufferedReader.class})
public class CompactClientTest {

    JSONObject toReturn;

    @Mock
    BufferedReader br;

    @Mock
    JSONArray array;

    @Mock
    JSONObject mockJson;

    @Mock
    User user;

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
        array = Mockito.mock(JSONArray.class);
        mockJson = Mockito.mock(JSONObject.class);
        user = Mockito.mock(User.class);
        client = Mockito.mock(Client.class);
        file = Mockito.mock(File.class);
        br = Mockito.mock(BufferedReader.class);

        Mockito.when(file.exists()).thenReturn(false);
        Mockito.when(br.readLine()).thenReturn(null);

        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        ccClient.token = null;
        ccClient.credentials = "";
        mockTarget = Mockito.mock(WebTarget.class);
        mockBuilder = Mockito.mock(Invocation.Builder.class);
        mockResponse = Mockito.mock(Response.class);


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
        resNoToken = Mockito.mock(Response.class);
        Mockito.when(resNoToken.readEntity(JSONObject.class)).thenReturn(jo1);
        Mockito.when(resNoToken.getStatus()).thenReturn(200); //The OK status

        JSONObject jo2 = new JSONObject();
        jo2.append("Weight", "100");
        jo2.append("token", "tok");
        resToken = Mockito.mock(Response.class);
        Mockito.when(resToken.readEntity(JSONObject.class)).thenReturn(jo2);
        Mockito.when(resToken.getStatus()).thenReturn(200); //The OK status

        builderNoToken = Mockito.mock(Invocation.Builder.class);
        Mockito.when(builderNoToken.header(eq("Authorization"),
                eq("Bearer "))).thenReturn(builderNoToken);
        Mockito.when(builderNoToken.get(Response.class)).thenReturn(resNoToken);
        Mockito.when(builderNoToken.post(Entity.json(jo1))).thenReturn(resNoToken);

        targetNoToken = Mockito.mock(WebTarget.class);
        Mockito.when(targetNoToken.path(anyString())).thenReturn(targetNoToken);
        Mockito.when(targetNoToken.request(MediaType.APPLICATION_JSON))
                .thenReturn(builderNoToken);
        Mockito.when(ccClient.client.target("testCompactClient")).thenReturn(targetNoToken);

        builderToken = Mockito.mock(Invocation.Builder.class);
        Mockito.when(builderToken.header(eq("Authorization"),
                eq("Bearer "))).thenReturn(builderToken);
        Mockito.when(builderToken.get(Response.class)).thenReturn(resToken);
        Mockito.when(builderToken.post(Entity.json(jo1))).thenReturn(resToken);

        targetToken = Mockito.mock(WebTarget.class);
        Mockito.when(targetToken.path(anyString())).thenReturn(targetToken);
        Mockito.when(targetToken.request(MediaType.APPLICATION_JSON))
                .thenReturn(builderToken);
        Mockito.when(ccClient.client.target("testCompactClientToken")).thenReturn(targetToken);



    }

    /**
     * Test constructor when reading something.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void testConstructorSomeRead() throws IOException {
        br = Mockito.mock(BufferedReader.class);
        file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(br.readLine()).thenReturn("abc");
        ccClient = new CompactClient(file, br);
        ccClient.client = client;


        Assert.assertEquals("abc", ccClient.token);
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

    /**
     * Method for testing the GET for the Heat Consumption feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getHeatConsumptionTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getHeatConsumption());
    }

    /**
     * Method for testing the GET for the Bike feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getBikeTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getBiker());
    }

    /**
     * Method for testing the GET for the Vegan Meal feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getMealsTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getMealCarbon().intValue());
    }

    /**
     * Method for testing the GET for the Local Product feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getLocalTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getLocalProduce().intValue());
    }

    /**
     * Method for testing the GET for the Solar Panel feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getSolarTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, ccClient.getSolar());
    }

    /**
     * Method for testing the GET for the Public Transport feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getTransportTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertEquals(100, (int)ccClient.getPublicTransport());
    }

    /**
     * Method for testing the Post for the Vegan Meal feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postVeganTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();


        re.setMealType("Dairy");
        re.setMealType2("Meat");
        re.setTotal_Meals(1.0);


        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postMeal(1.0,"Dairy","Meat").equals(toReturn));
    }

    /**
     * Method for testing the Post for the Local Product feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postLocalTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();


        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postLocalProduce(5.0,"Meat").equals(toReturn));
    }

    /**
     * Method for testing the Post for the Solar Panels feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postSolarTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();

        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postSolar(100).equals(toReturn.toJSONString(10)));
    }

    /**
     * Method for testing the Post for the Public Transport feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postTransportTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();

        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postPublicTransport("Car",
                "Public", 5.0).equals(toReturn.toJSONString(10)));
    }

    /**
     * Method for testing the Post for the Heat Consumption feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postHeatTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();

        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postHeatConsumption(100,
                50, "Electric").equals(toReturn.toJSONString(10)));
    }

    /**
     * Method for testing the Post for the Bike feature.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void postBikeTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();

        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.postBiker("Electric",
                100).equals(toReturn.toJSONString(10)));
    }

    /**
     * Method for testing the request for following another user.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void followUserTest() throws Exception {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;
        Resource re = new Resource();

        //        whenNew(Resource.class).withAnyArguments().thenReturn(re);
        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.header(any(), any())).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.post(any())).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.followUser("email").equals(toReturn));
    }

    /**
     * Method for testing the request for getting User stats.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getStatsTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getStats().equals(toReturn));
    }

    /**
     * Method for testing the request for getting User personal info.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getPersonalTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getPersonalInfo().equals(toReturn));
    }

    /**
     * Method for testing the request for getting User achievements.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getAchievementsTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getAchievements().equals("000"));
    }

    /**
     * Method for testing the request for getting User Level.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getLevelTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(toReturn);

        Assert.assertTrue(ccClient.getLevel() == 5);
    }

    /**
     * Method for testing the checkToken function when the File exists.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void checkTokenTestFileExists() throws IOException {
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(br.readLine()).thenReturn(null);
        Mockito.when(user.login(null)).thenReturn(true);
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Assert.assertEquals(true, ccClient.checkToken(file, br, user));
    }

    /**
     * Method for testing the checkToken function when the File doesn't exists.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void checkTokenTestFileDoesNotExists() throws IOException {
        Mockito.when(file.exists()).thenReturn(false);
        Mockito.when(user.login(anyString())).thenReturn(false);
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Assert.assertEquals(false, ccClient.checkToken(file, br, user));
    }

    /**
     * Method for testing the request for getting the list of friends.
     * @throws IOException Possible error with regard to file reading.
     */
    @Test
    public void getAllFriendsTest() throws IOException {
        ccClient = new CompactClient(file, br);
        ccClient.client = client;

        Mockito.when(client.target(anyString())).thenReturn(mockTarget);
        Mockito.when(mockTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.get(Response.class)).thenReturn(mockResponse);
        Mockito.when(mockResponse.readEntity(JSONObject.class)).thenReturn(mockJson);
        Mockito.when(mockJson.getJSONArray("friends")).thenReturn(array);
        Mockito.when(array.size()).thenReturn(1);
        Mockito.when(array.getJSONArray(anyInt())).thenReturn(array);
        Mockito.when(array.get(0)).thenReturn("SomeName");
        Mockito.when(array.get(1)).thenReturn("SomeScore");

        String[][] result = ccClient.getAllFriends();

        Assert.assertEquals("SomeName", result[0][0]);
        Assert.assertEquals("SomeScore", result[0][1]);

    }

}
