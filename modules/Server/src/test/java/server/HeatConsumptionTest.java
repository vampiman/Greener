package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cn.hutool.json.JSONObject;

import org.junit.Test;

public class HeatConsumptionTest {

    server.HeatConsumption server = new server.HeatConsumption();


    /**
     * Tests if the status of the response from the server
     * is 200 (OK) after performing an get-request on the server.
     * Expects equal.
     */
    @Test
    public void getMethodStatus() {
        assertEquals(200, server.getData().getStatus());
    }

    /**
     * Tests if the status of the response from the server
     * is 200 (OK) after performing an post-request on the server.
     * Expects equal.
     */
    @Test
    public void postMethodStatus() {
        JSONObject obj = new JSONObject();
        assertEquals(200, server.postData(obj).getStatus());
    }

    /**
     * Tests if the JSON object created in the test
     * is the same as the received JSON from the server after making
     * an get-request.
     * Expects equal.
     */
    @Test
    public void getDataJson() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 100);

        assertEquals(obj, server.getData().getEntity());
    }

    /**
     * Tests if the JSON object that is send to the server using a post request
     * is the same as the object that is send in the response of the request.
     * Expects equal.
     */
    @Test
    public void postDataJson() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 10000);
        assertEquals(obj, server.postData(obj).getEntity());
    }

    /**
     * Tests if the status of the response from the server
     * is 404 (Method not found) after performing an get-request on the server.
     * Expects unequal.
     */
    @Test
    public void getMethodStatusIncorrect() {
        assertNotEquals(404, server.getData().getStatus());
    }

    /**
     * Tests if the status of the response from the server
     * is 404 (Method not found) after performing an post-request on the server.
     * Expects unequal.
     */
    @Test
    public void postMethodStatusIncorrect() {
        JSONObject obj = new JSONObject();
        assertNotEquals(404, server.postData(obj).getStatus());
    }

    /**
     * Tests if the JSON object created in the test
     * is the same as the received JSON from the server after making
     * an get-request.
     * Expects unequal.
     */
    @Test
    public void getDataJsonIncorrect() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 5);

        assertNotEquals(obj, server.getData().getEntity());
    }

    /**
     * Tests if an JSON object different from the JSON object
     * that is send to the server using a post-request
     * is the same as the object that is send in the response of the request.
     * Expects unequal.
     */
    @Test
    public void postDataJsonIncorrect() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 10000);

        JSONObject obj2 = new JSONObject();
        obj2.put("Points", 5);
        assertNotEquals(obj2, server.postData(obj).getEntity());
    }

}
