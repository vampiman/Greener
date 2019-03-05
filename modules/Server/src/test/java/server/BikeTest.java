package server;

import cn.hutool.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class BikeTest {

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects true.
     */
    @Test
    public void getDataCorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.getData().getEntity(),
                jo);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects false.
     */
    @Test
    public void getDataIncorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "1000");
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.getData().getEntity(),
                jo);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    @Test
    public void getDataCorrectStatus() {
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.getData().getStatus(), 200);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
    @Test
    public void getDataIncorrectStatus() {
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.getData().getStatus(), 404);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects true.
     */
    @Test
    public void postDataCorrectEntity() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.postData(jo).getEntity(),
                jo);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects false.
     */
    @Test
    public void postDataIncorrectEntity() {
        JSONObject jo1 = new JSONObject();
        jo1.append("Distance", "10");
        JSONObject jo2 = new JSONObject();
        jo2.append("Distnce", "1011");
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.postData(jo1).getEntity(),
                jo2);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    @Test
    public void postDataCorrectStatus() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertEquals(bikeServer.postData(jo).getStatus(),
                200);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
    @Test
    public void postDataIncorrectStatus() {
        JSONObject jo = new JSONObject();
        jo.append("Distance", "10");
        Bike bikeServer = new Bike();
        Assert.assertNotEquals(bikeServer.postData(jo).getStatus(),
                404);
    }
}