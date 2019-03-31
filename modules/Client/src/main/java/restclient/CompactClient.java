package restclient;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class CompactClient  {

    private Client client;
    private String credentials;
    private String token;

    /**
     * Constructor for user.
     */
    public CompactClient() throws IOException {
        this.client = ClientBuilder.newClient();
        String token = "";
        File file = new File("test.txt");
        boolean fileExists = file.exists();

        if (fileExists) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                token = st;
            }
        }
        this.token = token;
    }

    public JSONObject generalGet(WebTarget webTarget, String auth) {
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", auth);
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        return jo;
    }

    /**
     * Method forms the 'Authorization' header content.
     * @return The 'Authorization' header content
     */
    public String formAuthHeader() {
        //HASH CREDENTIALS
        String auth = "Bearer ";
        if (token == null) {
            auth = auth + credentials;
        } else {
            credentials = null;
            auth = auth + token;
        }

//        System.out.println("Token is " + token);
//        System.out.println("Credentials is " + credentials);
        return auth;
    }

    /**
     * Method skims the token of unnecessary characters.
     * @param jo JSONObject to get the header from
     */
    public void adjustToken(JSONObject jo) {
        if (this.token == null && jo.containsKey("token")) {
            this.token = jo.get("token").toString();
            this.token = this.token.replaceAll("\\[", "");
            this.token = this.token.replaceAll("\\]", "");
            this.token = this.token.replaceAll("\"", "");
        }
    }

    /**
     * The method creates a client and a GET request
     * for retrieval of data on the server in a JSON file.
     *
     * @return JSON object contained in the server response.
     */
    public JSONObject getActivityInfo(String uri) {
        WebTarget webTarget = this.client.target(uri);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", formAuthHeader());
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        return jo;

    }

    /**
     * The method creates a client and a POST request
     * for upload of the entered data to the server as JSON.
     *
     * @return JSON object from server response (the one which was posted)
     */
    public JSONObject postActivityInfo(String uri) {
        String auth = formAuthHeader();

        JSONObject j1 = new JSONObject().append("Weight", "100");
        Response res = client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(j1));

        JSONObject jo = res.readEntity(JSONObject.class);

        adjustToken(jo);

        return jo;
    }

    /**
     * Method for sending a post request for the calculation of the carbon
     * footprint with regard to the heat consumption.
     * @param averageConsumption Average consumption before savings
     * @param currentConsumption Consumption after starting to lower temperature
     * @param energyType Type of energy used to generate the heat
     * @return JSON Response as a String
     */
    public String postHeatConsumption(int averageConsumption, int currentConsumption,
                                          String energyType) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setAverageHeatConsumption(averageConsumption);
        re.setCurrentHeatConsumption(currentConsumption);
        re.setEnergyType(energyType);

        Response res = client.target("http://localhost:8080/serverside/webapi/heatconsumption/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class).toString();
    }


    /**
     * Method telling if the token is stored on disk.
     * @return Whether it is stored or not as a boolean
     * @throws IOException Error can occur while reading the file
     */
    public double getHeatConsumption() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/heatconsumption/get");
        JSONObject jo = generalGet(webTarget, auth);

        return (double)jo.get("savedHeatConsumption");

    }

    /**
     * Method that posts data about public transport.
     * @param typeCar Type of car user did not use
     * @param typePublicTransport Type of public transport use
     * @param distance Distance taken by a bus
     * @return JSON object
     */
    public String postPublicTransport(String typeCar,
                                      String typePublicTransport, double distance) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setCarType(typeCar);
        re.setPublicTransportType(typePublicTransport);
        re.setTotal_Distance(distance);

        Response res = client.target("http://localhost:8080/serverside/webapi/publictransport/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class).toJSONString(10);
    }

    /**
     * Method telling if the token is stored on disk.
     * @return Whether it is stored or not as a boolean
     * @throws IOException Error can occur while reading the file
     */
    public JSONObject getPublicTransport() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/publictransport/get");
        JSONObject jo = generalGet(webTarget, auth);

        return jo;

    }

    public String getBiker() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/bike/distance");
        JSONObject jo = generalGet(webTarget, auth);

        return jo.toJSONString(10);
    }

    public String postBiker(String vehicleType, double distance) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setCarType(vehicleType);
        re.setTotal_Distance(distance);

        Response res = client.target("http://localhost:8080/serverside/webapi/bike/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class).toJSONString(10);
    }

    public int getSolar() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/solarpanels/percentage");
        JSONObject jo = generalGet(webTarget, auth);

        return (int)jo.get("savedSolar");
    }

    public String postSolar(int kwhProduced) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setKwh(kwhProduced);

        Response res = client.target("http://localhost:8080/serverside/webapi/solarpanels/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class).toJSONString(10);
    }

    public JSONObject followUser(String email) {
        String auth = formAuthHeader();
        Resource re = new Resource();

        Response res = client.target("http://localhost:8080/serverside/webapi/friends/follow?user=" + email)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class);
    }

    public String[][] getAllFriends() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/friends/list");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", auth);
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        JSONArray j1 = jo.getJSONArray("friends");

        String[][] result = new String[j1.size()][2];

        int i = 0;
        while(i != j1.size()) {
            JSONArray arr = j1.getJSONArray(i);
            result[i][0] = (String)arr.get(0);
            result[i][1] = (String)arr.get(1);
            i++;
        }

        return result;
    }

    public Double getMealCarbon() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/veganmeal/totalVegan");
        JSONObject jo = generalGet(webTarget, auth);

        return (Double)jo.get("total_Meals");
    }

    public JSONObject postMeal(Double amountOfMeals, String insteadOf, String iHad) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setTotal_Meals(amountOfMeals);
        re.setMealType(insteadOf);
        re.setMealType2(iHad);

        Response res = client.target("http://localhost:8080/serverside/webapi/veganmeal/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class);
    }

    public Double getLocalProduce() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/localproduce/get");
        JSONObject jo = generalGet(webTarget, auth);

        return (Double)jo.getDouble("total_Produce");
    }

    public JSONObject postLocalProduce(Double kilograms, String type) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setTotal_Produce(kilograms);
        re.setMealType(type);

        Response res = client.target("http://localhost:8080/serverside/webapi/localproduce/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class);
    }

    public JSONObject getStats() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/allstats");
        JSONObject jo = generalGet(webTarget, auth);

        return jo;
    }

    public JSONObject getPersonalInfo() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/personalinfo");
        JSONObject jo = generalGet(webTarget, auth);

        return jo;
    }

    public String getAchievements() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/achievements");
        JSONObject jo = generalGet(webTarget, auth);

        return jo.get("achievements").toString();
    }

    public int getLevel() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/level");
        JSONObject jo = generalGet(webTarget, auth);

        return (int)jo.get("level");
    }

    /**
     * Method that verifies the token stored in a file.
     * @return true when authentication succeeded, false when failed
     * @throws IOException in case the file is not found/unable to be opened or read.
     */
    public boolean checkToken() throws IOException {
        String token = "";
        File file = new File("test.txt");
        boolean fileExists = file.exists();

        if (fileExists) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                token = st;
            }
        }

        User user = new User("", "");
        if (user.login(token)) {
            return true;
        }
        return false;
    }

    //FOR TESTING ONLY
    /**
     * Main method that simulates the client.
     *
     * @param args Input for main
     */
    public static void main(String[] args) throws IOException {
        CompactClient cc = new CompactClient();
        //        System.out.println(cc.getPublicTransport());

        System.out.println(cc.getHeatConsumption());
        //cc.getActivityInfo("http://localhost:8080/serverside/webapi/localproduce/get");
        //cc.postActivityInfo("http://localhost:8080/serverside/webapi/localproduce/post");

    }

}