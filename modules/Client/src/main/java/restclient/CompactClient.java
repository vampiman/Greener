package restclient;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
//import javafx.scene.layout.Pane;
//import gui.Controller;

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


public class CompactClient {

    Client client;
    String credentials;
    String token;

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


    /**
     * General get method for most GET requests.
     *
     * @param webTarget the target which returns a response to GET
     * @param auth      the authentication (token or credentials)
     * @return custom JSONObject based on request
     */
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
     *
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

        return auth;
    }

    /**
     * Method skims the token of unnecessary characters.
     *
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
     *
     * @param averageConsumption Average consumption before savings
     * @param currentConsumption Consumption after starting to lower temperature
     * @param energyType         Type of energy used to generate the heat
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

        return res.readEntity(JSONObject.class).toJSONString(10);
    }


    /**
     * Method telling if the token is stored on disk.
     *
     * @return Whether it is stored or not as a boolean
     * @throws IOException Error can occur while reading the file
     */
    public double getHeatConsumption() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/heatconsumption/get");
        JSONObject jo = generalGet(webTarget, auth);

        return (double) jo.get("savedHeatConsumption");

    }


    /**
     * Method that posts data about public transport.
     *
     * @param typeCar             Type of car user did not use
     * @param typePublicTransport Type of public transport use
     * @param distance            Distance taken by a bus
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
     *
     * @return Whether it is stored or not as a boolean
     * @throws IOException Error can occur while reading the file
     */
    public double getPublicTransport() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/publictransport/get");
        JSONObject jo = generalGet(webTarget, auth);

        return (double) jo.get("savedPublicTransport");

    }


    /**
     * Returns the total CO2 saved by biking.
     *
     * @return total CO2 saved by biking
     */
    public double getBiker() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/bike/distance");
        JSONObject jo = generalGet(webTarget, auth);

        return (double) jo.get("bikeSaved");
    }


    /**
     * Posts new bike activity information.
     *
     * @param vehicleType type of vehicle
     * @param distance    distance traveled
     * @return the response with the originally sent resource
     */
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

    /**
     * Returns the total CO2 saved by using solar panels.
     *
     * @return total CO2 saved with solar panels
     */
    public int getSolar() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/solarpanels/percentage");
        JSONObject jo = generalGet(webTarget, auth);

        Double savedSolar = (Double) jo.get("savedSolar");

        return savedSolar.intValue();
    }


    /**
     * Posts latest solar panel activity information.
     *
     * @param kwhProduced kilowatts-hour produces by solar panels
     * @return JSON string of the resource containing the input information.
     */
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

    /**
     * Method executes request to follow another user.
     *
     * @param email friend's email
     * @return JSONString of resource originally posted
     */
    public JSONObject followUser(String email) {
        String auth = formAuthHeader();
        Resource re = new Resource();

        Response res = client.target("http://localhost:8080/serverside/webapi/friends/follow?user=" + email)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class);
    }

    /**
     * Method that fetches all user's friends.
     *
     * @return 2D String array with friends and their scores
     */
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

        int counter = 0;
        while (counter != j1.size()) {
            JSONArray arr = j1.getJSONArray(counter);
            result[counter][0] = (String) arr.get(0);
            result[counter][1] = (String) arr.get(1);
            counter++;
        }

        return result;
    }

    /**
     * Get total CO2 saved by vegan/vegetarian meals.
     *
     * @return total CO2 saved by eating vegan/vegetarian meals
     */
    public Double getMealCarbon() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/veganmeal/totalVegan");
        JSONObject jo = generalGet(webTarget, auth);

        return (Double) jo.get("total_Meals");
    }

    /**
     * Post data of latest vegan/vegetarian meal activity.
     *
     * @param amountOfMeals amount of meals eaten
     * @param insteadOf     food which was substituted
     * @param ihad          the food which was eaten instead of the substituted one
     * @return the posted JSONObject with activity info
     */
    public JSONObject postMeal(Double amountOfMeals, String insteadOf, String ihad) {
        String auth = formAuthHeader();
        Resource re = new Resource();
        re.setTotal_Meals(amountOfMeals);
        re.setMealType(insteadOf);
        re.setMealType2(ihad);

        Response res = client.target("http://localhost:8080/serverside/webapi/veganmeal/post")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
                .post(Entity.json(re));

        return res.readEntity(JSONObject.class);
    }

    /**
     * Get total CO2 saved by buying local produce.
     *
     * @return total CO2 saved by buying local produce
     */
    public Double getLocalProduce() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/localproduce/get");
        JSONObject jo = generalGet(webTarget, auth);

        return (Double) jo.getDouble("localSaved");
    }

    /**
     * Posts the latest buying local produce activity information.
     *
     * @param kilograms of local produce bought
     * @param type      type of food
     * @return JSONObject Resource originally posted
     */
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

    /**
     * Returns user statistics (of all activities).
     *
     * @return JSONObject with user statistics
     */
    public JSONObject getStats() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/allstats");
        JSONObject jo = generalGet(webTarget, auth);

        return jo;
    }


    /**
     * Returns user personal information.
     *
     * @return JSONObject with user personal information
     */
    public JSONObject getPersonalInfo() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/personalinfo");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", auth);
        Response response = invocationBuilder.get(Response.class);
        JSONObject jo = response.readEntity(JSONObject.class);

        adjustToken(jo);

        return jo;
    }

    /**
     * Returns user's achievements status.
     *
     * @return user's achievements status
     */
    public String getAchievements() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/achievements");
        JSONObject jo = generalGet(webTarget, auth);

        return jo.get("achievements").toString();
    }


    /**
     * Returns user's current level.
     *
     * @return user's current level
     */
    public int getLevel() {
        String auth = formAuthHeader();

        WebTarget webTarget = this.client.target("http://localhost:8080/serverside/webapi/statistics/level");
        JSONObject jo = generalGet(webTarget, auth);

        return (int) jo.get("level");
    }

    /**
     * Method that verifies the token stored in a file.
     *
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

    //    /**
    //     * Create a string of bits for the achievements.
    //     * When a bit is set to 1, the user achieved and achievement.
    //     */
    //    public void setAchievements() {
    //        StringBuilder bits = new StringBuilder("000000000000000000000000000");
    //        //if (count(getAllFriends()) > 10){
    //        //    bits.setCharAt(0, '1');
    //        //}
    //        //if (getAllFriends() > 50){
    //        //    bits.setCharAt(1, '1');
    //        //}
    //        //if (getAllFriends() > 100){
    //        //    bits.setCharAt(2, '1');
    //        //}
    //        if (getLevel() > 10) {
    //            bits.setCharAt(6, '1');
    //        }
    //        if (getLevel() > 50) {
    //            bits.setCharAt(7, '1');
    //        }
    //        if (getLevel() > 100) {
    //            bits.setCharAt(8, '1');
    //        }
    //        if (getBiker() > 10) {
    //            bits.setCharAt(9, '1');
    //        }
    //        if (getBiker() > 50) {
    //            bits.setCharAt(10, '1');
    //        }
    //        if (getBiker() > 100) {
    //            bits.setCharAt(11, '1');
    //        }
    //        if (getPublicTransport() > 10) {
    //            bits.setCharAt(12, '1');
    //        }
    //        if (getPublicTransport() > 50) {
    //            bits.setCharAt(13, '1');
    //        }
    //        if (getPublicTransport() > 100) {
    //            bits.setCharAt(14, '1');
    //        }
    //        if (getSolar() > 10) {
    //            bits.setCharAt(15, '1');
    //        }
    //        if (getSolar() > 50) {
    //            bits.setCharAt(16, '1');
    //        }
    //        if (getSolar() > 100) {
    //            bits.setCharAt(17, '1');
    //        }
    //        if (getHeatConsumption() > 10) {
    //            bits.setCharAt(18, '1');
    //        }
    //        if (getHeatConsumption() > 50) {
    //            bits.setCharAt(19, '1');
    //        }
    //        if (getHeatConsumption() > 100) {
    //            bits.setCharAt(20, '1');
    //        }
    //        if (getMealCarbon() > 10) {
    //            bits.setCharAt(21, '1');
    //        }
    //        if (getMealCarbon() > 50) {
    //            bits.setCharAt(22, '1');
    //        }
    //        if (getMealCarbon() > 100) {
    //            bits.setCharAt(23, '1');
    //        }
    //        if (getLocalProduce() > 10) {
    //            bits.setCharAt(24, '1');
    //        }
    //        if (getLocalProduce() > 50) {
    //            bits.setCharAt(25, '1');
    //        }
    //        if (getLocalProduce() > 100) {
    //            bits.setCharAt(26, '1');
    //        }
    //    }

    //    public StringBuilder setAchievements(){
    //       StringBuilder bits = new StringBuilder("000000000000000000000000000");
    //
    //       return bits;
    //    }

    //FOR TESTING ONLY
    //    /**
    //     * Main method that simulates the client.
    //     *
    //     * @param args Input for main
    //     */
    //    public static void main(String[] args) throws IOException {
    //        CompactClient cc = new CompactClient();
    //        //        System.out.println(cc.getPublicTransport());
    //
    //        System.out.println(cc.getHeatConsumption());
    //        //cc.getActivityInfo("http://localhost:8080/serverside/webapi/localproduce/get");
    //        //cc.postActivityInfo("http://localhost:8080/serverside/webapi/localproduce/post");
    //
    //    }

}