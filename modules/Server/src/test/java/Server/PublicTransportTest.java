package server;

import cn.hutool.json.JSONObject;

import static org.junit.Assert.*;
import org.junit.Test;


public class PublicTransportTest {

    server.PublicTransport PTserver = new server.PublicTransport();


    /**
     * Tests if the status of the response from the server
     * is 200 (OK) after performing an get-request on the server.
     * Expects equal.
     */
    @Test
    public void getMethodStatus() {
        assertEquals(200, PTserver.getData().getStatus());
    }

    /**
     * Tests if the status of the response from the server
     * is 200 (OK) after performing an post-request on the server.
     * Expects equal.
     */
    @Test
    public void postMethodStatus() {
        JSONObject obj = new JSONObject();
        assertEquals(200, PTserver.postData(obj).getStatus());
    }

    /**
     * Tests if the JSON object created in the test
     * is the same as the received JSON from the server after making
     * an get-request.
     * Expects equal.
     */
    @Test
    public void getDataJSON() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 100);

        assertEquals(obj, PTserver.getData().getEntity());
    }

    /**
     * Tests if the JSON object that is send to the server using a post request
     * is the same as the object that is send in the response of the request.
     * Expects equal.
     */
    @Test
    public void postDataJSON() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 10000);
        assertEquals(obj, PTserver.postData(obj).getEntity());
    }

    /**
     * Tests if the status of the response from the server
     * is 404 (Method not found) after performing an get-request on the server.
     * Expects unequal.
     */
    @Test
    public void getMethodStatusIncorrect() {
        assertNotEquals(404, PTserver.getData().getStatus());
    }

    /**
     * Tests if the status of the response from the server
     * is 404 (Method not found) after performing an post-request on the server.
     * Expects unequal.
     */
    @Test
    public void postMethodStatusIncorrect() {
        JSONObject obj = new JSONObject();
        assertNotEquals(404, PTserver.postData(obj).getStatus());
    }

    /**
     * Tests if the JSON object created in the test
     * is the same as the received JSON from the server after making
     * an get-request.
     * Expects unequal.
     */
    @Test
    public void getDataJSONIncorrect() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 5);

        assertNotEquals(obj, PTserver.getData().getEntity());
    }

    /**
     * Tests if an JSON object different from the JSON object that is send to the server using a post-request
     * is the same as the object that is send in the response of the request.
     * Expects unequal.
     */
    @Test
    public void postDataJSONIncorrect() {
        JSONObject obj = new JSONObject();
        obj.put("Points", 10000);

        JSONObject obj2 = new JSONObject();
        obj2.put("Points", 5);
        assertNotEquals(obj2, PTserver.postData(obj).getEntity());
    }

}